package model;

import java.util.ArrayList;
import java.util.List;

public class WorkflowInstance {

    private List<Token> allTokens;
    private WorkflowStructure structure;

    public WorkflowInstance(WorkflowStructure structure, int workflowID) {
       this.structure = structure;
       this.allTokens = new ArrayList<Token>();
       //TODO put token in start state for workflow
       
       
    }
    
    //TODO implement this method that will get all tokens in a said state by state name
    public List<Token> getTokensInState(String stateID){
    	return null;
    }
    
    //TODO implement this method to get all token in a state by actual state instance
    public List<Token> getTokensInState(State state){
    	return null;
    }

}