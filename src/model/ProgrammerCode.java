package model;

import java.io.Serializable;

/**
 * Created by Derek on 12/11/16.
 */
public class ProgrammerCode implements Serializable {
    private String packageName;
    private String className;
    private String methodName;
    private String stateID; // invoke in this state

    public ProgrammerCode(String packageName, String className, String methodName, String stateID) {
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.stateID = stateID;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getStateID() {
        return stateID;
    }
}
