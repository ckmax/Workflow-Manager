package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class SelectStructureController implements Initializable {
	
	@FXML ListView selectStructureLV;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Check all the workflow structure files in the default directory
		File folder = new File("your/path");	
		File[] listOfFiles = folder.listFiles();
		List<String> workflowStructureNames = new ArrayList<String>();
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  workflowStructureNames.add(listOfFiles[i].getName());
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		}

	}

}
