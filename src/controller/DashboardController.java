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
import javafx.stage.WindowEvent;
import model.Form;
import model.State;
import model.User;
import model.WorkflowEntry;
import model.WorkflowInstance;

/**
 * 
 * @author Capki Kim
 * 
 * The main interface for the average user will consist of a dashboard that lists 
 * all the workflows that that user is involved in. If the user is an ¡°initializer¡±, 
 * or a person that can instantiate a workflow such as a student requesting an SPN number, 
 * that user will have a request button on the interface. The workflow programmer will 
 * specify whether a certain user type will have the functionality to start a workflow and 
 * also to be able to approve/reject incoming forms. 
 */

public class DashboardController implements Initializable {

	@FXML private TableView tableView;
	@FXML private Text workflowNameTF;
	@FXML private Text usernameTF;
	@FXML private Button initializeBtn;
	@FXML private Button removeBtn;
	
	public static WorkflowEntry selectedWorkflowEntry;
	
	public static Stage multipleFormStage;
	
	
	ObservableList<WorkflowEntry> data = FXCollections.observableArrayList();
	
	ObservableList<WorkflowEntry> tempData = FXCollections.observableArrayList();
	
	State currentStateOfUser = null;
	
	String currentStatesNames = "";

	
	/**
	 * The button to instantiate the workflow call instantiate() from workflowManager class
	 * with the returned workflow object, either initialize the formController 
	 * written by the workflow programmer or some other controller that is written by the programmer
	 */
	public void initializeBtn(){
		
		Integer workflowInstanceId = WorkflowManager.instantiate(LoginController.currentUser, SelectStructureController.workflowStructure);
		
		WorkflowInstance wfi = WorkflowManager.getWorkflowInstance(workflowInstanceId);
		
		
		WorkflowEntry wfe = new WorkflowEntry(wfi.getId() + "", wfi.getWorkflowStructure().getFirstState().getName());
		
		
		data.add(wfe);
		
	}
	
	/**
	 * 
	 */
	
	public void initializeForm(){
			
			try{
				FXMLLoader loader = new FXMLLoader();
				
				loader.setLocation(getClass().getResource("/view/MultipleForm.fxml"));
				
				BorderPane root = (BorderPane)loader.load();
				
				Stage stage = new Stage();
				
				Scene scene = new Scene(root);
				
				stage.setScene(scene);
				stage.setTitle("Codeflow");
				
				multipleFormStage = stage;
				
				multipleFormStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
					initializeTable();
					updateTable();
				});
				
							
				stage.show(); // Pop-up form stage
							
			} catch (IOException e){
				e.printStackTrace();
			}
		
	}
	
	
	/**
	 * Initialize the table on the dashboard
	 */
	public void initializeTable(){
	   
		List<WorkflowInstance> wfiList = LoginController.currentUser.getInvolvesIn();
		
		
		
		for(WorkflowInstance w : wfiList){
			currentStateOfUser = null;
			w.getCurrentStates().forEach(state -> {
				if(state.getUserType().equals(LoginController.currentUser.getUserType())){
					currentStateOfUser = state;
				}
			});
			
			if(currentStateOfUser != null){
			
				WorkflowEntry wfe = new WorkflowEntry(w.getId() + "", currentStateOfUser.getName());
				
				data.add(wfe);
			} else {
				w.getCompletedStates().forEach(state -> {
					if(state.getUserType().equals(LoginController.currentUser.getUserType())){
						currentStatesNames = "";
						w.getCurrentStates().forEach(state1 -> {
							currentStatesNames += state1.getName() + " ";
						});
						if(currentStatesNames.equals("")){
							currentStatesNames = "Completed";
						}
						
						WorkflowEntry wfe1 = new WorkflowEntry(w.getId() + "", currentStatesNames);
						if(!data.contains(wfe1)){
							data.add(wfe1);
						}
					}
				});
			}
			
		}
		
		
		// Set the event when mouse is double clicked
	   tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {		            
		            
		        	selectedWorkflowEntry = (WorkflowEntry) tableView.getSelectionModel().getSelectedItem();
		        	initializeForm();
		            
		        }
		    }
		});
		
		tableView.setItems(data);
	}
	
	/**
	 * 	Calls logOut() from workflowManager class & returns to the log-in page=
	 */
	public void logOutBtn(){
		
		try{
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			
			Stage stage = new Stage();
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Codeflow");
			
			SelectStructureController.loginStage = stage;
						
			stage.show(); // Pop-up login stage
			
			LoginController.dashboardStage.close(); // Close dashboard stage
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void updateTable(){
	
		//Way to update the table after editing
		
		for(WorkflowEntry e : data){
			tempData.add(e);
		}
		
		data.removeAll(data);
		
		for(WorkflowEntry e : tempData){
			data.add(e);
		}
		
		tempData.removeAll(tempData);
		
	}

	/**
	 * Removes the selected entry from the list
	 */
	
	public void removeBtn(){
		
		WorkflowEntry entryToBeRemoved = (WorkflowEntry) tableView.getSelectionModel().getSelectedItem();
		
		data.remove(entryToBeRemoved);
		
	}
	
	/**
	 * When a user approves or ¡®passes¡¯ an instance, it will call transition() from the workflowManager class
	 * the transition may switch the interface to the next instance of a form if a state receives multiple forms 
	 * or just simply move the token(s) if there is only one form.
	 */
	public void passBtn(){
		
	}
	
	/**
	 * 	When a user rejects or ¡®stops¡¯ an instance, it will call endWorkflow() from the workflowManager class
	 */
	public void stopBtn(){
		
	}
	
	public void initTableCol(){
		// Create column entries from the start 
		TableColumn workflowName = new TableColumn("ID");
		
		workflowName.setCellValueFactory(
                new PropertyValueFactory<WorkflowEntry, String>("id"));
		
		TableColumn info = new TableColumn("Current State");
		
	   info.setCellValueFactory(
                new PropertyValueFactory<WorkflowEntry, String>("currentState"));
       
		tableView.getColumns().addAll(workflowName,info);
	}

	public boolean checkUser(User user){
		
		return SelectStructureController.workflowStructure.getFirstState().getUserType().equals(user.getUserType());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		usernameTF.setText(LoginController.currentUser.getUsername());
		workflowNameTF.setText(SelectStructureController.selectedStructure);
		
		//Initialize the new/remove buttons
		if(!checkUser(LoginController.currentUser)){
			
			initializeBtn.setVisible(false);
			removeBtn.setVisible(false);
			
		}
		
		initTableCol();
		
		//Populate Table
		initializeTable();

	}
	
}
