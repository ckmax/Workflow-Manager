package model;

public class WorkflowEntry {

	private String id;
	private String currentState;
	private String action;
	
	public WorkflowEntry(String id, String currentState, String action){
		this.id = id;
		this.currentState = currentState;
		this.action = action;
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
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof WorkflowEntry)) {
            return false;
        }

        WorkflowEntry workflowEntry = (WorkflowEntry) obj;

        return this.getId().equals(workflowEntry.getId());
    }


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}
}
