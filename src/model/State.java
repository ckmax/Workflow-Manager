package model;

import java.sql.Connection;
import java.util.ArrayList;

public class State {
	
	String name_id;
    //A hashmap of all connections coming off this state. String key is connection name_id
	Hashmap<String, Connection>ConnectionsLeaving;
	//A hashmap of all connections coming into this state. String key is connection name_id    
	Hashmap<String, Connection>;
	//All tokens within this state
    ArrayList<Token> Tokens;
    //Check to see if token can be moved from one state to another
    boolean checkIfCanMoveToken();


}
