package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Form;
import model.User;
import model.WorkflowInstance;

public class MultipleFormController implements Initializable {
	
	@FXML private TableView multipleFormTV;
	@FXML private Button submitB;
	@FXML private Text errorMsg;
	public static Stage formStage;
	public static Form selectedForm;
	
	
	public static ObservableList<Form> data = FXCollections.observableArrayList();
	
	public  ObservableList<Form> tempFormList = FXCollections.observableArrayList();
	
	boolean canSubmit = false;
	
	String currentStatesNames;
	
	public void setUpFormTable(){
				
		//Call getForms from the WorkflowManager
		
		WorkflowInstance wfi = WorkflowManager.getWorkflowInstance(Integer.parseInt(DashboardController.selectedWorkflowEntry.getId()));
		
		//List<Form> forms = WorkflowManager.getForms(LoginController.currentUser, wfi);
		
		List<Form> forms = wfi.getForms();
		
		if(!data.isEmpty()){
			data.removeAll(data);
		}
		
		// Add the forms to the observable list
		for(Form f : forms){
			data.add(f);
		}
		
		setUpColNames();
	   
	   
	   // Set the event when mouse is double clicked
	   multipleFormTV.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {	
		        	selectedForm = (Form) multipleFormTV.getSelectionModel().getSelectedItem();
		            showForm();
		        }
		    }
		});
				
		
	}
	
	public void updateTable(){
		
		//Way to update the table after editing
		
		for(Form f : data){
			tempFormList.add(f);
		}
		
		data.removeAll(data);
		
		for(Form f : tempFormList){
			data.add(f);
		}
		
		tempFormList.removeAll(tempFormList);
		
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
	
	public void submitBtn(){
		
		if(!WorkflowManager.transition(WorkflowManager.getWorkflowInstance(Integer.parseInt(DashboardController.selectedWorkflowEntry.getId())))){
			
			//Error Message
			errorMsg.setText("Please complete all the forms");
			
			
		}else{
			/*
			currentStatesNames = "";
			w.getCurrentStates().forEach(state1 -> {
				currentStatesNames += state1.getName() + " ";
			});
			if(currentStatesNames.equals("")){
				currentStatesNames = "Completed";
			}*/
			
			/*
			try{
        		
            	//Load secondary controller
        		
        		FXMLLoader loader = new FXMLLoader();
        				
        		loader.setLocation(getClass().getResource("/view/Dashboard.fxml"));
        		
        		AnchorPane root = (AnchorPane) loader.load();
        		
        		DashboardController controller = loader.getController();
        		
        		System.out.println("REACH");
        		controller.initializeTable();
    		
        	}catch(Exception e){
        		e.printStackTrace();
        	}*/
			
			DashboardController.multipleFormStage.close();
			
		}
		
		
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		submitB.setVisible(false);
		
		WorkflowManager.getWorkflowInstance(Integer.parseInt(DashboardController.selectedWorkflowEntry.getId())).getCurrentStates().forEach(state -> {
			if(state.getUserType().equals(LoginController.currentUser.getUserType())) {
				submitB.setVisible(true);
			}
		});
		
		setUpFormTable();
	
	}

}
