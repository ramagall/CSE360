 package TheProject.Views;

import TheProject.Records.*;
import TheProject.Users.*;

import java.util.ArrayList;

import TheProject.SceneViewer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateAccountView extends VBox {
	
	public CreateAccountView(SceneViewer sceneViewer, PatientRecords patientRecords, EmailRecords emailRecords, Patient newPatient){
	super();
	
	Label addAccountWelcome = new Label("Welcome! Please complete the following form.");
	
	Label CAIS_ERROR = new Label();
    
    TextField firstName = new TextField();
    firstName.setPromptText("First Name");
    TextField lastName = new TextField();
    lastName.setPromptText("Last Name");
    TextField insurance = new TextField ();
    insurance.setPromptText("Enter insurance type");
    
    Label dobTitle = new Label("Enter Date of Birth:");
    TextField month = new TextField();
    month.setPromptText("MM");
    TextField day = new TextField();
    day.setPromptText("DD");
    TextField year = new TextField();
    year.setPromptText("YYYY");
    
    
    
    TextFormatter<String> formatter = new TextFormatter<>(change -> {
        if (change.isDeleted()) {
            return change; // Allow deletion of characters
        }

        // Reject non-numeric characters
        if (!change.getControlNewText().matches("\\d*")) {
            return null;
        }
        

        // Limit the length
        int maxLength = 2; // Set your desired maximum length here
        if (change.getControlNewText().length() > maxLength) {
            return null;
        }
        
        try {
            int monthValue = Integer.parseInt(change.getControlNewText());
            if (monthValue < 1 || monthValue > 12) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }

        return change;
    });
    
    TextFormatter<String> formatter2 = new TextFormatter<>(change -> {
        if (change.isDeleted()) {
            return change; // Allow deletion of characters
        }

        // Reject non-numeric characters
        if (!change.getControlNewText().matches("\\d*")) {
            return null;
        }
        

        // Limit the length
        int maxLength = 2; // Set your desired maximum length here
        if (change.getControlNewText().length() > maxLength) {
            return null;
        }
        
        try {
            int monthValue = Integer.parseInt(change.getControlNewText());
            if (monthValue < 1 || monthValue > 31) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }

        return change;
    });
    
    TextFormatter<String> formatter3 = new TextFormatter<>(change -> {
        if (change.isDeleted()) {
            return change; // Allow deletion of characters
        }

        // Reject non-numeric characters
        if (!change.getControlNewText().matches("\\d*")) {
            return null;
        }
        

        // Limit the length
        int maxLength = 4; // Set your desired maximum length here
        if (change.getControlNewText().length() > maxLength) {
            return null;
        }
        
        try {
            int yearValue = Integer.parseInt(change.getControlNewText());
            if (yearValue < 0 || yearValue > 2024) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }

        return change;
    });
    
    month.setTextFormatter(formatter);
	month.textProperty().addListener((observable, oldValue, newValue) -> {
		
		 int max = 10; // Set your desired maximum length here

		    if (newValue.length() > max) {
		        // Trim the text to the maximum length
		        String trimmedText = newValue.substring(0, max);
		        month.setText(trimmedText);
		    }
		
		
	});
	
	day.setTextFormatter(formatter2);
	day.textProperty().addListener((observable, oldValue, newValue) -> {
		
		 int max = 10; // Set your desired maximum length here

		    if (newValue.length() > max) {
		        // Trim the text to the maximum length
		        String trimmedText = newValue.substring(0, max);
		        day.setText(trimmedText);
		    }
		
		
	});
	
	year.setTextFormatter(formatter3);
	year.textProperty().addListener((observable, oldValue, newValue) -> {
		
		 int max = 10; // Set your desired maximum length here

		    if (newValue.length() > max) {
		        // Trim the text to the maximum length
		        String trimmedText = newValue.substring(0, max);
		        year.setText(trimmedText);
		    }
		
		
	});

    HBox dateOfBirth = new HBox(10);
    dateOfBirth.setPadding(new Insets(20));
    dateOfBirth.getChildren().addAll(month, day, year);

    Button caisEnterButton = new Button("Enter Info");
    
    caisEnterButton.setOnAction(e -> {
        String patient_name = firstName.getText() + lastName.getText();
        
        String m = month.getText();
        String d = day.getText();
        String y = year.getText();
        String patient_dob = m + "/" + d + "/" + y;

        if (patient_name.equals("")) {
        	CAIS_ERROR.setText("Please Enter first name and last name");
        }
        else if (m.equals("")) {
        	CAIS_ERROR.setText("Invalid Birth Month. Please Use (MM)");
        }
        else if (d.equals("")) {
        	CAIS_ERROR.setText("Invalid Birth Day. Please Use (DD)");
        }
        else if (y.equals("")) {
        	CAIS_ERROR.setText("Invalid Birth Year. Please Use (YYYY)");
        }
        else {

        	//input data into text file
        	
        	newPatient.setFirstName(firstName.getText());
        	newPatient.setlastName(lastName.getText());
        	newPatient.setDOB(patient_dob);
        	newPatient.setInsuranceType(insurance.getText());
        	
        	patientRecords.createNewPatient(newPatient);
        	ArrayList<Email> startList = new ArrayList<Email>();
        	emailRecords.inboxList.put(newPatient.getUser(), startList);
        	emailRecords.outboxList.put(newPatient.getUser(), startList);
        	
          sceneViewer.changeView(new PatientView(sceneViewer, emailRecords, patientRecords, newPatient.getUser()));
        }
      });
    
    super.setPadding(new Insets(20));
    super.getChildren().addAll(addAccountWelcome, firstName, lastName, insurance, dobTitle, dateOfBirth, caisEnterButton, CAIS_ERROR);
	}
    
}