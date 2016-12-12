package model;

import java.io.Serializable;

public class Field implements Serializable {
    private String name;
    private String value;
    private String type;

    public Field(String name, String type) {
        this.name = name;
        this.value = "";
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Field)) {
            return false;
        }
        Field field = (Field) obj;
        return this.name.equals(field.name) && this.type.equals(field.type);
    }
}
