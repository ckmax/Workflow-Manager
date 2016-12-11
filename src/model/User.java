package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name; // person's name
	private String username;
    private String password;
    private String userType;
    private String email;
    private List<String> messages;
    private boolean loggedIn;
    private List<WorkflowInstance> involvesIn;

    public User(String name, String username, String password, String userType, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.email = email;
        messages = new ArrayList<>();
        this.loggedIn = false;
        involvesIn = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<WorkflowInstance> getInvolvesIn() {
        return this.involvesIn;
    }

    public void resetName(String name) {
        this.name = name;
    }

    public boolean resetPassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }

    public void resetUserType(String userType) {
        this.userType = userType;
    }

    public void resetEmail(String email) {
        this.email = email;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void addWorkflow(WorkflowInstance wfi) {
        involvesIn.add(wfi);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean login(String password) {
        if (this.password.equals(password)) {
            this.loggedIn = true;
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        this.loggedIn = false;
    }
    
}
