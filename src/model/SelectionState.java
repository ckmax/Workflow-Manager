package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Derek on 12/11/16.
 */
public class SelectionState extends State implements Serializable {

    public SelectionState(String id, String userType, List<Form> forms, List<ProgrammerCode> programmerCodes) {
        super(id, userType, forms, programmerCodes);
    }

    @Override
    public Set<State> getNextStates(WorkflowInstance wfi) {
        Set<State> optionalNextStates = wfi.getWorkflowStructure().getNextStates(this.id);
        List<Form> forms = wfi.getForms();
        Form lastForm = forms.get(forms.size() - 1);
        State nextState = wfi.getWorkflowStructure().getState(lastForm.getFields().get(0).getValue());
        if (!optionalNextStates.contains(nextState)) {
            throw new RuntimeException("Intended Next State is not available.\n");
        }
        optionalNextStates.clear();
        optionalNextStates.add(nextState);
        return optionalNextStates;
    }
}
