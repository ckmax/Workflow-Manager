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
import javafx.scene.control.TextArea;
import model.User;

public class NewMsgController implements Initializable {

	@FXML private ComboBox usertypeCB;
	@FXML private TextArea msg;
	
	ObservableList<User> data = FXCollections.observableArrayList(); 
	
	public void sendBtn(){
				
		WorkflowManager.notifyUser(LoginController.currentUser,(User)usertypeCB.getSelectionModel().getSelectedItem(), msg.getText());
		
		DashboardController.sendMsgStage.close();
		
	}
	
	public void cancelBtn(){
		DashboardController.sendMsgStage.close();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Collection<User> c = UserManager.getUsers(SelectStructureController.workflowStructure);

		data.addAll(c);
		
		usertypeCB.setItems(data);
	}

}
