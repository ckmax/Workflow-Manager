package model;

public class WorkflowEntry {

	private String id;
	private String currentState;
	
	public WorkflowEntry(String id, String currentState){
		this.id = id;
		this.currentState = currentState;
	}
	
	
	public String getCurrentState(){
		return currentState;
	}
	
	public void setCurrentState(String name){
		this.currentState = name;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String info){
		this.id = info;
	}
}
