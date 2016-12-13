package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EditUserController implements Initializable {
	
	@FXML private TextField nameTF;
	@FXML private ComboBox usertypeCB;
	@FXML private TextField emailTF;
	
	
	/**
	 * 	Updates the dashboard
	 * @throws IOException 
	 */
	public void editUserBtn() throws IOException{
		
		//Load secondary controller
		
		FXMLLoader loader = new FXMLLoader();
				
		loader.setLocation(getClass().getResource("/view/Admin.fxml"));
		
		AnchorPane root = (AnchorPane) loader.load();
		
		AdminController controller = loader.getController();
		
		//Retrieve values
		String name = nameTF.getText();
		
		String type = (String)usertypeCB.getValue();
		
		System.out.println(name + " " + type);
		
		controller.editUser(name, AdminController.userToBeEdited.getUsername(),type,emailTF.getText());				
		
		AdminController.editUserStage.close();
	}
	
	public void cancelBtn(){
		
		
		AdminController.editUserStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		usertypeCB.setItems(AdminController.usertypeList);
		
		nameTF.setText(AdminController.userToBeEdited.getName());
		emailTF.setText(AdminController.userToBeEdited.getEmail());
		usertypeCB.setValue(AdminController.userToBeEdited.getUserType());
        
       		
	}

}
