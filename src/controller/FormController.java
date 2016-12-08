package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

public class FormController implements Initializable {

	@FXML AnchorPane formAP;
	
	
	//Gonna receive a list of fields, and know what kind of user this is 
	public void populateForm(){
		
		//
		
				
		final VBox vbox = new VBox();
	    final HBox hbox = new HBox();
	    
	    Label tf1Label = new Label("Name");
	    TextField tf1 = new TextField();
	    
	    Label tf2Label = new Label("Age");
	    TextField tf2 = new TextField();
	    
	    Label tf3Label = new Label("Job");
	    TextField tf3 = new TextField();
	    
	    Label tf4Label = new Label("Course Request");
	    TextField tf4 = new TextField();
	    
	    
	    Button submit = new Button("Submit");
	    Button cancel = new Button("Cancel");
	    
	    hbox.setSpacing(5);
	    hbox.getChildren().addAll(submit, cancel);
	    
	    vbox.setSpacing(5);
	    vbox.setPadding(new Insets(20, 0, 0, 20));
	    vbox.getChildren().addAll(tf1Label,tf1,tf2Label,tf2,tf3Label,tf3,tf4Label,tf4);
	    vbox.getChildren().addAll(hbox);
		//AnchorPane.setRightAnchor(vbox, 10.0);
		
		formAP.getChildren().add(vbox);
		
		/*
		//When you press submit, we can save the values of the form through this
		submit.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        System.out.println(columns.getText());
		    }
		});*/

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		populateForm();
		
	}
}
