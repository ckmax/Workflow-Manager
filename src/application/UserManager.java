package application;

import model.User;

import java.util.HashMap;
import java.util.List;

public final class UserManager {

	private static HashMap<String, User> userHashMap = null;

	/**
	 * Create new User
	 * @param username username
	 * @param password password
	 * @param userType user type
     * @param email email address
     * @return 0 if successful
     *         1 if there's already a user with the same username
     *         2 if password is illegal
	 */
	public static int createUser(String username, String password, String userType, String email){
        if (userHashMap == null) {
            userHashMap = new HashMap<>();
        }

        if (userHashMap.containsKey(username)) {
            return 1;
        }

        if (isIllegalPassword(password)) {
            return 2;
        }

        User newUser = new User(username, password, userType, email);
        userHashMap.put(username, newUser);
        return 0;
	}
	
	/**
	 * Remove user
	 * @param username username
	 * @return 0 if successful
     *         1 if no user has the provided username
     *         -1 if there's no user at all
	 */
	public static int removeUser(String username){
		if (userHashMap == null) {
            return -1;
        }

        if (!userHashMap.containsKey(username)) {
            return 1;
        }

        userHashMap.remove(username);
        return 0;
	}
	
	/**
	 * Edit a current user's general information
	 * @param username username
	 * @param userType user type
	 * @param email email address
	 * @return 0 if successful
     *         1 if no user has the provided username
     *         -1 if there's no user at all
	 */
	public static int editInfo(String username, String userType, String email){
		if (userHashMap == null) {
            return -1;
        }

        if (!userHashMap.containsKey(username)) {
			return 1;
		}

		User user = userHashMap.get(username);
		user.resetUserType(userType);
		user.resetEmail(email);

        return 0;
	}

    /**
     * Change a user's password
     * @param username username
     * @param oldPassword old password
     * @param newPassword new password
     * @return 0 if successful
     *         1 if no user has the provided username
     *         2 if the password is wrong
     *         -1 if there's no user at all
     */
	public static int editPassword(String username, String oldPassword, String newPassword) {
        if (userHashMap == null) {
            return -1;
        }

        if (!userHashMap.containsKey(username)) {
            return 1;
        }

        if (!userHashMap.get(username).resetPassword(oldPassword, newPassword)) {
            return 2;
        }

        return 0;
    }
	
	/**
	 * Login
	 * @param username username
	 * @param password password
	 * @return 0 if successful
     *         1 if no user has the provided username
     *         2 if the password is wrong
     *         3 if the user is already logged in
     *         -1 if there's no user at all
	 */
	public static User int login(String username, String password){
        if (userHashMap == null) {
            return -1;
        }

        if (!userHashMap.containsKey(username)) {
            return 1;
        }

	    User user = userHashMap.get(username);

        if (user.isLoggedIn()) {
            return 3;
        }

        if (!user.login(password)) {
            return 2;
        }

        return 0;
	}

    /**
     *
     * @param username
     * @return
     */
	public static User getUser(String username) {

    }
	
	/**
	 * Logout
	 * @param username username
	 * @return 0 if successful
     *         1 if no user has the provided username
     *         3 if the user is not logged in
     *         -1 if there's no user at all
	 */
	public static int logout(String username){
        if (userHashMap == null) {
            return -1;
        }

        if (!userHashMap.containsKey(username)) {
            return 1;
        }

		User user = userHashMap.get(username);

        if (!user.isLoggedIn()) {
            return 3;
        }

        user.logout();
        return 0;
	}

    /**
     * Return all possible user types
     * @return
     */
	public List<String> getUserTypes() {

    }

    /**
     * Check whether the password is a legal password or not.
     * @param password the password to check
     * @return true if the given password is legal
     *         false if the given password is illegal
     */
	private static boolean isIllegalPassword(String password) {
        return false;
    }

    /**
     * Delete all user data
     */
    protected static void clearUserData() {
        userHashMap = null;
    }
}
