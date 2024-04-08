package TheProject.Views;

import TheProject.Records.*;
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
	public PatientView(SceneViewer sceneViewer, PatientRecords patientRecords) {
		
		super();
		
	    Label welcomePV = new Label("Patient View.");
	    HBox titleBoxPV = new HBox(welcomePV);

	    //Patients list 
	    ListView<String> visitListPV = new ListView<>();
	    visitListPV.getItems().addAll("February 15th 2024", "March 10th 2024", "July 21 2023");
	    TextField searchedPatientFieldPV = new TextField();
	    searchedPatientFieldPV.setPromptText("Search Visits");
	    Button searchPatientPV = new Button("Search");
	    Button patientLogout = new Button("Logout");
	    Button patientMyProfilePV = new Button("My Profile");
	    VBox searchPatientBoxPV = new VBox(searchedPatientFieldPV, visitListPV,  searchPatientPV, patientMyProfilePV, patientLogout);

	    

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
	    
	    Label date = new Label("Date: January 1, 2000");

	    Label reasonFV = new Label("Reason for Visit: Broken Ankle");
	    Label Notes = new Label("Notes: ");

	    summaryOfVisit.setContent(new VBox(date, reasonFV, Notes));
	    
	    Label insurance = new Label("Insurance: Generic");

	    insuranceTabPV.setContent(new VBox(insurance));



	     Label healthIssues1 = new Label("Prior Health Issues");

	     HBox hx = new HBox(20);
	     HBox vx = new HBox(20); // spacing = 20
	     VBox comb = new VBox(20);

	     Label vaccinesR = new Label("Vaccination Records");

	     TextArea vaccines = new TextArea("vaccine 1" + "vaccine2" + "vaccine3");
	     vaccines.setEditable(false);
	     vaccines.setLayoutX(300);
	     vaccines.setLayoutY(100);
	     vaccines.setMaxWidth(100);
	     vaccines.setWrapText(true);
	     vaccines.setEditable(false);
	     TextArea healthIssues = new TextArea("\nvaccine \n" + "\nvaccine2\n" + "\nvaccine2\n");
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


	    
	    //Patients - Inbox and Send Message
	    TabPane emailTabPanePV = new TabPane();

	    //Inbox
	    Tab inboxTabPV = new Tab("Inbox");
	    inboxTabPV.setClosable(false);
	    ListView<String> inboxPV = new ListView<>();
	    inboxPV.getItems().addAll("Message 1", "Message 2", "Message 3");
	    inboxTabPV.setContent(inboxPV);

	    //Send a Message
	    Tab sendMessageTabPV = new Tab("Send a Message");
	    sendMessageTabPV.setClosable(false);

	    emailTabPanePV.getTabs().addAll(inboxTabPV, sendMessageTabPV);

	    TextField typeMessagePV = new TextField();
	    typeMessagePV.setPromptText("Type your message here:");
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
	    super.setCenter(patientDetailsTabsPV);
	    super.setRight(emailTabPanePV);


	    // Patient Logout Check 
	    patientLogout.setOnAction(e -> {
	    	sceneViewer.setLoginView();
	    });
	}
}