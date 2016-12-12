package model;

import java.io.Serializable;

public class Connection implements Serializable {
	
	private String id;
	private String origin;
	private String destination;

	public Connection(String id, String origin, String destination) {
	    this.id = id;
	    this.origin = origin;
	    this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public State getOrigin(WorkflowStructure wfs) {
        return wfs.getState(this.origin);
    }

    public State getDestination(WorkflowStructure wfs) {
        return wfs.getState(this.destination);
    }
}
