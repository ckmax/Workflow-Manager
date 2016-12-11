package model;

import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Token {
	
	WorkflowInstance workflowInstance;
	
	//Current state token is in
    State CurrentState;
    //All forms associated with token at the moment
    //Each form associated with state
    HashMap<State, Form> allForms;

    //Submit forms for this token
    boolean SubmitForm(String stateID, Form form){
    	for(State state : this.allForms.keySet()){
    		if(state.name_id.equals(stateID)){
    			this.allForms.put(state, form);
    			return true;
    		}
    	}
    	return false;
    }
    
    //clear all forms
    void ClearForms(){
    	for(State state : this.allForms.keySet()){
			this.allForms.put(state, null);		
    	}
    }

    boolean checkIfCanMoveToken(){
    	if(this.allForms.get(this.CurrentState) != null){
    		return true;
    	}
    	return false;
    }
    
    boolean carryOutMove(){
    	if(this.checkIfCanMoveToken()){
    		//TODO actually carry out move
    		//also need to check if token moves into terminate state
    		return true;
    	}
    	return false;
    }
    
    public Token(WorkflowInstance workflowinstance, State state){
    	this.workflowInstance = workflowinstance;
    	this.CurrentState = state;
    	this.allForms = new HashMap<State, Form>();
    	//TODO from workflow instance get workflow structure to get all states and populate allForms hashmap with State keys but null forms
    	
    }

}