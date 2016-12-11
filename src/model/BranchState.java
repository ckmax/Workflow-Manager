package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Derek on 12/11/16.
 */
public class BranchState extends State implements Serializable {

    public BranchState(String id, String userType, List<Form> forms) {
        super(id, userType, forms);
    }

    @Override
    public List<State> getNextStates(WorkflowInstance wfi) {
        return null;
    }
}
