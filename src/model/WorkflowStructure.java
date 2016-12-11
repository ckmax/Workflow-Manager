package model;

import java.io.Serializable;
import java.util.HashMap;

public class WorkflowStructure implements Serializable {
	
    private HashMap<String, State> stateHashMap;

    private HashMap<String, Connection> connectionHashMap;

    public WorkflowStructure(String[] stateIDs, State[] states, String[] connectionIDs, Connection[] connections) {
        stateHashMap = new HashMap<>();
        connectionHashMap = new HashMap<>();

        for (int i = 0; i < states.length; i++) {
            stateHashMap.put(stateIDs[i], states[i]);
        }

        for (int i = 0; i < connections.length; i++) {
            connectionHashMap.put(connectionIDs[i], connections[i]);
        }
    }

    public State getState(String id) {
        return stateHashMap.get(id);
    }

    public Connection getConnection(String id) {
        return connectionHashMap.get(id);
    }
}
