package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Derek on 12/11/16.
 */
public class SelectionState extends State implements Serializable {

    private final String selectFieldName;
    private HashMap<String, String> selects;

    public SelectionState(String id, String name, String userType, String fieldName) {
        super(id, name, userType);
        this.selectFieldName = fieldName;
        this.selects = new HashMap<>();
    }

    public void addSelect(String value, String connectionID) {
        this.selects.put(value, connectionID);
    }

    @Override
    public Set<State> getNextStates(WorkflowInstance wfi) {
        Set<State> nextStates = new HashSet<>();
        List<Form> forms = wfi.getForms();
        Form lastForm = forms.get(forms.size() - 1);
        nextStates.add(wfi.getWorkflowStructure().getConnection(selects.get(lastForm.getField(selectFieldName).getValue())).getDestination(wfi.getWorkflowStructure()));
        return nextStates;
    }
}
