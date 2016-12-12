package model;

import java.io.Serializable;

/**
 * Created by Derek on 12/11/16.
 */
public class LinearState extends State implements Serializable {

    public LinearState(String id, String name, String userType) {
        super(id, name, userType);
    }
}
