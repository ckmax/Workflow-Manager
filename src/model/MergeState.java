package model;

import java.io.Serializable;

/**
 * Created by Derek on 12/11/16.
 */
public class MergeState extends State implements Serializable {

    private final String pairedStateID;

    public MergeState(String id, String name, String userType, String pairedStateID) {
        super(id, name, userType);
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

    public String getPairedStateID() {
        return pairedStateID;
    }
}
