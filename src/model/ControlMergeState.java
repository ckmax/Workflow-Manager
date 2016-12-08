package model;

import java.util.ArrayList;

public class ControlMergeState extends State {
	
	//Parrallel states that merge into this state
    ArrayList<State> PreviousStates;
    //Check if tokens in previous states can move
    boolean checkPreviousStatesAndSeeIfCanMerge();
    void ReduceTokensAndMove();

}
