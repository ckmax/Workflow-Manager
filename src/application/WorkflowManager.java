package application;

import model.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class WorkflowManager {

    private static HashMap<String, WorkflowInstance> workflowInstanceHashMap = null;
	
	/**
	 *  Parse the XML file, get a Document object and create a Workflow instance from the Document
	 * @param filePath
	 * @return
	 */
	public static WorkflowInstance instantiate(String filePath){
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

        return null;
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
	public static boolean invokeProgrammerMethod(Token t, String packageName, String className, String methodName, Class<?> parameters){
		return false;
	}
	
	/**
	 * Send an email notification to the user types specified in the XML file by the workflow programmer
	 * The notify function can extend further to create a notification system on the user interface. 
	 * This will be done as time permits.
	 * @return
	 */
	public static void notifyUser(){
		return;
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
	public static List<Form> getForms(WorkflowInstance wfi) {
	    List<Form> forms = new ArrayList<>();
	    wfi.getCurrentStates().forEach(state -> forms.addAll(state.getForms()));
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
