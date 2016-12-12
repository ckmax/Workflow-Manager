package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.UserManager;
import application.WorkflowManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SelectStructureController implements Initializable {
	
	@FXML ListView selectStructureLV;
	
	public static Stage loginStage;
	
	public static String selectedStructure;
	
	public static final String filePath = "../Sample/";
	
	ObservableList<String> structures = FXCollections.observableArrayList ();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Check all the workflow structure files in the default directory
		File folder = new File("../Sample/");	
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  structures.add(listOfFiles[i].getName().substring(0, (listOfFiles[i].getName().length() - 4) ) );
		      }
		}
		
		selectStructureLV.setItems(structures);
		
		selectStructureLV.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	if(event.getClickCount() == 2){
	        		selectedStructure = (String)selectStructureLV.getSelectionModel().getSelectedItem();
	        		
	        		try{
						FXMLLoader loader = new FXMLLoader();
						
						loader.setLocation(getClass().getResource("/view/Login.fxml"));
						
						AnchorPane root = (AnchorPane)loader.load();
						
						Stage stage = new Stage();
						
						Scene scene = new Scene(root);
						
						stage.setScene(scene);
						stage.setResizable(false);
						stage.setTitle("Login");
						
						loginStage = stage;
						
						stage.show(); // Pop-up login stage
						
						Main.selectStructureStage.close(); // Close selectStructure stage
						
	        		} catch (IOException e){
						e.printStackTrace();
					}
	        		
	        	}
	        }
	    });
	}

}
