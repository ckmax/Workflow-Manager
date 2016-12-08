package model;

import java.util.List;

public class User {

	private String username;
    private String password;
    private String userType;
    private String email;
    private boolean loggedIn;
    private List<WorkflowInstance> involvesIn;

    public User(String username, String password, String userType, String email) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.email = email;
        this.loggedIn = false;
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
