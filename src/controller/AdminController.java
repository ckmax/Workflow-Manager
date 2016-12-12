package controller;

import application.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 
 * @author Capki Kim
 *
 * The admin page will have a dashboard of all the instantiated users that have access to the workflow system. 
 * This interface will consist of three buttons that will either add, remove, or edit a user. 
 * This dashboard will later be upgraded as time permits to allow additional functionalities 
 * such as a profile page when you double click on a user.
 *
 */

public class AdminController implements Initializable {
	
	public static Stage editUserStage;
	public static Stage addUserStage;
	public static Stage changePasswordStage;
	
	public static User userToBeEdited;
	
	public static boolean initialized = false;
		
	@FXML
	private TableView adminTV;
	
	@FXML  Text workflowName;
	
	public static ObservableList<User> userList = FXCollections.observableArrayList();
	
	public  ObservableList<User> tempUserList = FXCollections.observableArrayList();

	
	public static ObservableList<String> usertypeList = FXCollections.observableArrayList();
	
	/**
	 * Initializes editUserController
	 */
	public void editUserBtn(){
		
		userToBeEdited = (User) adminTV.getSelectionModel().getSelectedItem();
		
		if(userToBeEdited != null){
		
			try{
				
				FXMLLoader loader = new FXMLLoader();
				
				loader.setLocation(getClass().getResource("/view/EditUser.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				
				Stage stage = new Stage();
				
				Scene scene = new Scene(root);
				
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Codeflow");
				
				editUserStage = stage;
				
				stage.show(); // Pop-up editUser stage
				
				
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 	Initializes addUserController
	 */
	public void addUserBtn(){
		
		try{
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/view/Adduser.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			
			Stage stage = new Stage();
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Codeflow");
			
			addUserStage = stage;
			
			stage.show(); // Pop-up addUser stage
						
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	public void changePasswordBtn(){
		
		userToBeEdited = (User) adminTV.getSelectionModel().getSelectedItem();
		
		if(userToBeEdited != null){
		
			try{
				
				FXMLLoader loader = new FXMLLoader();
				
				loader.setLocation(getClass().getResource("/view/ChangePassword.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				
				Stage stage = new Stage();
				
				Scene scene = new Scene(root);
				
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Codeflow");
				
				changePasswordStage = stage;
				
				stage.show(); // Pop-up editUser stage
				
				
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * Removes selected user from the user list
	 */
	public void removeUserBtn(){
		
		User entryToBeRemoved = (User) adminTV.getSelectionModel().getSelectedItem();
		
		userList.remove(entryToBeRemoved);
		
	}
	
	/**
	 * Exit to the login page
	 */
	public void logoutBtn(){
		try{
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			
			Stage stage = new Stage();
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Codeflow");
						
			stage.show(); // Pop-up login stage
			
			LoginController.adminStage.close(); // Close admin stage
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void editUser(String name, String username, String usertype, String email){
		
		if(userToBeEdited == null){
			System.out.println("entry is null");
		}
		
		System.out.println("name: " + name + " type: " + usertype);
		System.out.println("Original name: " + userToBeEdited.getName() + " type: " + userToBeEdited.getUserType());


		UserManager.editInfo(name, username, usertype, email);
		
		userList.get(userList.indexOf(userToBeEdited)).resetEmail(email);
		userList.get(userList.indexOf(userToBeEdited)).resetName(name);
		userList.get(userList.indexOf(userToBeEdited)).resetUserType(usertype);
		
		System.out.println("New name: " + userToBeEdited.getName() + " New Type: " + userToBeEdited.getUserType());
		
		//Way to update the table after editing
		
		for(User u : userList){
			tempUserList.add(u);
		}
		
		userList.removeAll(userList);
		
		for(User e : tempUserList){
			userList.add(e);
		}
		
		tempUserList.removeAll(tempUserList);
		
	}
	
	public void setTableColumns(){
		// Create column entries from the start 
		TableColumn userName = new TableColumn("Name");
		
		userName.setCellValueFactory(
                new PropertyValueFactory<User, String>("name"));
		
		TableColumn role = new TableColumn("Role");
		
	   role.setCellValueFactory(
                new PropertyValueFactory<User, String>("userType"));
	   	   	
	   	TableColumn email = new TableColumn("Email");
		
	   email.setCellValueFactory(
                new PropertyValueFactory<User, String>("email"));
	   
	   adminTV.getColumns().addAll(userName,role,email);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
				
		   	setTableColumns();
		   	
		   	//Set workflow name
			workflowName.setText(SelectStructureController.selectedStructure);
	 		
			
		   	// Upload current users
			// Get the user types associated with the current workflow structure
			List<String> userTypes = UserManager.getUserTypes("../Sample/" + SelectStructureController.selectedStructure + ".xml");
	 		
	 		// Add user types
	 		for(String s : userTypes){
	 			System.out.println(s);
	 			if(!usertypeList.contains(s)){
	 				AdminController.usertypeList.add(s);
	 			}
	 		}
	 		
	 		
 		//Sample user
 		//UserManager.createUser("John Park", "park123" , "413414", "Student", "john@gmail.com");
 		
 		
 		// Find all the users associated with that workflow structure using the user types
	 		
	 	if(!initialized){
	 		for(int i = 0 ; i < usertypeList.size() ; i++){
	 			
	 			try{
		 			Collection<User> users = UserManager.getTypeOf(usertypeList.get(i));
		 				 			
		 			for(User user : users){
		 				userList.add(user);
		 			}
	 			}catch(NullPointerException e){
	 			}
	 			
	 		}
	 		
	 		initialized = true;
	 	}
 		
 		//Link observableList to table view
	   	adminTV.setItems(userList);
	}

}
