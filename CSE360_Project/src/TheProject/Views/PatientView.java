package TheProject.Views;

import TheProject.Records.*;
import TheProject.Users.Patient;

import java.util.ArrayList;

import TheProject.SceneViewer;
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
	    for (int i = 0; i < inbox.size(); i++) {
	    	
	    	Email currEmail = inbox.get(i);
	    	String urgent = currEmail.urgency.equals("True") ? " (Urgent)" : " (Not Urgent)";
	    	String header = currEmail.head + urgent;
	    	inboxPV.getItems().add(header);
	    }
	    inboxTabPV.setContent(inboxPV);
	    
	    // Outbox
	    Tab sentMessagesPV = new Tab("Sent");
	    sentMessagesPV.setClosable(false);
	    ListView<String> outboxPV = new ListView<>();
	    
	    // Populate Outbox with Email Headers
	    ArrayList<Email> outbox = emailRecords.outboxList.get(username);
	    for (int i = 0; i < outbox.size(); i++) {
	    	
	    	Email currEmail = outbox.get(i);
	    	String recipient = currEmail.intendedPerson;
	    	String header = "To: " + recipient + " - " + currEmail.head;
	    	outboxPV.getItems().add(header);
	    }
	    sentMessagesPV.setContent(outboxPV);

	    //Send a Message
	    Tab sendMessageTabPV = new Tab("Send a Message");
	    sendMessageTabPV.setClosable(false);

	    emailTabPanePV.getTabs().addAll(inboxTabPV, sentMessagesPV, sendMessageTabPV);

	    TextArea typeMessagePV = new TextArea();
	    typeMessagePV.setPromptText("Type your message here:");
	    typeMessagePV.setPrefRowCount(5);
	    typeMessagePV.setPrefColumnCount(1);
	    Label sendAMessageToLabelPV = new Label( "Send a message to: ");
	    RadioButton doctorButtonPV = new RadioButton("Doctor");
	    RadioButton nurseButtonPV = new RadioButton("Nurse");
	    ToggleGroup sendMessageToPV = new ToggleGroup();
	    doctorButtonPV.setToggleGroup(sendMessageToPV);
	    nurseButtonPV.setToggleGroup(sendMessageToPV);

	    HBox sendMessageToPVBox = new HBox(10, doctorButtonPV, nurseButtonPV);

	    Label usernameSendToPV = new Label("Username:");
	    TextField usernameSendToPVField = new TextField();

	    HBox user_sendToPV = new HBox(10);
	    user_sendToPV.setPadding(new Insets(20));
	    user_sendToPV.getChildren().addAll(usernameSendToPV, usernameSendToPVField);

	    Label passwordSendToPV = new Label("Password:");
	    PasswordField passwordSendToPVField = new PasswordField();

	    HBox pass_sendToPV = new HBox(10);
	    pass_sendToPV.setPadding(new Insets(20));
	    pass_sendToPV.getChildren().addAll(passwordSendToPV, passwordSendToPVField);

	    CheckBox urgentPV = new CheckBox("Check if Urgent");
	    Button submitPV = new Button("Submit");

	    HBox buttonsBoxPV = new HBox(10);
	    buttonsBoxPV.setPadding(new Insets(20));
	    buttonsBoxPV.getChildren().addAll(urgentPV, submitPV);

	    VBox sendMessagePV = new VBox(10);
	    sendMessagePV.setPadding(new Insets(20));
	    sendMessagePV.getChildren().addAll(emailTabPanePV,
	                                       typeMessagePV, 
	                                       sendAMessageToLabelPV, 
	                                       sendMessageToPVBox, 
	                                       user_sendToPV, 
	                                       pass_sendToPV, 
	                                       buttonsBoxPV);

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
	    
	    outboxPV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	System.out.println("BOZO");
            }
        });
	    
	    inboxPV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	System.out.println("BOZO");
            }
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