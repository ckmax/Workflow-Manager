package application;

import model.User;
import model.WorkflowInstance;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public final class UserManager {

	private static HashMap<String, User> userHashMap = null;

	/**
	 * Create new User
     * @param name person's name
	 * @param username username
	 * @param password password
	 * @param userType user type
     * @param email email address
     * @return 0 if successful
     *         1 if there's already a user with the same username
     *         2 if password is illegal
	 */
	public static void createUser(String name, String username, String password, String userType, String email)
        throws DuplicateUserException,
            IllegalPasswordException
    {
        if (userHashMap == null) {
            userHashMap = new HashMap<>();
        }

        if (userHashMap.containsKey(username)) {
            throw new DuplicateUserException();
        }

        if (isIllegalPassword(password)) {
            throw new IllegalPasswordException();
        }

        User newUser = new User(name, username, password, userType, email);
        userHashMap.put(username, newUser);
	}
	
	/**
	 * Remove user
	 * @param username username
	 * @return 0 if successful
     *         1 if no user has the provided username
     *         -1 if there's no user at all
	 */
	public static void removeUser(String username)
        throws EmptyDatabaseException,
            UserNotFoundException
    {
		if (userHashMap == null) {
            throw new EmptyDatabaseException();
        }

        if (!userHashMap.containsKey(username)) {
            throw new UserNotFoundException();
        }

        userHashMap.remove(username);
	}
	
	/**
	 * Edit a current user's general information
     * @param name person's name
	 * @param username username
	 * @param userType user type
	 * @param email email address
	 * @return 0 if successful
     *         1 if no user has the provided username
     *         -1 if there's no user at all
	 */
	public static void editInfo(String name, String username, String userType, String email)
        throws EmptyDatabaseException,
            UserNotFoundException
    {
		if (userHashMap == null) {
            throw new EmptyDatabaseException();
        }

        if (!userHashMap.containsKey(username)) {
			throw new UserNotFoundException();
		}

		User user = userHashMap.get(username);
		user.resetName(name);
		user.resetUserType(userType);
		user.resetEmail(email);
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
	public static void editPassword(String username, String oldPassword, String newPassword)
        throws EmptyDatabaseException,
            UserNotFoundException,
            IncorrectPasswordException
    {
        if (userHashMap == null) {
            throw new EmptyDatabaseException();
        }

        if (!userHashMap.containsKey(username)) {
            throw new UserNotFoundException();
        }

        if (!userHashMap.get(username).resetPassword(oldPassword, newPassword)) {
            throw new IncorrectPasswordException();
        }
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
	public static User login(String username, String password)
        throws EmptyDatabaseException,
            UserNotFoundException,
            LoginStatusMismatchException,
            IncorrectPasswordException
    {
        if (userHashMap == null) {
            throw new EmptyDatabaseException();
        }

        if (!userHashMap.containsKey(username)) {
            throw new UserNotFoundException();
        }

	    User user = userHashMap.get(username);

        if (user.isLoggedIn()) {
            throw new LoginStatusMismatchException();
        }

        if (!user.login(password)) {
            throw new IncorrectPasswordException();
        }

        return user;
	}
	
	/**
	 * Logout
	 * @param username username
	 * @return 0 if successful
     *         1 if no user has the provided username
     *         3 if the user is not logged in
     *         -1 if there's no user at all
	 */
	public static void logout(String username)
        throws EmptyDatabaseException,
            UserNotFoundException,
            LoginStatusMismatchException
    {
        if (userHashMap == null) {
            throw new EmptyDatabaseException();
        }

        if (!userHashMap.containsKey(username)) {
            throw new UserNotFoundException();
        }

		User user = userHashMap.get(username);

        if (!user.isLoggedIn()) {
            throw new LoginStatusMismatchException();
        }

        user.logout();
	}

    /**
     * Return all possible user types
     * @param filePath the path of the xml file
     * @return List of String of user types
     */
	public List<String> getUserTypes(String filePath) {
        List<String> userTypeList = new ArrayList<>();
        try {
            File xmlFile = new File(filePath);

            SAXBuilder saxBuilder = new SAXBuilder();

            Document document = saxBuilder.build(xmlFile);

            Element workflowElement = document.getRootElement();

            Element users = workflowElement.getChild("users");

            List<Element> userTypes = users.getChildren();

            for (Element element : userTypes) {
                userTypeList.add(element.getChild("name").getText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userTypeList;
    }

    public static Collection<User> getInvolvesIn(WorkflowInstance wfi) {
	    Collection<User> users = new ArrayList<>();
	    userHashMap.forEach((s, user) -> {
	        if (user.getInvolvesIn().contains(wfi)) {
	            users.add(user);
            }
        });
	    return users;
    }

    public static Collection<User> getTypeOf(String userType) {
        Collection<User> users = new ArrayList<>();
        userHashMap.forEach((s, user) -> {
            if (user.getUserType().equals(userType)) {
                users.add(user);
            }
        });
        return users;
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
