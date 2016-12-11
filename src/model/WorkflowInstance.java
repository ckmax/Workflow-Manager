package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkflowInstance implements Serializable {

    private final int id;

    private final WorkflowStructure workflowStructure;

    private List<State> currentStates;

    private List<Form> forms;

    private List<Token> tokens;

    public WorkflowInstance(WorkflowStructure workflowStructure, int id) {
        this.workflowStructure = workflowStructure;
        this.id = id;
        this.currentStates = new ArrayList<>();
        this.forms = new ArrayList<>();
        this.tokens = new ArrayList<>();
    }

    public WorkflowStructure getWorkflowStructure() {
        return workflowStructure;
    }

    public int getId() {
        return id;
    }

    public List<State> getCurrentStates() {
        return currentStates;
    }

    public List<Form> getForms() {
        return forms;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
