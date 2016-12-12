package model;

import java.io.Serializable;
import java.util.*;

public class WorkflowStructure implements Serializable {
	
    private HashMap<String, State> stateHashMap;

    private HashMap<String, Connection> connectionHashMap;

    public WorkflowStructure(List<State> states,List<Connection> connections) {
        stateHashMap = new HashMap<>();
        connectionHashMap = new HashMap<>();
        states.forEach(state -> stateHashMap.put(state.getId(), state));
        connections.forEach(connection -> connectionHashMap.put(connection.getId(), connection));
    }

    public State getState(String id) {
        return stateHashMap.get(id);
    }

    public State getFirstState() {
        return stateHashMap.get("S1");
    }

    public Set<State> getNextStates(String currentStateID) {
        Set<State> nextStates = new HashSet<>();
        connectionHashMap.forEach((s, connection) -> {
            if (connection.getOrigin(this).getId().equals(currentStateID)) {
                nextStates.add(connection.getDestination(this));
            }
        });
        return nextStates;
    }

    public Connection getConnection(String id) {
        return connectionHashMap.get(id);
    }
}
