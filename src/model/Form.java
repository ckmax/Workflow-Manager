package model;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.List;

public class Form implements Serializable{

    private String id;
	private String name;
	private String status;
    private String stateID;
    private boolean completed;
    private List<Field> fields;

    public Form(String id, String name, String stateID, List<Field> fields) {
        this.id = id;
        this.name = name;
        this.stateID = stateID;
        this.completed = false;
        this.fields = fields;
        this.status = "Not started";
    }

    public String getId() {
        return id;
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

    public Field getField(String fieldName) {
        for (Field field : this.fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
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

    public String belongsTo () {
        return this.stateID;
    }

    public Form deepClone() {
        return SerializationUtils.clone(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Form)) {
            return false;
        }

        Form form = (Form) obj;

        if (this.fields.size() != form.fields.size()) {
            return false;
        }

        for (int i = 0; i < this.fields.size(); i++) {
            if (!this.fields.get(i).equals(form.fields.get(i))) {
                return false;
            }
        }

        return true;
    }
}
