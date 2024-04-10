package TheProject.Views;

import TheProject.Records.*;
import TheProject.Users.Patient;

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

public class PatientView extends BorderPane {
	public PatientView(SceneViewer sceneViewer, EmailRecords emailRecords, PatientRecords patientRecords, String username) {
		
		super();
		Patient theUser = new Patient("None","None");
	    Label welcomePV = new Label("Patient View.");
	    HBox titleBoxPV = new HBox(welcomePV);
	    //System.out.println(username); 
	    
	    //Patients list 
	    ListView<String> visitListPV = new ListView<>();
	    for(Patient value: patientRecords.patientList.values())
        {
	    	if(value.getUser().equals(username)) {
	    		theUser = value;
	    		for(String key : value.visits.keySet()) {
	    			visitListPV.getItems().add(key);
	    		}
	    	}
        }
	    TextField searchedPatientFieldPV = new TextField();
	    searchedPatientFieldPV.setPromptText("Search Visits");
	    Button searchPatientPV = new Button("Search");
	    Button patientLogout = new Button("Logout");
	    Button patientMyProfilePV = new Button("My Profile");
	    VBox searchPatientBoxPV = new VBox(searchedPatientFieldPV, visitListPV,  searchPatientPV, patientMyProfilePV, patientLogout);

	    
	    
	    //Patients - Inbox and Send Message
	    TabPane emailTabPanePV = new TabPane();

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

	    emailTabPanePV.getTabs().addAll(inboxTabPV, sentMessagesPV, sendMessageTabPV);

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
	    sendMessagePV.getChildren().addAll(emailTabPanePV, user_sendToPV, headerPV, typeMessagePV, buttonsBoxPV, notifLabel);

	    sendMessageTabPV.setContent(sendMessagePV);

	    super.setTop(titleBoxPV);
	    super.setLeft(searchPatientBoxPV);
	    super.setRight(emailTabPanePV);


	    // Patient Logout Check 
	    patientLogout.setOnAction(e -> {
	    	sceneViewer.setLoginView();
	    });
	    
	    // MyProfile Button
	    patientMyProfilePV.setOnAction(e -> {
	    	sceneViewer.changeView(new PatientProfileView(sceneViewer, emailRecords, patientRecords, username));
	    });
	    
	    visitListPV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	viewVisitDetails(username, newValue, patientRecords);
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
            			emailTabPanePV, sendMessageTabPV, usernameSendToPVField, headerPV);
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
	    	usernameSendToPVField.clear();
	    	usernameSendToPVField.setEditable(true);
	    	headerPV.clear();
	    	headerPV.setEditable(true);
	    	typeMessagePV.clear();
	    	urgentPV.setSelected(false);
	    	notifLabel.setText("Email successfully sent.");
	    	
	    	sceneViewer.changeView(new PatientView(sceneViewer, emailRecords, patientRecords, username));
	    });
	    
	    cancelPV.setOnAction(e -> {
	    	
	    	// Clean textfields 
	    	usernameSendToPVField.clear();
	    	usernameSendToPVField.setEditable(true);
	    	headerPV.clear();
	    	headerPV.setEditable(true);
	    	typeMessagePV.clear();
	    	urgentPV.setSelected(false);
	    	
	    	sceneViewer.changeView(new PatientView(sceneViewer, emailRecords, patientRecords, username));
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
    		String head = "Re: " + passEmail.head;
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
	
	public void viewVisitDetails(String user, String theDate, PatientRecords patientRecords){
		Patient currentUser = patientRecords.searchPatient(user);
		String[] currentVisit = currentUser.getVisit(theDate);
	    //Tabs for patients - center
	    TabPane patientDetailsTabsPV = new TabPane();
	    Tab summaryOfVisit = new Tab("Summary of Visit");
	    summaryOfVisit.setClosable(false);
	    Tab patientHistoryTabPV = new Tab("Patient History");
	    patientHistoryTabPV.setClosable(false);
	    Tab insuranceTabPV = new Tab("Insurance");
	    insuranceTabPV.setClosable(false);
	    patientDetailsTabsPV.getTabs().addAll(summaryOfVisit, patientHistoryTabPV, insuranceTabPV);

	    /*
	      Center information 
	    */
	    
	    Label date = new Label(theDate);

	    Label reasonFV = new Label("Reason for Visit: " + currentVisit[10]);
	    Label Notes = new Label("Notes: ");

	    summaryOfVisit.setContent(new VBox(date, reasonFV, Notes));
	    
	    Label insurance = new Label("Insurance: " + currentUser.getInsuranceType());

	    insuranceTabPV.setContent(new VBox(insurance));

	     Label healthIssues1 = new Label("Prior Health Issues");

	     HBox hx = new HBox(20);
	     HBox vx = new HBox(20); // spacing = 20
	     VBox comb = new VBox(20);

	     Label vaccinesR = new Label("Vaccination Records");

	     TextArea vaccines = new TextArea(currentVisit[8]);
	     vaccines.setEditable(false);
	     vaccines.setLayoutX(300);
	     vaccines.setLayoutY(100);
	     vaccines.setMaxWidth(100);
	     vaccines.setWrapText(true);
	     vaccines.setEditable(false);
	     TextArea healthIssues = new TextArea(currentVisit[7]);
	     healthIssues.setEditable(false);
	     healthIssues.setLayoutX(380);
	     healthIssues.setLayoutY(100);
	     healthIssues.setMaxWidth(100);
	     healthIssues.setWrapText(true);
	     healthIssues.setEditable(false);

	     hx.getChildren().addAll(healthIssues1, vaccinesR);
	     vx.getChildren().addAll(hx, healthIssues, vaccines);

	     comb.getChildren().addAll(hx,vx);

	     patientHistoryTabPV.setContent(comb);
	     super.setCenter(patientDetailsTabsPV);
	}
}