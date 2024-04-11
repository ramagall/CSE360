package TheProject.Views;

import TheProject.Records.*;
import TheProject.Users.*;

import java.io.File;
import java.util.ArrayList;

import TheProject.SceneViewer;
import TheProject.FileHandling.FileHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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

public class NurseView extends BorderPane {
    private ListView<String> patientListNV;
    private TextField searchedPatientFieldNV;
    private TabPane patientDetailsTabsNV;
    private ListView<String> inboxNV;
    private ListView<String> patientHistoryListNV;
    
    
    int day;
    int month;
    int year;
    String user;
// test
    public NurseView(SceneViewer sceneViewer, EmailRecords emailRecords, NurseRecords nurseRecords, PatientRecords patientRecords, String username) {
        super();
       

        Label welcomeNV = new Label("Nurse View.");
        HBox titleBoxNV = new HBox(welcomeNV);

        // Patients list
        patientListNV = new ListView<>();
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
        	patientListNV.getItems().add(name);
        	}
        }

        
        searchedPatientFieldNV = new TextField();
        searchedPatientFieldNV.setPromptText("Search Patient");
        Button searchPatientNV = new Button("Search");
        Button nurseLogout = new Button("Logout");
        VBox searchPatientBoxNV = new VBox(searchedPatientFieldNV, patientListNV, searchPatientNV, nurseLogout);
        searchPatientNV.setOnAction(e -> {
            String searchText = searchedPatientFieldNV.getText().toLowerCase(); 
            patientListNV.getItems().clear();

      
            for (String key : patientRecords.patientList.keySet()) {
                String name = patientRecords.searchPatient(key).getFirstName() + " " + patientRecords.searchPatient(key).getLastName();
                if (name.toLowerCase().contains(searchText)) {
                    patientListNV.getItems().add(name);
                }
            }
        });

        // Tabs for patients - center
        patientDetailsTabsNV = new TabPane();

        // Inbox and Send Message
        TabPane emailTabPaneNV = new TabPane();
        
        //Inbox
	    Tab inboxTabNV = new Tab("Inbox");
	    inboxTabNV.setClosable(false);
	    ListView<String> inboxNV = new ListView<>();
	    
	    // Populate Inbox with Email Headers
	    ArrayList<Email> inbox = emailRecords.inboxList.get(username);
	    try {
	    	for (int i = 0; i < inbox.size(); i++) {
		    	
		    	Email currEmail = inbox.get(i);
		    	String urgent = currEmail.urgency.equals("True") ? " (Urgent)" : " (Not Urgent)";
		    	String header = currEmail.head + urgent;
		    	inboxNV.getItems().add(header);
		    }
	    } catch (NullPointerException e) {
	    	// do nothing (empty inbox)
	    }
	    inboxTabNV.setContent(inboxNV);
	    
	    // Outbox
	    Tab sentMessagesNV = new Tab("Sent");
	    sentMessagesNV.setClosable(false);
	    ListView<String> outboxNV = new ListView<>();
	    
	    // Populate Outbox with Email Headers
	    ArrayList<Email> outbox = emailRecords.outboxList.get(username);
	    try {
	    	for (int i = 0; i < outbox.size(); i++) {
		    	
		    	Email currEmail = outbox.get(i);
		    	String recipient = currEmail.intendedPerson;
		    	String header = "To: " + recipient + " - " + currEmail.head;
		    	outboxNV.getItems().add(header);
		    }
	    } catch (NullPointerException e) {
	    	// do nothing (outbox empty)
	    }
	    sentMessagesNV.setContent(outboxNV);

	    //Send a Message
	    Tab sendMessageTabNV = new Tab("Send a Message");
	    sendMessageTabNV.setClosable(false);

	    emailTabPaneNV.getTabs().addAll(inboxTabNV, sentMessagesNV, sendMessageTabNV);

	    Label usernameSendToNV = new Label("Send To:");
	    TextField usernameSendToNVField = new TextField();

	    // Send To Field
	    HBox user_sendToNV = new HBox(10);
	    user_sendToNV.setPadding(new Insets(20));
	    user_sendToNV.getChildren().addAll(usernameSendToNV, usernameSendToNVField);

	    // Header Field
	    TextField headerNV = new TextField();
	    headerNV.setPromptText("Header...");
	    
	    // Message Body Field
	    TextArea typeMessageNV = new TextArea();
	    typeMessageNV.setPromptText("Type your message here...");
	    typeMessageNV.setPrefRowCount(5);
	    typeMessageNV.setPrefColumnCount(1);
	    
	    // Check Urgent Field
	    CheckBox urgentNV = new CheckBox("Check if Urgent");
	    Button submitNV = new Button("Send Message");
	    Button cancelNV = new Button("Cancel");

	    HBox buttonsBoxNV = new HBox(10);
	    buttonsBoxNV.setPadding(new Insets(20));
	    buttonsBoxNV.getChildren().addAll(urgentNV, submitNV, cancelNV);

	    // Notification Label
	    Label notifLabel = new Label("");
	    
	    // Complete Send Message
	    VBox sendMessagePV = new VBox(10);
	    sendMessagePV.setPadding(new Insets(20));
	    sendMessagePV.getChildren().addAll(emailTabPaneNV, user_sendToNV, headerNV, typeMessageNV, buttonsBoxNV, notifLabel);

	    sendMessageTabNV.setContent(sendMessagePV);

        super.setTop(titleBoxNV);
        super.setLeft(searchPatientBoxNV);
        super.setCenter(patientDetailsTabsNV);
        super.setRight(emailTabPaneNV);

        nurseLogout.setOnAction(e -> {
            sceneViewer.setLoginView();
        });

        // Event handler for selecting a patient
        patientListNV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	updatePatientDetails(newValue, patientRecords);
            }
        });
        
     // Switch to Outbox
	    outboxNV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	viewOutboxEmailDetails(username, newValue, sceneViewer, nurseRecords, patientRecords, emailRecords);
            }
        });
	    
	    // Switch to Inbox
	    inboxNV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	viewInboxEmailDetails(username, newValue, sceneViewer, nurseRecords, patientRecords, emailRecords,
            			emailTabPaneNV, sendMessageTabNV, usernameSendToNVField, headerNV);
            }
        });
	    
	    // Send Email Button
	    submitNV.setOnAction(e -> {
	    	
	    	// Gather info from textfields
	    	String recipient = usernameSendToNVField.getText();
	    	String header = headerNV.getText();
	    	String body = typeMessageNV.getText().replace('\n', '_');
	    	String isUrgent = urgentNV.isSelected() ? "True" : "False";
	    	
	    	// Make email line
	    	String insertedEmail = recipient + "~" + username + "~" + isUrgent + "~" + header + "~" + body;
	    	
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
			FileHandler.FileReplace(infile.getPath(), insertedEmail);
			File outFile = FileHandler.getFile(username + "_outbox", "Emails/Outbox");
			FileHandler.FileReplace(outFile.getPath(), insertedEmail);
	    	
			// Clean textfields 
	    	usernameSendToNVField.clear();
	    	usernameSendToNVField.setEditable(true);
	    	headerNV.clear();
	    	headerNV.setEditable(true);
	    	typeMessageNV.clear();
	    	urgentNV.setSelected(false);
	    	notifLabel.setText("Email successfully sent.");
	    	
	    	sceneViewer.changeView(new NurseView(sceneViewer, emailRecords, nurseRecords, patientRecords, username));
	    });
	    
	    cancelNV.setOnAction(e -> {
	    	
	    	// Clean textfields 
	    	usernameSendToNVField.clear();
	    	usernameSendToNVField.setEditable(true);
	    	headerNV.clear();
	    	headerNV.setEditable(true);
	    	typeMessageNV.clear();
	    	urgentNV.setSelected(false);
	    	notifLabel.setText("Email draft cancelled.");
	    	
	    	sceneViewer.changeView(new NurseView(sceneViewer, emailRecords, nurseRecords, patientRecords, username));
	    });
    }

    public void viewInboxEmailDetails(String username, String newValue, SceneViewer sceneViewer, NurseRecords nurseRecords, PatientRecords patientRecords, EmailRecords emailRecords,
			TabPane emailTabPanePV, Tab sendMessageTabPV, TextField usernameSendToPVField, TextField headerPV) {
		
		// Find Email: Derive Header
    	String[] info = newValue.split(" ");
    	StringBuilder header = new StringBuilder();
    	for (int i = 0; i < info.length - 1; i++) {
    		if((i == info.length - 2) && (info[i].equals("(Not"))) {
    			break;
    		}
    		header.append(info[i] + " ");
    	}
    	
    	String theHeader = header.toString();
    	theHeader = theHeader.substring(0, theHeader.length() - 1);
    	System.out.println(theHeader);
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
    		sceneViewer.changeView(new NurseView(sceneViewer, emailRecords, nurseRecords, patientRecords, username));
    		// Fixed the BUG
    	});
    	
    	replyButton.setOnAction(e -> {
    		
    		emailTabPanePV.getSelectionModel().select(sendMessageTabPV);
    		
    		usernameSendToPVField.setText(passEmail.sender);
    		usernameSendToPVField.setEditable(false);
    		String head = "Re " + passEmail.head;
    		headerPV.setText(head);
    		headerPV.setEditable(false);
    		
    		super.setRight(emailTabPanePV);
    	});
	}
	
    public void viewOutboxEmailDetails(String username, String newValue, SceneViewer sceneViewer, NurseRecords nurseRecords, PatientRecords patientRecords, EmailRecords emailRecords) {
		
		// Find Email: Derive Header
    	String[] info = newValue.split(" ");
    	StringBuilder header = new StringBuilder();
    	for (int i = 0; i < info.length - 1; i++) {
    		if((i == info.length - 2) && (info[i].equals("(Not"))) {
    			break;
    		}
    		header.append(info[i]);
    	}
    	String theHeader = header.toString();
    	theHeader = theHeader.substring(0, theHeader.length() - 1);
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
    	fromUserField.setText(theEmail.intendedPerson);
    	
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
    	Tab viewEmail = new Tab("Email to " + theEmail.intendedPerson);
    	viewEmail.setClosable(false);
    	viewEmail.setContent(new VBox(fromUserUI, emailHeader, emailBody, actionButtons));
    	
    	emailDetails.getTabs().add(viewEmail);
    	super.setRight(emailDetails);
    	
    	exitButton.setOnAction(e -> {
    		sceneViewer.changeView(new NurseView(sceneViewer, emailRecords, nurseRecords, patientRecords, username));
    		// Fixed the BUG
    	});
	}

    // Method to update patient details based on the selected patient
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
        patientDetailsTabsNV.getTabs().clear();
        	
        // Vitals Tab
        Tab vitalsTabNV = new Tab("Vitals");

        // Patient Name Label
        Label nameLabel = new Label(selectedPatient);
        nameLabel.setStyle("-fx-font-weight: bold");
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        nameLabel.setAlignment(javafx.geometry.Pos.CENTER);

        // Text Fields for Vitals
        TextField dateField = new TextField();
        dateField.setPromptText("Date");
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
        Button inputInfoButton = new Button("Input Info");

        // VBox to hold all components
        VBox vitalsContent = new VBox(50, nameLabel, dateField, weightField, temperatureField, heightField, bloodPressureField, reasonField, inputInfoButton);
        vitalsContent.setPadding(new Insets(10));
        vitalsContent.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        vitalsTabNV.setContent(vitalsContent);
        vitalsTabNV.setClosable(false);
        
        Tab patientHistoryTabNV = new Tab("Patient History");
        ListView<String> patientHistoryListView = new ListView<>();
     
        

        inputInfoButton.setOnAction(e -> 
        {
        	
        	if( thePatient.getAge() < 12)
        	{
        		System.out.print(thePatient.getAge());
        		Label ageLimit = new Label("Not old enough for vitals, reason validated");
        		vitalsContent.getChildren().add(ageLimit);
        		vitalsTabNV.setContent(vitalsContent);
        		visit[1] = dateField.getText(); // date
    			visit[2] = "Too Young"; // temp
    			visit[3] = "Too Young"; // height 
    			visit[4] =	"Too Young"; // B.P.
    			visit[10] = reasonField.getText();
        	}
        	
        	else
        	{	
        		//note
        		String date = dateField.getText();
        		String reason = reasonField.getText();
        		String weight = weightField.getText();
        		String temperature = temperatureField.getText();
        		String height = heightField.getText();
        		String bloodPressure = bloodPressureField.getText();
        		
        		visit[1] = date; // date
        		visit [2] = weight;
    			visit[3] = temperature; // temp
    			visit[4] = height; // height
    			visit[5] =	bloodPressure; // B.P.
    			visit[10] = reason;
    			
    	
    	        
    
    	        
		  
        	}
   	
        });
        
            
        TextField historyField = new TextField();
        historyField.setPromptText("Input medical history");
        TextField vaxField = new TextField();
        vaxField.setPromptText("Input vaccine history");
        TextField prescriptionsField = new TextField();
        prescriptionsField.setPromptText("Input prescriptions");
        Button inputHistoryButton = new Button("Input History");
        
        inputHistoryButton.setOnAction(e -> {
        	visit[7] = historyField.getText();
        	visit[8] = vaxField.getText();
        	visit[9] = prescriptionsField.getText();
        });
        
        VBox patientHistoryContent = new VBox(historyField, vaxField, prescriptionsField, inputHistoryButton);
        patientHistoryTabNV.setContent(patientHistoryContent);
        patientHistoryTabNV.setClosable(false);
        patientDetailsTabsNV.getTabs().add(patientHistoryTabNV);
        /*
        
        this is where Input Info will save into the patient file 
        
        */
        
        

        patientDetailsTabsNV.getTabs().add(vitalsTabNV);

        // Allergies Tab
        Tab allergiesTabNV = new Tab("Allergies");

        // Text Field for Allergies
        TextField allergyField = new TextField();
        allergyField.setPromptText("Enter allergy");

        // Button for inputting allergy
        Button inputAllergyButton = new Button("Input Allergy");
        Button saveVisit = new Button("Save visit");

        // VBox to hold Allergies components
        VBox allergiesContent = new VBox(10, allergyField, inputAllergyButton, saveVisit);
        allergiesTabNV.setContent(allergiesContent);
        allergiesTabNV.setClosable(false);

        patientDetailsTabsNV.getTabs().add(allergiesTabNV);
        
        inputAllergyButton.setOnAction(e -> {
        	visit[6] = allergyField.getText();
        });
        
        saveVisit.setOnAction(e-> {
        	thePatient.setVisit(visit);
        	patientRecords.createVisit(thePatient, visit);
        	saveVisit.setDisable(true);
        });

        // Placeholder for patient history information

    }
}