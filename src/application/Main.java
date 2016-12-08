package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
	public static Stage loginStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Load FXML file	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Photo Album Log in");
			primaryStage.setResizable(false); 
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			loginStage = primaryStage;
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
