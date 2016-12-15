package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import application.UserManager;
import application.WorkflowManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import model.User;
import model.WorkflowEntry;

public class NewMsgController implements Initializable {

	@FXML private ComboBox usertypeCB;
	
	ObservableList<User> data = FXCollections.observableArrayList(); 
	
	public void sendBtn(){
		
	}
	
	public void cancelBtn(){
		DashboardController.sendMsgStage.close();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Collection<User> c = UserManager.getUsers(WorkflowManager.getWorkflowInstance(Integer.parseInt(DashboardController.selectedWorkflowEntry.getId())));

		data.addAll(c);
		
		usertypeCB.setItems(data);
	}

}
