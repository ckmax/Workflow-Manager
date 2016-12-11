package model;

import java.util.ArrayList;
import java.util.List;

public class ControlMergeState extends State {
	
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
//		Form createFormForThisState(){
//			List<Field> fields = new ArrayList<Field>();
//			for(Entry<String, String> entry : this.requiredForm.FormFields.entrySet()){
//				Field newField = new Field(entry.getKey(), null, entry.getValue());
//				fields.add(newField);
//			}
//			return new Form(fields);
//		}
		
		//Only one next state for Mergre state
		State nextState;
		
		//Other states this merge has to know about
		List<State> otherMergeStates;
		
		List<State> getOtherMergeStates(){
			return this.otherMergeStates;
		}
		
		public ControlMergeState(String nameid, FormType requiredForm, boolean isStartState){
			this.name_id = nameid;
			this.requiredForm = requiredForm;
			this.isStartState = isStartState;
		}
		
		public void setNextState(State state){
			this.nextState = state;
		}
		
		public void setOtherMergeStates(List<State> mergeStates){
			this.otherMergeStates = mergeStates;
		}
		

}