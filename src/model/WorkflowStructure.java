
package model;

import java.util.HashMap;

public class WorkflowStructure {
	
	//Key value is name of state
    //Second value is state associated with that name
    HashMap<String, State> States;
    //A list of specific start states
    State StartState;

    //TODO IMPORTANT I DON'T KNOW HOW STATE INFORMATION IS BEING READ INTO THIS METHOD FROM XML PARSER --JULIUS
    public WorkflowStructure() {
    	//SHOULD SET START STATE
    	
    	//SHOULD INITIALIZE HASHMAP OF STATES AND SET UP HASHMAP
    	
    	//SHOULD SET NEXT STATE OF EACH STATE WITHIN WORKFLOWSTRUCTURE BY USING SETNEXTSTATE OR SETNEXTSTATES METHODS PROVIDED IN STATE CLASSES
    }

    public State getStartState() {
        return StartState;
    }
}
