package model;

import java.io.Serializable;

/**
 * Created by Derek on 12/11/16.
 */
public class BranchState extends State implements Serializable {

    public BranchState(String id, String name, String userType) {
        super(id, name, userType);
    }
}
