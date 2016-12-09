package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.FormEntry;
import model.WorkflowEntry;

public class MultipleFormController implements Initializable {
	
	@FXML private TableView multipleFormTV;
	@FXML private Button submitB;
	
	ObservableList<FormEntry> data = FXCollections.observableArrayList();


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Call getForms from the WorkflowManager
		
		//Turn forms to FormEntries
		
		FormEntry formEntry1 = new FormEntry("Application");
		FormEntry formEntry2 = new FormEntry("Application2");
			
		data.addAll(formEntry1,formEntry2);
		
		// Create column entries from the start 
		TableColumn formName = new TableColumn("Name");
		
		formName.setCellValueFactory(
                new PropertyValueFactory<FormEntry, String>("Name"));
		
		formName.setPrefWidth(222.0);
		
		TableColumn formStatus = new TableColumn("Status");
		
	   formStatus.setCellValueFactory(
                new PropertyValueFactory<FormEntry, String>("Status"));
	   
	   formStatus.setPrefWidth(105.0);
	   
	   multipleFormTV.getColumns().addAll(formName,formStatus);
	   multipleFormTV.setItems(data);
		
	}

}
