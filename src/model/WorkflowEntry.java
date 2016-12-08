package model;

public class WorkflowEntry {

	private String name;
	private String info;
	
	public WorkflowEntry(String name, String info){
		this.name = name;
		this.info = info;
	}
	
	public WorkflowEntry(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getInfo(){
		return info;
	}
	
	public void setInfo(String info){
		this.info = info;
	}
}
