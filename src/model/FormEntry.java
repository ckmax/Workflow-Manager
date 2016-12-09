package model;

public class FormEntry {

	private String name;
	private String status;
	
	public FormEntry(String name) {
		
		this.name = name;
		this.status = "Not started";
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
