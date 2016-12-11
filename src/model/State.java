package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public abstract class State {
	
	//form required to move to next state
    FormType requiredForm;

    //unique id
	String name_id;
    
	//boolean to denote whether this is start state
	boolean isStartState;
	
    //gets all the required forms
    Form createFormForThisState(){
    	List<Field> fields = new ArrayList<Field>();
    	for(Entry<String, String> entry : this.requiredForm.FormFields.entrySet()){
    		Field newField = new Field(entry.getKey(), null, entry.getValue());
    		fields.add(newField);
    	}
    	return new Form(fields);
    }
    
    
    
}