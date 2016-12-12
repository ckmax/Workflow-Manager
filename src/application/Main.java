package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Capki, David, Derek, Julius
 *
 * Import the workflow structure into the system from a file by workflow programmer
 */

public class Main extends Application {
	
	/**
	 * Stores the login stage
	 */
	public static Stage selectStructureStage;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			UserManager.createUser("Cap", "1", "1", "Student", "max@gmail.com");
			
			// Load FXML file	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SelectStructure.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Workflow Selection");
			primaryStage.setResizable(false); 
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			selectStructureStage = primaryStage;

            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop(){
		
		WorkflowManager.saveWorkflowData();
        UserManager.saveUserData();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
