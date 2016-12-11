package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Derek on 12/11/16.
 */
public class MergeState extends State implements Serializable {

    private final String pairedStateID;

    public MergeState(String id, String userType, List<Form> forms, String pairedStateID) {
        super(id, userType, forms);
        this.pairedStateID = pairedStateID;
    }

    @Override
    public boolean canLeave(WorkflowInstance wfi) {
        if (this.done(wfi) && ((MergeState) wfi.getWorkflowStructure().getState(this.pairedStateID)).done(wfi)) {
            return true;
        }
        return false;
    }

    private boolean done(WorkflowInstance wfi) {
        return super.canLeave(wfi);
    }

    @Override
    public List<State> getNextStates(WorkflowInstance wfi) {
        return null;
    }
}
