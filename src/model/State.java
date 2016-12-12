package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class State implements Serializable {
	
	protected String id;
	protected String name;
	protected String userType;
    protected List<Form> forms;
    protected List<ProgrammerCode> programmerCodes;

    public State(String id, String name, String userType) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.forms = new ArrayList<>();
        this.programmerCodes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }

    public List<Form> getForms() {
        return forms;
    }

    public List<ProgrammerCode> getProgrammerCodes() {
        return programmerCodes;
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

    public Set<State> getNextStates(WorkflowInstance wfi) {
        return wfi.getWorkflowStructure().getNextStates(this.id);
    }
}
