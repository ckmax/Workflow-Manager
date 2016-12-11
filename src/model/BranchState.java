package model;

import java.util.List;

/**
 * Created by Derek on 12/11/16.
 */
public class BranchState extends State {

    public BranchState(String id, String userType, List<Form> forms) {
        super(id, userType, forms);
    }

    @Override
    public boolean checkIfCanEnter() {
        return true;
    }
}
