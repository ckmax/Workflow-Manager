package model;

import java.util.List;

public class Form {

    private State belongsTo;
    private List<Field> fields;

    public Form(State state, List<Field> fields) {
        this.belongsTo = state;
        this.fields = fields;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public State belongsTo () {
        return belongsTo;
    }
}
