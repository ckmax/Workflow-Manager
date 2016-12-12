package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.IncorrectPasswordException;
import application.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ChangePasswordController implements Initializable{
	
	@FXML TextField newPasswordTF;
	@FXML TextField oldPasswordTF;
	@FXML TextField confirmTF;
	@FXML Text errorMsg;
	
	public void cancelBtn(){
		
		AdminController.changePasswordStage.close();
	}
	
	public void changePasswordBtn(){
		
		if(newPasswordTF.getText().isEmpty() || confirmTF.getText().isEmpty()){
			errorMsg.setText("Enter a password");
		}else if(newPasswordTF.getText().equals(confirmTF.getText())){
			try{
				UserManager.editPassword(AdminController.userToBeEdited.getUsername(), oldPasswordTF.getText(), newPasswordTF.getText());
			}catch(IncorrectPasswordException e){
				errorMsg.setText("Incorrect Password");
			}
		}else{
			errorMsg.setText("Confirm your password correctly");
		}
		
		AdminController.changePasswordStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
