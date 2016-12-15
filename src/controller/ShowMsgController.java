package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Message;

public class ShowMsgController implements Initializable {
	
	@FXML AnchorPane showAP;

	public void showMsg(){
		
		final VBox vbox = new VBox();
		
		for(Message m : LoginController.currentUser.getMessages()){

 	    	Label t = new Label(m.toString());
// 	    	Text text = new Text();
// 	    	text.setText(m.getMessage());
 	    	
 	    	vbox.getChildren().addAll(t);
		}
		
		vbox.setSpacing(5);
	    vbox.setPadding(new Insets(20, 0, 0, 20));
		showAP.getChildren().add(vbox);

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		showMsg();
	
	}

}
