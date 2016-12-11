package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derek on 12/5/16.
 */
public class SelectionState extends State {
	//INHERITED
	//form required to move to next state
	//FormType requiredForm;

    //unique id
	//String name_id;
    
	//boolean to denote whether this is start state
	//boolean isStartState;
	
	//gets all the required forms
    //ArrayList<FormType> getRequiredForms(){
    //	return this.requiredForms;
    //}
	
    //create form for this state
    //gets all the required forms
//	Form createFormForThisState(){
//		List<Field> fields = new ArrayList<Field>();
//		for(Entry<String, String> entry : this.requiredForm.FormFields.entrySet()){
//			Field newField = new Field(entry.getKey(), null, entry.getValue());
//			fields.add(newField);
//		}
//		return new Form(fields);
//	}
	
	Form stateChoiceForm;
	
	//Multiple next states
	List<State> nextStates;
	
	public SelectionState(String nameid, FormType requiredForm, boolean isStartState){
		this.name_id = nameid;
		this.requiredForm = requiredForm;
		this.isStartState = isStartState;
	}
	
	public void setNextStates(List<State> nextStates){
		this.nextStates = nextStates;
	}
	//TODO need a method to create a Form that allows a choice of state
}
