package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.User;

public class AddUserController implements Initializable {
	
	@FXML private TextField usernameTF;
	@FXML private TextField passwordTF;
	@FXML private TextField nameTF;
	@FXML private ComboBox usertypeCB;
	@FXML private TextField emailTF;
	
	/**
	 * 	Calls createUser() from workflowManager and updates the dashboard
	 */
	public void addUserBtn(){
		
		// TODO: ERROR CHECK
		
		User user = new User(nameTF.getText(), usernameTF.getText(), passwordTF.getText(), (String)usertypeCB.getValue(), emailTF.getText());
		
		UserManager.createUser(nameTF.getText(), usernameTF.getText(), passwordTF.getText(), (String)usertypeCB.getValue(), emailTF.getText());
		
		// Create a UserEntry and add it to the observable list
		AdminController.userList.add(user);		
		
	    //AdminController.adminTV.setItems(AdminController.userList);
		
		//Close current stage
		AdminController.addUserStage.close();
		
	}
	
	
	public void cancelBtn(){
		
		//Close current stage
		AdminController.addUserStage.close();
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	        
	     usertypeCB.setItems(AdminController.usertypeList);
		
	}

}
