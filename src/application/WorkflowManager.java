package application;

import model.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Instant;
import java.util.*;

public final class WorkflowManager {

    public static final String xmlLocation = "";
    public static final String userCodeLocation = "";

    private static HashMap<Integer, WorkflowInstance> workflowInstanceHashMap = null;
    private static final String dataFilePath = "workflowData.dat";

    public static WorkflowStructure parse(String filePath) {
        try {
            File xmlFile = new File(filePath);

            SAXBuilder saxBuilder = new SAXBuilder();

            Document document = saxBuilder.build(xmlFile);

            Element workflowElement = document.getRootElement();

            Element connectionsElement = workflowElement.getChild("allflows");
            List<Connection> connectionList = new ArrayList<>();
            connectionsElement.getChildren().forEach(element -> {
                connectionList.add(new Connection(element.getChildText("id"),
                        element.getChildText("origin"),
                        element.getChildText("dest")));
            });

            Element statesElement = workflowElement.getChild("allstates");
            List<State> stateList = new ArrayList<>();
            statesElement.getChildren().forEach(element -> {
                String stateType = element.getChildText("statetype");
                if (stateType.equals("Linear")) {
                    stateList.add(new LinearState(element.getChildText("id"),
                            element.getChildText("name"),
                            element.getChildText("type")));
                } else if (stateType.equals("Branching")) {
                    stateList.add(new BranchState(element.getChildText("id"),
                            element.getChildText("name"),
                            element.getChildText("type")));
                } else if (stateType.equals("Selection")) {
                    SelectionState selectionState = new SelectionState(element.getChildText("id"),
                            element.getChildText("name"),
                            element.getChildText("type"),
                            element.getChild("field").getChildText("name"));
                    element.getChild("field").getChildren("value").forEach(element1 -> {
                        selectionState.addSelect(element1.getText().trim(), element1.getChildText("connectionid"));
                    });
                    stateList.add(selectionState);
                } else {// merge state
                    stateList.add(new MergeState(element.getChildText("id"),
                            element.getChildText("name"),
                            element.getChildText("type"),
                            element.getChildText("pairstate")));
                }
            });

            Element usersElement = workflowElement.getChild("users");
            Set<String> userTypes = new HashSet<>();
            usersElement.getChildren().forEach(element -> {
                userTypes.add(element.getChildText("name"));
            });

            WorkflowStructure wfs = new WorkflowStructure(stateList, connectionList, userTypes);

            Element formsElement = workflowElement.getChild("allforms");
            List<Form> formList = new ArrayList<>();
            formsElement.getChildren().forEach(element -> {
                List<Field> fieldList = new ArrayList<Field>();
                element.getChildren("formfield").forEach(element1 -> {
                    fieldList.add(new Field(element1.getText(), "String"));
                });
                formList.add(new Form(element.getChildText("id"),
                        element.getChildText("name"),
                        element.getChildText("state"),
                        fieldList));
            });
            formList.forEach(form -> {
                wfs.getState(form.belongsTo()).getForms().add(form);
            });

            Element codesElement = workflowElement.getChild("codesnippets");
            List<ProgrammerCode> programmerCodeList = new ArrayList<>();
            if (codesElement != null) {
                codesElement.getChildren().forEach(element -> {
                    programmerCodeList.add(new ProgrammerCode(element.getChildText("packagename"),
                            element.getChildText("classname"),
                            element.getChildText("method"),
                            element.getChildText("stateid")));
                });
                programmerCodeList.forEach(programmerCode -> {
                    wfs.getState(programmerCode.getStateID()).getProgrammerCodes().add(programmerCode);
                });
            }

            return wfs;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * Parse the XML file, get a Document object and create a Workflow instance from the Document
	 * @param user
     * @param wfs
	 * @return
	 */
	public static Integer instantiate(User user, WorkflowStructure wfs){
		if (workflowInstanceHashMap == null) {
            workflowInstanceHashMap = new HashMap<>();
        }

        WorkflowInstance wfi = new WorkflowInstance(wfs, assignWorkflowID(), wfs.getFirstState());

        Integer workflowID = wfi.getId();
        workflowInstanceHashMap.put(workflowID, wfi);
        UserManager.addNewWorkflowInstance(wfi);

        return workflowID;
	}

    /**
     * Get a workflowInstance from id
     * @param id
     * @return
     */
    public static WorkflowInstance getWorkflowInstance(int id) {
	    return workflowInstanceHashMap.get(id);
    }
	
	/**
	 * 
	 * Move token to the next state(s) when a transition is made
	 * call notify to email the appropriate users
	 * @param wfi
	 */
	public static boolean transition(WorkflowInstance wfi){
		if (checkTransSrc(wfi) && checkTransDest(wfi)) {
		    wfi.getCurrentStates().forEach(state -> {
		        state.getProgrammerCodes().forEach(programmerCode -> {
		            try {
		                invokeProgrammerMethod(programmerCode.getPackageName(),
                                programmerCode.getClassName(),
                                programmerCode.getMethodName());
                    } catch (Exception e) {
		                e.printStackTrace();
                    }
                });
            });
		    wfi.nextStates();
		    return true;
        }
        return false;
	}
	
	/**
	 * Check if the end user has done everything required to do in current state(s)
	 * @param wfi
	 * @return
	 */
	public static boolean checkTransSrc(WorkflowInstance wfi){
        Set<State> states = wfi.getCurrentStates();
        for (State state : states) {
            if (!state.canLeave(wfi)) {
                return false;
            }
        }
        return true;
	}
	
	/**
	 * Check if the end user has done everything in all previous state(s)
	 * @param wfi
	 * @return
	 */
	public static boolean checkTransDest(WorkflowInstance wfi){
        Set<State> nextStates = new HashSet<>();
        wfi.getCurrentStates().forEach(state -> nextStates.addAll(state.getNextStates(wfi)));
        for (State state : nextStates) {
            if (!state.canEnter(wfi)) {
                return false;
            }
        }
        return true;
	}
	
	/**
	 * Invoke java method provided by wf programmer
	 * @param packageName
     * @param className
     * @param methodName
     * @param args
	 * @return
	 */
	public static boolean invokeProgrammerMethod(String packageName, String className, String methodName, Object... args)
        throws MalformedURLException,
            ClassNotFoundException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException
    {
		File file = new File(userCodeLocation);

		URL url = file.toURI().toURL();
        URL[] urls = new URL[] {url};

        ClassLoader cl = new URLClassLoader(urls);

        String name;
        if (packageName.isEmpty()) {
            name = className;
        } else {
            name = packageName + "." + className;
        }

        Class<?> aClass = cl.loadClass(name);

        ArrayList<Class> classList = new ArrayList<>();
        for (Object object : args) {
            classList.add(object.getClass());
        }

        Method method = aClass.getMethod(methodName, classList.toArray(new Class[classList.size()]));

        method.invoke(aClass.newInstance(), args);

	    return false;
	}
	
	/**
	 * Send an email notification to the user types specified in the XML file by the workflow programmer
	 * The notify function can extend further to create a notification system on the user interface. 
	 * This will be done as time permits.
	 * @return
	 */
	public static void notifyUser(User receiver, String message){
        receiver.addMessage(message);
	}
	
	/**
	 * Terminates an ongoing workflow instance and removes all the tokens associated with that workflow instance
	 * @param workflowID
	 */
	public static void endWorkflow(int workflowID){
		UserManager.getInvolvesIn(workflowInstanceHashMap.get(workflowID))
                .forEach(user -> notifyUser(user, workflowID + " is terminated"));

	    return;
	}

	/**
	 *
	 * @param user
	 * @param wfi
	 * @return
	 */
	public static List<Form> getForms(User user, WorkflowInstance wfi) {
	    List<Form> forms = new ArrayList<>();
	    wfi.getCurrentStates().forEach(state -> {
	        if (state.getUserType().equals(user.getUserType())) {
                forms.addAll(state.getForms());
            }
        });
        return forms;
    }

    /**
     * Give a new workflowID based on current time. Duplicate avoided.
     * @return the new workflowID
     */
	private static int assignWorkflowID() {
        return Instant.now().hashCode();
    }

    protected static void saveWorkflowData() {
        try {
            File file = new File(dataFilePath);
            file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFilePath));
            oos.writeObject(workflowInstanceHashMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void updateDataForNewUser(User user) {
	    if (workflowInstanceHashMap == null) {
	        return;
        }
	    workflowInstanceHashMap.forEach((integer, workflowInstance) -> {
	        if (workflowInstance.getWorkflowStructure().getUserTypes().contains(user.getUserType())) {
	            user.addWorkflow(workflowInstance);
            }
        });
    }

    /**
     *
     */
    protected static void clearWorkflowData() {
        workflowInstanceHashMap = null;
    }

    /**
     *
     */
    public static void debug() {
        try {
            workflowInstanceHashMap.forEach((integer, workflowInstance) -> {
                System.out.println();
                System.out.println("------------------");
                System.out.println("------------------");
                System.out.println("WorkflowInstanceID: " + workflowInstance.getId());
                System.out.println("---------Completed states---------");
                workflowInstance.getCompletedStates().forEach(state -> System.out.println(state.getId() + ", " + state.getName()));
                System.out.println("---------Current State(s)---------");
                workflowInstance.getCurrentStates().forEach(state -> System.out.println(state.getId() + ", " + state.getName()));
                System.out.println("---------Forms---------");
                workflowInstance.getForms().forEach(form -> {
                    System.out.println(form.getId() + ", " + form.getName() + ", completed: " + form.isCompleted());
                    form.getFields().forEach(field -> {
                        System.out.println("    (" + field.getType() + ") " + field.getName() + ": " + field.getValue());
                    });
                });
                System.out.println("------------------");
                System.out.println("------------------");
                System.out.println();
                System.out.println();
            });
        } catch (NullPointerException e) {
            System.out.println("Something is null");
            e.printStackTrace();
        }
    }

}
