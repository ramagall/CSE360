package TheProject.Views;

import TheProject.Records.*;
import TheProject.Users.Patient;

import java.io.File;
import java.util.ArrayList;

import TheProject.SceneViewer;
import TheProject.FileHandling.FileHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DoctorView extends BorderPane{
	 private ListView<String> patientListDV;
	    private TextField searchedPatientFieldDV;
	    private TabPane patientDetailsTabsDV;
	    private ListView<String> inboxDV;
	    
	    
	    int day;
	    int month;
	    int year;
	    String user;
	public DoctorView(SceneViewer sceneViewer, EmailRecords emailRecords, DoctorRecords doctorRecords, PatientRecords patientRecords, String username) {
		super();
		
		Label welcomeDV = new Label("Doctor View.");
	    HBox titleBoxDV = new HBox(welcomeDV);

	    //Patients list
	    patientListDV = new ListView<>();
        for(String key: patientRecords.patientList.keySet())
        {
        	String name = patientRecords.searchPatient(key).getFirstName() + " " + patientRecords.searchPatient(key).getLastName();
        	String age = patientRecords.searchPatient(key).getDOB();
        	 user = patientRecords.searchPatient(key).getUser();
        	
        	String[] values = age.split("/");
        	  day = Integer.parseInt(values[0]);
              month = Integer.parseInt(values[1]);
              year = Integer.parseInt(values[2]);
        	
        	{
        	patientListDV.getItems().add(name);
        	}
        }
        searchedPatientFieldDV = new TextField();
        
        
        searchedPatientFieldDV.setPromptText("Search Patient");
        Button searchPatientDV = new Button("Search");
        Button doctorLogout = new Button("Logout");
        VBox searchPatientBoxDV = new VBox(searchedPatientFieldDV, patientListDV, searchPatientDV, doctorLogout);
        
        searchPatientDV.setOnAction(e -> {
            String searchText = searchedPatientFieldDV.getText().toLowerCase(); 
            patientListDV.getItems().clear();

      
            for (String key : patientRecords.patientList.keySet()) {
                String name = patientRecords.searchPatient(key).getFirstName() + " " + patientRecords.searchPatient(key).getLastName();
                if (name.toLowerCase().contains(searchText)) {
                    patientListDV.getItems().add(name);
                }
            }
        });

        // Tabs for patients - center
        patientDetailsTabsDV = new TabPane();

      //Patients - Inbox and Send Message
	    TabPane emailTabPaneDV = new TabPane();

	    //Inbox
	    Tab inboxTabDV = new Tab("Inbox");
	    inboxTabDV.setClosable(false);
	    ListView<String> inboxDV = new ListView<>();
	    
	    // Populate Inbox with Email Headers
	    ArrayList<Email> inbox = emailRecords.inboxList.get(username);
	    try {
	    	for (int i = 0; i < inbox.size(); i++) {
		    	
		    	Email currEmail = inbox.get(i);
		    	String urgent = currEmail.urgency.equals("True") ? " (Urgent)" : " (Not Urgent)";
		    	String header = currEmail.head + urgent;
		    	inboxDV.getItems().add(header);
		    }
	    } catch (NullPointerException e) {
	    	// do nothing (empty inbox)
	    }
	    inboxTabDV.setContent(inboxDV);
	    
	    // Outbox
	    Tab sentMessagesDV = new Tab("Sent");
	    sentMessagesDV.setClosable(false);
	    ListView<String> outboxDV = new ListView<>();
	    
	    // Populate Outbox with Email Headers
	    ArrayList<Email> outbox = emailRecords.outboxList.get(username);
	    try {
	    	for (int i = 0; i < outbox.size(); i++) {
		    	
		    	Email currEmail = outbox.get(i);
		    	String recipient = currEmail.intendedPerson;
		    	String header = "To: " + recipient + " - " + currEmail.head;
		    	outboxDV.getItems().add(header);
		    }
	    } catch (NullPointerException e) {
	    	// do nothing (outbox empty)
	    }
	    sentMessagesDV.setContent(outboxDV);

	    //Send a Message
	    Tab sendMessageTabDV = new Tab("Send a Message");
	    sendMessageTabDV.setClosable(false);

	    emailTabPaneDV.getTabs().addAll(inboxTabDV, sentMessagesDV, sendMessageTabDV);

	    Label usernameSendToDV = new Label("Send To:");
	    TextField usernameSendToDVField = new TextField();

	    // Send To Field
	    HBox user_sendToDV = new HBox(10);
	    user_sendToDV.setPadding(new Insets(20));
	    user_sendToDV.getChildren().addAll(usernameSendToDV, usernameSendToDVField);

	    // Header Field
	    TextField headerDV = new TextField();
	    headerDV.setPromptText("Header...");
	    
	    // Message Body Field
	    TextArea typeMessageDV = new TextArea();
	    typeMessageDV.setPromptText("Type your message here...");
	    typeMessageDV.setPrefRowCount(5);
	    typeMessageDV.setPrefColumnCount(1);
	    
	    // Check Urgent Field
	    CheckBox urgentDV = new CheckBox("Check if Urgent");
	    Button submitDV = new Button("Send Message");
	    Button cancelDV = new Button("Cancel");

	    HBox buttonsBoxDV = new HBox(10);
	    buttonsBoxDV.setPadding(new Insets(20));
	    buttonsBoxDV.getChildren().addAll(urgentDV, submitDV, cancelDV);

	    // Notification Label
	    Label notifLabel = new Label("");
	    
	    // Complete Send Message
	    VBox sendMessageDV = new VBox(10);
	    sendMessageDV.setPadding(new Insets(20));
	    sendMessageDV.getChildren().addAll(emailTabPaneDV, user_sendToDV, headerDV, typeMessageDV, buttonsBoxDV, notifLabel);

	    sendMessageTabDV.setContent(sendMessageDV);

        super.setTop(titleBoxDV);
        super.setLeft(searchPatientBoxDV);
        super.setCenter(patientDetailsTabsDV);
        super.setRight(emailTabPaneDV);

        doctorLogout.setOnAction(e -> {
            sceneViewer.setLoginView();
        });

	    
	    patientListDV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	updatePatientDetails(newValue, patientRecords);
            }
        });
	    
	    // Switch to Outbox
	    outboxDV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	viewOutboxEmailDetails(username, newValue, sceneViewer, doctorRecords, patientRecords, emailRecords);
            }
        });
	    
	    // Switch to Inbox
	    inboxDV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	viewInboxEmailDetails(username, newValue, sceneViewer, doctorRecords, patientRecords, emailRecords,
            			emailTabPaneDV, sendMessageTabDV, usernameSendToDVField, headerDV);
            }
        });
	    
	    // Send Email Button
	    submitDV.setOnAction(e -> {
	    	
	    	// Gather info from textfields
	    	String recipient = usernameSendToDVField.getText();
	    	String header = headerDV.getText();
	    	String body = typeMessageDV.getText().replace('\n', '_');
	    	String isUrgent = urgentDV.isSelected() ? "True" : "False";
	    	
	    	// Make email line
	    	String insertedEmail = username + "~" + recipient + "~" + isUrgent + "~" + header + "~" + body;
	    	
	    	// Write to Email Records
	    	ArrayList<Email> inTemp = emailRecords.inboxList.get(recipient);
	    	ArrayList<Email> outTemp = emailRecords.outboxList.get(username);
	    	Email insertEmail = new Email(recipient, username, isUrgent, header, body);
	    	inTemp.add(insertEmail);
	    	outTemp.add(insertEmail);
	    	emailRecords.inboxList.put(recipient, inTemp);
	    	emailRecords.outboxList.put(username, outTemp);
	    	
	    	// Write to Inbox and Outbox
	    	File infile = FileHandler.getFile(recipient + "_inbox", "Emails/Inbox");
			FileHandler.writeToFile(infile, insertedEmail);
			File outFile = FileHandler.getFile(username + "_outbox", "Emails/Outbox");
			FileHandler.writeToFile(outFile, insertedEmail);
	    	
			// Clean textfields 
	    	usernameSendToDVField.clear();
	    	usernameSendToDVField.setEditable(true);
	    	headerDV.clear();
	    	headerDV.setEditable(true);
	    	typeMessageDV.clear();
	    	urgentDV.setSelected(false);
	    	notifLabel.setText("Email successfully sent.");
	    });
	    
	    cancelDV.setOnAction(e -> {
	    	
	    	// Clean textfields 
	    	usernameSendToDVField.clear();
	    	usernameSendToDVField.setEditable(true);
	    	headerDV.clear();
	    	headerDV.setEditable(true);
	    	typeMessageDV.clear();
	    	urgentDV.setSelected(false);
	    	
	    	sceneViewer.changeView(new DoctorView(sceneViewer, emailRecords, doctorRecords, patientRecords, username));
	    });

	}
	
	public void viewInboxEmailDetails(String username, String newValue, SceneViewer sceneViewer, DoctorRecords doctorRecords, PatientRecords patientRecords, EmailRecords emailRecords,
			TabPane emailTabPanePV, Tab sendMessageTabPV, TextField usernameSendToPVField, TextField headerPV) {
		
		// Find Email: Derive Header
    	String[] info = newValue.split(" ");
    	StringBuilder header = new StringBuilder();
    	for (int i = 0; i < info.length - 1; i++) {
    		header.append(info[i]);
    	}
    	String theHeader = header.toString();
    	Email theEmail = new Email();
    	
    	// Find Email: Search Records
    	ArrayList<Email> log = emailRecords.inboxList.get(username);
    	for (int i = 0; i < log.size(); i++) {
    		
    		theEmail = log.get(i);
    		if (theEmail.head.equals(theHeader)) {
    			break;
    		}
    	}
    	
    	// From user UI
    	Label fromUser = new Label("From: ");
    	TextField fromUserField = new TextField();
    	fromUserField.setEditable(false);
    	fromUserField.setText(theEmail.sender);
    	
    	HBox fromUserUI = new HBox(10);
    	fromUserUI.setPadding(new Insets(20));
    	fromUserUI.getChildren().addAll(fromUser, fromUserField);
    	
    	// Email Header
    	TextField emailHeader = new TextField();
    	emailHeader.setEditable(false);
    	emailHeader.setText(theEmail.head);
    	
    	// Email Body
    	TextArea emailBody = new TextArea();
    	emailBody.setEditable(false);
    	emailBody.setText(theEmail.body.replace('_', '\n'));
    	
    	// Action Buttons
    	Button replyButton = new Button();
    	replyButton.setText("Reply...");
    	Button exitButton = new Button();
    	exitButton.setText("Exit Email");
    	
    	HBox actionButtons = new HBox(10);
    	actionButtons.setPadding(new Insets(20));
    	actionButtons.getChildren().addAll(replyButton, exitButton);
    	
    	// Email Details Screen
    	TabPane emailDetails = new TabPane();
    	Tab viewEmail = new Tab("Email from " + theEmail.sender);
    	viewEmail.setClosable(false);
    	viewEmail.setContent(new VBox(fromUserUI, emailHeader, emailBody, actionButtons));
    	
    	// Pass email to reply action
    	final Email passEmail = theEmail;
    	
    	emailDetails.getTabs().add(viewEmail);
    	super.setRight(emailDetails);
    	
    	exitButton.setOnAction(e -> {
    		sceneViewer.changeView(new DoctorView(sceneViewer, emailRecords, doctorRecords, patientRecords, username));
    		// Fixed the BUG
    	});
    	
    	replyButton.setOnAction(e -> {
    		
    		emailTabPanePV.getSelectionModel().select(sendMessageTabPV);
    		
    		usernameSendToPVField.setText(passEmail.sender);
    		usernameSendToPVField.setEditable(false);
    		String head = "Re: " + passEmail.head;
    		headerPV.setText(head);
    		headerPV.setEditable(false);
    		
    		super.setRight(emailTabPanePV);
    	});
	}
	
	public void viewOutboxEmailDetails(String username, String newValue, SceneViewer sceneViewer, DoctorRecords doctorRecords, PatientRecords patientRecords, EmailRecords emailRecords) {
		
		// Find Email: Derive Header
    	String[] info = newValue.split(" ");
    	StringBuilder header = new StringBuilder();
    	for (int i = 0; i < info.length - 1; i++) {
    		header.append(info[i]);
    	}
    	String theHeader = header.toString();
    	Email theEmail = new Email();
    	
    	// Find Email: Search Records
    	ArrayList<Email> log = emailRecords.outboxList.get(username);
    	for (int i = 0; i < log.size(); i++) {
    		
    		theEmail = log.get(i);
    		if (theEmail.head.equals(theHeader)) {
    			break;
    		}
    	}
    	
    	// From user UI
    	Label fromUser = new Label("To: ");
    	TextField fromUserField = new TextField();
    	fromUserField.setEditable(false);
    	fromUserField.setText(theEmail.sender);
    	
    	HBox fromUserUI = new HBox(10);
    	fromUserUI.setPadding(new Insets(20));
    	fromUserUI.getChildren().addAll(fromUser, fromUserField);
    	
    	// Email Header
    	TextField emailHeader = new TextField();
    	emailHeader.setEditable(false);
    	emailHeader.setText(theEmail.head);
    	
    	// Email Body
    	TextArea emailBody = new TextArea();
    	emailBody.setEditable(false);
    	emailBody.setText(theEmail.body.replace('_', '\n'));
    	
    	// Action Buttons
    	Button exitButton = new Button();
    	exitButton.setText("Exit Email");
    	
    	HBox actionButtons = new HBox(10);
    	actionButtons.setPadding(new Insets(20));
    	actionButtons.getChildren().addAll(exitButton);
    	
    	// Email Details Screen
    	TabPane emailDetails = new TabPane();
    	Tab viewEmail = new Tab("Email to " + theEmail.sender);
    	viewEmail.setClosable(false);
    	viewEmail.setContent(new VBox(fromUserUI, emailHeader, emailBody, actionButtons));
    	
    	emailDetails.getTabs().add(viewEmail);
    	super.setRight(emailDetails);
    	
    	exitButton.setOnAction(e -> {
    		sceneViewer.changeView(new PatientView(sceneViewer, emailRecords, patientRecords, username));
    		// Fixed the BUG
    	});
	}
	
	private void updatePatientDetails(String selectedPatient, PatientRecords patientRecords) {
        // Clear previous content
    	Patient thePatient = patientRecords.searchByName(selectedPatient);
    	String[] visit = new String[11];
    	visit[0] = thePatient.getUser();
    	for(int i = 1; i < visit.length; i++) {
    		visit[i] = "N/A";
    	}
    	
    	/*for(int i = 0; i < visit.length; i++) {
    		System.out.println(visit[i]);
    	}*/
        patientDetailsTabsDV.getTabs().clear();
        	
        // Vitals Tab
        Tab vitalsTabDV = new Tab("Vitals");

        // Patient Name Label
        Label nameLabel = new Label(selectedPatient);
        nameLabel.setStyle("-fx-font-weight: bold");
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        nameLabel.setAlignment(javafx.geometry.Pos.CENTER);

        // Text Fields for Vitals
        TextField dateField = new TextField();
        dateField.setPromptText("Data");
        TextField reasonField = new TextField();
        reasonField.setPromptText("Reason for visit");
        TextField weightField = new TextField();
        weightField.setPromptText("Weight");
        TextField temperatureField = new TextField();
        temperatureField.setPromptText("Temperature");
        TextField heightField = new TextField();
        heightField.setPromptText("Height");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");
        
       
        // Button for input info
       

        // VBox to hold all components
        VBox vitalsContent = new VBox(50, nameLabel, dateField, weightField, temperatureField, heightField, bloodPressureField, reasonField);
        vitalsContent.setPadding(new Insets(10));
        vitalsContent.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        vitalsTabDV.setContent(vitalsContent);
        vitalsTabDV.setClosable(false);
        
        
        
        /*
        
        this is where Input Info will save into the patient file 
        
        */
        
        

        patientDetailsTabsDV.getTabs().add(vitalsTabDV);

        // Allergies Tab
        Tab allergiesTabDV = new Tab("Allergies");

        // Text Field for Allergies
        TextField allergyField = new TextField();
        allergyField.setPromptText("Enter allergy");

        // Button for inputting allergy
        Button inputAllergyButton = new Button("Input Allergy");
        Button saveVisit = new Button("Save visit");

        // VBox to hold Allergies components
        VBox allergiesContent = new VBox(10, allergyField, inputAllergyButton, saveVisit);
        allergiesTabDV.setContent(allergiesContent);
        allergiesTabDV.setClosable(false);

        patientDetailsTabsDV.getTabs().add(allergiesTabDV);
        
        inputAllergyButton.setOnAction(e -> {
        	visit[6] = allergyField.getText();
        });
        
        saveVisit.setOnAction(e-> {
        	thePatient.setVisit(visit);
        	patientRecords.createVisit(thePatient, visit);
        	saveVisit.setDisable(true);
        });

        // Placeholder for patient history information
        Tab patientHistoryTabDV = new Tab("Patient History");
        ListView<String> patientHistoryListView = new ListView<>();
        patientHistoryListView.getItems().addAll("Patient history data for " + selectedPatient);
        patientHistoryTabDV.setContent(patientHistoryListView);
        patientHistoryTabDV.setClosable(false);
        patientDetailsTabsDV.getTabs().add(patientHistoryTabDV);
        
    }
}