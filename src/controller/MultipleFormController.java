package controller;

import application.WorkflowManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Form;
import model.WorkflowInstance;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MultipleFormController implements Initializable {
	
	@FXML private TableView multipleFormTV;
	@FXML private Button submitB;
	public static Stage formStage;
	
	ObservableList<Form> data = FXCollections.observableArrayList();
	
	public void setUpFormTable(){
		
		LoginController.currentUser.getInvolvesIn();
		
		//Call getForms from the WorkflowManager
		
		WorkflowInstance wfi = WorkflowManager.getWorkflowInstance(Integer.parseInt(DashboardController.selectedWorkflowEntry.getId()));

		List<Form> forms = WorkflowManager.getForms(LoginController.currentUser, wfi);
		
		//Turn forms to FormEntries
		
			
		data.addAll();
		
		setUpColNames();
	   
	   
	   // Set the event when mouse is double clicked
	   multipleFormTV.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {		            
		            showForm();
		            
		        }
		    }
		});
				
		
	}
	
	public void setUpColNames(){
		// Create column entries from the start 
		TableColumn formName = new TableColumn("Name");
		
		formName.setCellValueFactory(
                new PropertyValueFactory<Form, String>("name"));
		
		formName.setPrefWidth(222.0);
		
		TableColumn formStatus = new TableColumn("Completed");
		
	   formStatus.setCellValueFactory(
                new PropertyValueFactory<Form, Boolean>("completed"));
	   
	   formStatus.setPrefWidth(105.0);
	   
	   multipleFormTV.getColumns().addAll(formName,formStatus);
	   multipleFormTV.setItems(data);
	}
	
	public void showForm(){
		
		try{
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/view/Form.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			
			Stage stage = new Stage();
			
			formStage = stage;
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("Form");
						
			stage.show(); // Pop-up form stage
						
		} catch (IOException e){
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setUpFormTable();
	
	}

}
