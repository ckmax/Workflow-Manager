package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkflowInstance implements Serializable {

    private final int id;

    private final WorkflowStructure workflowStructure;

    private Set<State> currentStates;

    private List<Form> forms;

    public WorkflowInstance(WorkflowStructure workflowStructure, int id) {
        this.workflowStructure = workflowStructure;
        this.id = id;
        this.currentStates = new HashSet<>();
        this.forms = new ArrayList<>();
    }

    public WorkflowStructure getWorkflowStructure() {
        return workflowStructure;
    }

    public int getId() {
        return id;
    }

    public Set<State> getCurrentStates() {
        return currentStates;
    }

    public void nextStates() {
        Set<State> nextStates = new HashSet<>();
        this.currentStates.forEach(state -> nextStates.addAll(state.getNextStates(this)));
        this.currentStates = nextStates;
    }

    public List<Form> getForms() {
        return forms;
    }
}
