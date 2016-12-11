package model;

import java.io.Serializable;
import java.util.List;

public class Connection implements Serializable {
	
	private String id;
	private List<State> origins;
	private List<State> destinations;

	public Connection(String id, List<State> origins, List<State> destinations) {
	    this.id = id;
	    this.origins = origins;
	    this.destinations = destinations;
    }

    public String getId() {
        return id;
    }

    public List<State> getOrigins() {
        return origins;
    }

    public List<State> getDestinations() {
        return destinations;
    }
}
