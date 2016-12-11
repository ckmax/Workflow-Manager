package application;

import model.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class WorkflowManager {

    private static HashMap<Integer, WorkflowInstance> workflowInstanceHashMap = null;
    private static String xmlLocation = "";
    private static String userCodeLocation = "";
	
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
    public static WorkflowInstance getWorkflowInstance(Integer id) {
	    return workflowInstanceHashMap.get(id);
    }
	
	/**
	 * 
	 * Move token to the next state(s) when a transition is made
	 * call notify to email the appropriate users
	 * @param wfi
	 */
	public static void transition(WorkflowInstance wfi){
		List<State> states = wfi.getCurrentStates();
		for (State state : states) {
		    if (state.)
        }
	}
	
	/**
	 * 	Check if the token can be moved to the next state
	 * @param t
	 * @return
	 */
	public static boolean checkTransSrc(Token t){
		return t.getCurrentState().checkIfCanMove();
	}
	
	/**
	 * Check if the destination state is ready to accept the token
	 * @param t
	 * @return
	 */
	public static boolean checkTransDest(Token t){
		return t.getNextState().checkIfCanAccept();
	}
	
	/**
	 * Invoke java method provided by wf programmer
	 * @param t
	 * @return
	 */
	public static boolean invokeProgrammerMethod(Token t, String packageName, String className, String methodName, String[] parametersClassNames, Object... parameters)
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

        Class aClass = cl.loadClass(name);

        ArrayList<Class> classList = new ArrayList<>();
        for (String s : parametersClassNames) {
            classList.add(Class.forName(s));
        }

        Method method = aClass.getMethod(methodName, classList.toArray(new Class[classList.size()]));

        method.invoke(aClass.newInstance(), parameters);

	    return false;
	}
	
	/**
	 * Send an email notification to the user types specified in the XML file by the workflow programmer
	 * The notify function can extend further to create a notification system on the user interface. 
	 * This will be done as time permits.
	 * @return
	 */
	public static void notifyUser(User sender, User receiver, String text){

	}
	
	/**
	 * Terminates an ongoing workflow instance and removes all the tokens associated with that workflow instance
	 * @param workflowID
	 */
	public static void endWorkflow(int workflowID){
		return;
	}

	/**
	 *
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
