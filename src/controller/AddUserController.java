package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.UserEntry;

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
		
		UserEntry user = new UserEntry(nameTF.getText(), (String)usertypeCB.getValue(), emailTF.getText());
		AdminController.userEntryList.add(user);
		
		
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
