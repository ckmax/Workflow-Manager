package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author Capki Kim
 *
 * The login controller will simply have two input fields for the username and password, and a button to login the user.
 */

public class LoginController implements Initializable {
	
	@FXML private TextField username;
	@FXML private TextField password;
	
	public static Stage adminStage;
	public static Stage dashboardStage;
	
	/**
	 * The username/password combination of admin/admin will always initialize the adminController
	 * Otherwise, call checkUser()
	 * if true, initialize the dashboardController
	 */
	public void loginButton(){
		
			if(username.getText().equals("admin") && password.getText().equals("admin")){
				
				try{
					FXMLLoader loader = new FXMLLoader();
					
					loader.setLocation(getClass().getResource("/view/Admin.fxml"));
					
					AnchorPane root = (AnchorPane)loader.load();
					
					Stage stage = new Stage();
					
					Scene scene = new Scene(root);
					
					stage.setScene(scene);
					stage.setResizable(false);
					stage.setTitle("Admin Page");
					
					adminStage = stage;
					
					stage.show(); // Pop-up admin stage
					
					Main.loginStage.close(); // Close login stage
					
				} catch (IOException e){
					e.printStackTrace();
				}
				
				
			}else{
			
				//TODO: CHECK USER IN USERLIST
				
				try{
					FXMLLoader loader = new FXMLLoader();
					
					loader.setLocation(getClass().getResource("/view/Dashboard.fxml"));
					
					AnchorPane root = (AnchorPane)loader.load();
					
					Stage stage = new Stage();
					
					Scene scene = new Scene(root);
					
					stage.setScene(scene);
					stage.setResizable(false);
					stage.setTitle("Codeflow");
					
					dashboardStage = stage;
					
					stage.show(); // Pop-up dashboard stage
					
					Main.loginStage.close(); // Close login stage
					
				} catch (IOException e){
					e.printStackTrace();
				}
				
			}
		
		return;
	}
	
	/**
	 * Checks if the user passed in as the parameter is an existing user
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUser(String username, String password){
		return false;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
}
