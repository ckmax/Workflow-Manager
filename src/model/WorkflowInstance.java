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

    private Set<State> completedStates;

    private List<Form> forms;

    public WorkflowInstance(WorkflowStructure workflowStructure, int id, State startState) {
        this.workflowStructure = workflowStructure;
        this.id = id;
        this.currentStates = new HashSet<>();
        this.currentStates.add(startState);
        this.completedStates = new HashSet<>();
        this.forms = new ArrayList<>();
        startState.getForms().forEach(form -> this.forms.add(form.deepClone()));
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

    public Set<State> getCompletedStates() {
        return completedStates;
    }

    public void nextStates() {
        Set<State> nextStates = new HashSet<>();
        this.currentStates.forEach(state -> nextStates.addAll(state.getNextStates(this)));
        completedStates.addAll(this.currentStates);
        this.currentStates = nextStates;
        nextStates.forEach(state -> state.getForms().forEach(form -> this.forms.add(form.deepClone())));
    }

    public List<Form> getForms() {
        return forms;
    }
}
