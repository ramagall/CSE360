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
	    Tab inboxTabPV = new Tab("Inbox");
	    inboxTabPV.setClosable(false);
	    ListView<String> inboxPV = new ListView<>();
	    
	    // Populate Inbox with Email Headers
	    ArrayList<Email> inbox = emailRecords.inboxList.get(username);
	    try {
	    	for (int i = 0; i < inbox.size(); i++) {
		    	
		    	Email currEmail = inbox.get(i);
		    	String urgent = currEmail.urgency.equals("True") ? " (Urgent)" : " (Not Urgent)";
		    	String header = currEmail.head + urgent;
		    	inboxPV.getItems().add(header);
		    }
	    } catch (NullPointerException e) {
	    	// do nothing (empty inbox)
	    }
	    inboxTabPV.setContent(inboxPV);
	    
	    // Outbox
	    Tab sentMessagesPV = new Tab("Sent");
	    sentMessagesPV.setClosable(false);
	    ListView<String> outboxPV = new ListView<>();
	    
	    // Populate Outbox with Email Headers
	    ArrayList<Email> outbox = emailRecords.outboxList.get(username);
	    try {
	    	for (int i = 0; i < outbox.size(); i++) {
		    	
		    	Email currEmail = outbox.get(i);
		    	String recipient = currEmail.intendedPerson;
		    	String header = "To: " + recipient + " - " + currEmail.head;
		    	outboxPV.getItems().add(header);
		    }
	    } catch (NullPointerException e) {
	    	// do nothing (outbox empty)
	    }
	    sentMessagesPV.setContent(outboxPV);

	    //Send a Message
	    Tab sendMessageTabPV = new Tab("Send a Message");
	    sendMessageTabPV.setClosable(false);

	    emailTabPaneNV.getTabs().addAll(inboxTabPV, sentMessagesPV, sendMessageTabPV);

	    Label usernameSendToPV = new Label("Send To:");
	    TextField usernameSendToPVField = new TextField();

	    // Send To Field
	    HBox user_sendToPV = new HBox(10);
	    user_sendToPV.setPadding(new Insets(20));
	    user_sendToPV.getChildren().addAll(usernameSendToPV, usernameSendToPVField);

	    // Header Field
	    TextField headerPV = new TextField();
	    headerPV.setPromptText("Header...");
	    
	    // Message Body Field
	    TextArea typeMessagePV = new TextArea();
	    typeMessagePV.setPromptText("Type your message here...");
	    typeMessagePV.setPrefRowCount(5);
	    typeMessagePV.setPrefColumnCount(1);
	    
	    // Check Urgent Field
	    CheckBox urgentPV = new CheckBox("Check if Urgent");
	    Button submitPV = new Button("Send Message");
	    Button cancelPV = new Button("Cancel");

	    HBox buttonsBoxPV = new HBox(10);
	    buttonsBoxPV.setPadding(new Insets(20));
	    buttonsBoxPV.getChildren().addAll(urgentPV, submitPV, cancelPV);

	    // Notification Label
	    Label notifLabel = new Label("");
	    
	    // Complete Send Message
	    VBox sendMessagePV = new VBox(10);
	    sendMessagePV.setPadding(new Insets(20));
	    sendMessagePV.getChildren().addAll(emailTabPaneNV, user_sendToPV, headerPV, typeMessagePV, buttonsBoxPV, notifLabel);

	    sendMessageTabPV.setContent(sendMessagePV);

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
	    outboxPV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	viewOutboxEmailDetails(username, newValue, sceneViewer, patientRecords, emailRecords);
            }
        });
	    
	    // Switch to Inbox
	    inboxPV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	viewInboxEmailDetails(username, newValue, sceneViewer, patientRecords, emailRecords,
            			emailTabPaneNV, sendMessageTabPV, usernameSendToPVField, headerPV);
            }
        });
	    
	    // Send Email Button
	    submitPV.setOnAction(e -> {
	    	
	    	// Gather info from textfields
	    	String recipient = usernameSendToPVField.getText();
	    	String header = headerPV.getText();
	    	String body = typeMessagePV.getText().replace('\n', '_');
	    	String isUrgent = urgentPV.isSelected() ? "True" : "False";
	    	
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
	    	usernameSendToPVField.clear();
	    	usernameSendToPVField.setEditable(true);
	    	headerPV.clear();
	    	headerPV.setEditable(true);
	    	typeMessagePV.clear();
	    	urgentPV.setSelected(false);
	    	notifLabel.setText("Email successfully sent.");
	    });
	    
	    cancelPV.setOnAction(e -> {
	    	
	    	// Clean textfields 
	    	usernameSendToPVField.clear();
	    	usernameSendToPVField.setEditable(true);
	    	headerPV.clear();
	    	headerPV.setEditable(true);
	    	typeMessagePV.clear();
	    	urgentPV.setSelected(false);
	    	notifLabel.setText("Email draft cancelled.");
	    	
	    	// ** BUG **
	    	// emailTabPanePV.getSelectionModel().clearSelection();
	    	// this code deselects email so it can be selected again
	    	/* not sure where to put that line so that you are able
	    	 * to Email1 -> ViewEmail -> Reply -> Cancel -> EmailList -> Email1
	    	*/
	    });
    }

    public void viewInboxEmailDetails(String username, String newValue, SceneViewer sceneViewer, PatientRecords patientRecords, EmailRecords emailRecords,
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
    		sceneViewer.changeView(new PatientView(sceneViewer, emailRecords, patientRecords, username));
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
	
    public void viewOutboxEmailDetails(String username, String newValue, SceneViewer sceneViewer, PatientRecords patientRecords, EmailRecords emailRecords) {
		
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
        Tab patientHistoryTabNV = new Tab("Patient History");
        ListView<String> patientHistoryListView = new ListView<>();
        patientHistoryListView.getItems().addAll("Patient history data for " + selectedPatient);
        patientHistoryTabNV.setContent(patientHistoryListView);
        patientHistoryTabNV.setClosable(false);
        patientDetailsTabsNV.getTabs().add(patientHistoryTabNV);
        
    }
}