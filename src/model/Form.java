package model;

import java.util.List;

public class Form {

    private String name;
    private String status;
    private State belongsTo;
    private boolean completed;
    private List<Field> fields;

    public Form(String name, State state, List<Field> fields) {
        this.name = name;
        this.belongsTo = state;
        this.completed = false;
        this.fields = fields;
        this.status = "Not started";
    }

    public String getName() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public State belongsTo () {
        return belongsTo;
    }
}
