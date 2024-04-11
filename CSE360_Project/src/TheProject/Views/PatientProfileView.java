package TheProject.Views;

import TheProject.SceneViewer;
import TheProject.FileHandling.FileHandler;
import TheProject.Records.*;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class PatientProfileView extends BorderPane {
	public PatientProfileView(SceneViewer sceneViewer, EmailRecords emailRecords, PatientRecords patientRecords, String username) {
		
		super();
		
		// Labels
		Label titleMessage = new Label("My Profile");
	    Label myFirstName = new Label("First Name: ");
	    Label myLastName = new Label("Last Name: ");
	    Label myDOB = new Label("Date of Birth: ");
	    Label myPassword = new Label("Password: ");
	    Label errorMessage = new Label("");
	    
	    // original Patient data
	    String oldFirstName = patientRecords.searchPatient(username).getFirstName();
	    String oldLastName = patientRecords.searchPatient(username).getLastName();
	    String oldDOB = patientRecords.searchPatient(username).getDOB();
	    String oldPassword = patientRecords.searchPatient(username).getPass();
	    
	    //Text Fields from Patient data
	    TextField myFirstNameField = new TextField(oldFirstName);
	    TextField myLastNameField = new TextField(oldLastName);
	    TextField myDOBField = new TextField(oldDOB);
	    TextField myPasswordField = new TextField(oldPassword);
	    
	    //Buttons 
	    Button myProfileConfirmButtonPV = new Button("Confirm");

	    //HBox
	    HBox FirstNameHBox = new HBox(10);
	    FirstNameHBox.getChildren().addAll(myFirstName, myFirstNameField);
	    HBox LastNameHBox = new HBox(10);
	    LastNameHBox.getChildren().addAll(myLastName, myLastNameField);
	    HBox dobHBox = new HBox(10);
	    dobHBox.getChildren().addAll(myDOB, myDOBField);
	    HBox passwordHBox = new HBox(10);
	    passwordHBox.getChildren().addAll(myPassword, myPasswordField);

	    //VBox
	    VBox myProfileVBoxPV = new VBox(10);
	    myProfileVBoxPV.getChildren().addAll(titleMessage, FirstNameHBox, LastNameHBox, dobHBox, passwordHBox, myProfileConfirmButtonPV);
	    myProfileVBoxPV.setPadding(new Insets(20));
	    
	    super.setCenter(myProfileVBoxPV);
	    // myProfileVBoxPV.setAlignment(Pos.CENTER);
	    
	    myProfileConfirmButtonPV.setOnAction(e -> {
	    	
	    	// get all new fields
	    	String firstName = myFirstNameField.getText();
	    	String lastName = myLastNameField.getText();
	    	String dob = myDOBField.getText();
	    	String password = myPasswordField.getText();
	    	
	    	// enforce not empty
	    	if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || password.isEmpty()) {
	    		
	    		errorMessage.setText("Cannot leave text boxes blank.");
	    	} else {
	    		
	    		// update patient profile in file
	    		String fileName = "UserRecords/Patients/PatientInfo/" + username + "_info.txt";
	    		
	    		FileHandler.FileReplace(fileName, oldFirstName, firstName + "~" + lastName + "~" + username + "~" + password + "~" + dob + "~" + patientRecords.searchPatient(username).getInsuranceType());
	    		patientRecords.searchPatient(username).setFirstName(firstName);
	    		
	    		patientRecords.searchPatient(username).setlastName(lastName);
	    		
	    		patientRecords.searchPatient(username).setDOB(dob);
	    		
	    		patientRecords.searchPatient(username).setPass(password);
	    		
	    		sceneViewer.changeView(new PatientView(sceneViewer, emailRecords, patientRecords, username));
	    	}
	    });
	}
}