package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkflowStructure {
	
	//Key value is name of state
    //Second value is state associated with that name
    HashMap<String, State> States;
    //String is connection name key and Connection is actual connection
    HashMap<String, Connection> Connections;
    //A list of specific start states
    ArrayList<StartState> StartStates;

}
