package application;

import model.Form;
import model.State;
import model.User;
import model.WorkflowInstance;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
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

	/**
	 * Parse the XML file, get a Document object and create a Workflow instance from the Document
	 * @param filePath
	 * @return
	 */
	public static Integer instantiate(User user, String filePath){
		if (workflowInstanceHashMap == null) {
            workflowInstanceHashMap = new HashMap<>();
        }

		try {
            File xmlFile = new File(filePath);

            SAXBuilder saxBuilder = new SAXBuilder();

            Document document = saxBuilder.build(xmlFile);

            Element workflowElement = document.getRootElement();

        } catch (Exception e) {
		    e.printStackTrace();
        }

        WorkflowInstance wfi = null;

        user.addWorkflow(wfi);

        Integer workflowID = assignWorkflowID();
        workflowInstanceHashMap.put(workflowID, wfi);

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

    /**
     *
     */
    protected static void clearWorkflowData() {
        workflowInstanceHashMap = null;
    }

}
