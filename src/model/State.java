package model;

import java.io.Serializable;
import java.util.List;

public abstract class State implements Serializable {
	
	protected String id;
	protected String userType;
    protected List<Form> forms;

    public State(String id, String userType, List<Form> forms) {
        this.id = id;
        this.userType = userType;
        this.forms = forms;
    }

    public String getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }

    public List<Form> getForms() {
        return forms;
    }

    public boolean canLeave(WorkflowInstance wfi) {
        for (Form form : wfi.getForms()) {
            if (!form.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public boolean canEnter(WorkflowInstance wfi) {
        return true;
    }

    public abstract List<State> getNextStates(WorkflowInstance wfi);
}
