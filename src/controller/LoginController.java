package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.EmptyDatabaseException;
import application.IncorrectPasswordException;
import application.LoginStatusMismatchException;
import application.UserManager;
import application.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @author Capki Kim
 *
 * The login controller will simply have two input fields for the username and password, and a button to login the user.
 */

public class LoginController implements Initializable {
	
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private Text errorMsg;
	
	public static String currentUser;
	
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
					
					SelectStructureController.loginStage.close(); // Close login stage
					
				} catch (IOException e){
					e.printStackTrace();
				}
				
				
			}else{
			
				try{
					UserManager.login(username.getText(), password.getText());
				}catch(EmptyDatabaseException e){
					
					errorMsg.setText("There is currently no existing user");
					return;
					
				}catch(UserNotFoundException e){
					
					errorMsg.setText("There is no user with that username/password combination");
					return;
					
				}catch(LoginStatusMismatchException e){
					
					errorMsg.setText("The user is already logged in.");
					return;

					
				}catch(IncorrectPasswordException e){
					
					errorMsg.setText("Incorrect password");
					return;
					
				}
				
				// If there are no errors, continue to this code
				
				currentUser = username.getText();
				
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
					
					SelectStructureController.loginStage.close(); // Close login stage
					
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
