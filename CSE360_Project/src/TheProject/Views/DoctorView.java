package TheProject.Views;

import TheProject.Records.*;
import TheProject.SceneViewer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DoctorView extends BorderPane{
	public DoctorView(SceneViewer sceneViewer, PatientRecords patientRecords) {
		super();
		
		Label welcomeDV = new Label("Doctor View.");
	    HBox titleBoxDV = new HBox(welcomeDV);

	    //Patients list
	    ListView<String> patientListDV = new ListView<>();
	    patientListDV.getItems().addAll("Patient 1", "Patient 2", "Patient 3");
	    TextField searchedPatientFieldDV = new TextField();
	    searchedPatientFieldDV.setPromptText("Search Patient");
	    Button searchPatientDV = new Button("Search");
	    Button docLogout = new Button("Logout");
	    VBox searchPatientBoxDV = new VBox(searchedPatientFieldDV, patientListDV,  searchPatientDV, docLogout);

	    //Tabs for patients - center
	    TabPane patientDetailsTabsDV = new TabPane();
	    Tab examinationTabDV = new Tab("Exam results");
	    examinationTabDV.setClosable(false);
	    Tab patientHistoryTabDV = new Tab("Patient History");
	    patientHistoryTabDV.setClosable(false);
	    patientDetailsTabsDV.getTabs().addAll(examinationTabDV, patientHistoryTabDV);

	    //Patients - Inbox and Send Message
	    TabPane emailTabPaneDV = new TabPane();

	    //Inbox
	    Tab inboxTabDV = new Tab("Inbox");
	    inboxTabDV.setClosable(false);
	    ListView<String> inboxDV = new ListView<>();
	    inboxDV.getItems().addAll("Message 1", "Message 2", "Message 3");
	    inboxTabDV.setContent(inboxDV);

	    //Send a Message
	    Tab sendMessageTabDV = new Tab("Send a Message");
	    sendMessageTabDV.setClosable(false);

	    emailTabPaneDV.getTabs().addAll(inboxTabDV, sendMessageTabDV);
	    
	    TextField typeMessageDV = new TextField();
	    typeMessageDV.setPromptText("Type your message here:");
	    Label sendAMessageToLabelDV = new Label( "Send a message to: ");
	    RadioButton doctorButtonDV = new RadioButton("Patient");
	    ToggleGroup sendMessageToDV = new ToggleGroup();
	    doctorButtonDV.setToggleGroup(sendMessageToDV);
	    

	    HBox sendMessageToDVBox = new HBox(10, doctorButtonDV);

	    Label usernameSendToDV = new Label("Username:");
	    TextField usernameSendToDVField = new TextField();

	    HBox user_sendToDV = new HBox(10);
	    user_sendToDV.setPadding(new Insets(20));
	    user_sendToDV.getChildren().addAll(usernameSendToDV, usernameSendToDVField);

	    Label passwordSendToDV = new Label("Password:");
	    PasswordField passwordSendToDVField = new PasswordField();

	    HBox pass_sendToDV = new HBox(10);
	    pass_sendToDV.setPadding(new Insets(20));
	    pass_sendToDV.getChildren().addAll(passwordSendToDV, passwordSendToDVField);

	    CheckBox urgentDV = new CheckBox("Check if Urgent");
	    Button submitDV = new Button("Submit");

	    HBox buttonsBoxDV = new HBox(10);
	    buttonsBoxDV.setPadding(new Insets(20));
	    buttonsBoxDV.getChildren().addAll(urgentDV, submitDV);

	    VBox sendMessageDV = new VBox(10);
	    sendMessageDV.setPadding(new Insets(20));
	    sendMessageDV.getChildren().addAll(emailTabPaneDV,
	                                       typeMessageDV, 
	                                       sendAMessageToLabelDV, 
	                                       sendMessageToDVBox, 
	                                       user_sendToDV, 
	                                       pass_sendToDV, 
	                                       buttonsBoxDV);

	    sendMessageTabDV.setContent(sendMessageDV);
	    
	    super.setTop(titleBoxDV);
	    super.setLeft(searchPatientBoxDV);
	    super.setCenter(patientDetailsTabsDV);
	    super.setRight(emailTabPaneDV);
	    
	    docLogout.setOnAction(e -> {
	    	sceneViewer.setLoginView();
	    });

	}
}