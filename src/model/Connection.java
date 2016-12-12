package model;

import java.io.Serializable;

public class Connection implements Serializable {
	
	private String id;
	private State origin;
	private State destination;

	public Connection(String id, State origin, State destination) {
	    this.id = id;
	    this.origin = origin;
	    this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public State getOrigin() {
        return origin;
    }

    public State getDestination() {
        return destination;
    }
}
