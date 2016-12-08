package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.WorkflowEntry;

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
	
	ObservableList<WorkflowEntry> data = FXCollections.observableArrayList();;
	
	/**
	 * The button to instantiate the workflow call instantiate() from workflowManager class
	 * with the returned workflow object, either initialize the formController 
	 * written by the workflow programmer or some other controller that is written by the programmer
	 */
	public void initializeBtn(){
		
		//Populate Table
		initializeTable();
		
		//Initialize form
		initializeForm();
		
	}
	
	/**
	 * 
	 */
	
	public void initializeForm(){
		
		try{
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/view/Form.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			
			Stage stage = new Stage();
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("Codeflow");
						
			stage.show(); // Pop-up form stage
						
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Initialize the table on the dashboard
	 */
	public void initializeTable(){
	   
	   // Adding a button to tableView
	   /*
	   TableColumn actionCol = new TableColumn( "Action" );
       actionCol.setCellValueFactory( new PropertyValueFactory<>( "DUMMY" ) );

       Callback<TableColumn<WorkflowEntry, String>, TableCell<WorkflowEntry, String>> cellFactory = //
               new Callback<TableColumn<WorkflowEntry, String>, TableCell<WorkflowEntry, String>>()
               {
                   public TableCell<WorkflowEntry, String> call( final TableColumn<WorkflowEntry, String> param )
                   {
                       final TableCell<WorkflowEntry, String> cell = new TableCell<WorkflowEntry, String>()
                       {

                           final Button btn = new Button( "Approve" );

                           @Override
                           public void updateItem( String item, boolean empty )
                           {
                               super.updateItem( item, empty );
                               if ( empty )
                               {
                                   setGraphic( null );
                                   setText( null );
                               }
                               else
                               {
                                   btn.setOnAction( ( ActionEvent event ) ->
                                           {
                                               WorkflowEntry person = getTableView().getItems().get( getIndex() );
                                               System.out.println( person.getName() );
                                   } );
                                   setGraphic( btn );
                                   setText( null );
                               }
                           }
                       };
                       return cell;
                   }
               };

       actionCol.setCellFactory( cellFactory );*/
		
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
						
			stage.show(); // Pop-up login stage
			
			LoginController.dashboardStage.close(); // Close dashboard stage
			
		} catch (IOException e){
			e.printStackTrace();
		}
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		// Create column entries from the start 
		TableColumn workflowName = new TableColumn("Name");
		
		workflowName.setCellValueFactory(
                new PropertyValueFactory<WorkflowEntry, String>("name"));
		
		TableColumn info = new TableColumn("Info");
		
	   info.setCellValueFactory(
                new PropertyValueFactory<WorkflowEntry, String>("info"));
	   
	   // Adding a button to tableView
	   
	   TableColumn actionCol = new TableColumn( "Action" );
       actionCol.setCellValueFactory( new PropertyValueFactory<>( "DUMMY" ) );
       
		tableView.getColumns().addAll(workflowName,info,actionCol);

	}
	
}
