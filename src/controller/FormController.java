package controller;

import application.WorkflowManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Field;

import java.net.URL;
import java.util.ResourceBundle;

public class FormController implements Initializable {

	@FXML AnchorPane formAP;
	
	
	//Gonna receive a list of fields, and know what kind of user this is 
	public void populateForm(){
		
		//
		
				
		final VBox vbox = new VBox();
	    final HBox hbox = new HBox();
	    
	    
	    for(Field f : MultipleFormController.selectedForm.getFields()){

	    	Label t = new Label(f.getName());
	    	TextField tf = new TextField();
	    	tf.setId(f.getName());
	    	
	    	vbox.getChildren().addAll(t,tf);
	    }
	    
	    Button save = new Button("Save");
	    Button complete = new Button("Complete");
	    Button cancel = new Button("Cancel");
	    
	    //Set click handlers
	    	    
	    save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
	    		vbox.getChildren().forEach(node -> {
	    			if(node instanceof TextField){
	    				String name = ((TextField)node).getId();
	    				MultipleFormController.selectedForm.getField(name).setValue(((TextField)node).getText());
	    			}
	    		});

				WorkflowManager.getWorkflowInstance(
						Integer.parseInt(DashboardController.selectedWorkflowEntry.getId())).
						getForms().forEach(form -> {
					if (form.equals(MultipleFormController.selectedForm)) {
						for (int i = 0; i < form.getFields().size(); i++) {
						    form.getFields().get(i).setValue(
						            MultipleFormController.selectedForm.getFields().get(i).getValue());
                        }
					}
				});

            	MultipleFormController.formStage.close();
            	 	
            }
        });
	    
	    //MultipleFormController.selectedForm.getFields().forEach( f -> {
    	//	System.out.println(f.getName() + " " + f.getValue());
    	//});
	    
	    complete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	MultipleFormController.selectedForm.setCompleted();
				WorkflowManager.getWorkflowInstance(
						Integer.parseInt(DashboardController.selectedWorkflowEntry.getId())).
						getForms().forEach(form -> {
							if (form.equals(MultipleFormController.selectedForm)) {
								form.setCompleted();
							}
				});
            	int i = MultipleFormController.data.indexOf(MultipleFormController.selectedForm);
            	MultipleFormController.data.remove(i);
            	MultipleFormController.data.add(MultipleFormController.selectedForm);
//            	System.out.println(MultipleFormController.selectedForm.isCompleted());
            	
            	MultipleFormController.formStage.close();
            }
        });
	    
	    
	    cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	MultipleFormController.formStage.close();
            }
        });
	    
	    hbox.setSpacing(5);
	    hbox.getChildren().addAll(save, complete, cancel);
	    
	    vbox.setSpacing(5);
	    vbox.setPadding(new Insets(20, 0, 0, 20));
	    vbox.getChildren().addAll(hbox);
	    
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
