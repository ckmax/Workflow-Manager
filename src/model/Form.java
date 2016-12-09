package model;

import java.util.List;

public class Form {

	private String name;
	private String status;
    private State belongsTo;
    private List<Field> fields;

    public Form(State state, List<Field> fields, String name) {
        this.belongsTo = state;
        this.fields = fields;
        this.name = name;
        status = "Not started";
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public State belongsTo () {
        return belongsTo;
    }
}
