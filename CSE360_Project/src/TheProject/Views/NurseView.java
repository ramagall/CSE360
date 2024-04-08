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

public class NurseView extends BorderPane{
	public NurseView(SceneViewer sceneViewer, PatientRecords patientRecords){
		super();
		
	    Label welcomeNV = new Label("Nurse View.");
	    HBox titleBoxNV = new HBox(welcomeNV);

	    //Patients list
	    ListView<String> patientListNV = new ListView<>();
	    patientListNV.getItems().addAll("Patient 1", "Patient 2", "Patient 3");
	    TextField searchedPatientFieldNV = new TextField();
	    searchedPatientFieldNV.setPromptText("Search Patient");
	    Button searchPatientNV = new Button("Search");
	    Button nurseLogout = new Button("Logout");
	    VBox searchPatientBoxNV = new VBox(searchedPatientFieldNV, patientListNV,  searchPatientNV, nurseLogout);


	    //Tabs for patients - center
	    TabPane patientDetailsTabsNV = new TabPane();
	    Tab examinationTabNV = new Tab("Exam results");
	    examinationTabNV.setClosable(false);
	    Tab patientHistoryTabNV = new Tab("Patient History");
	    patientHistoryTabNV.setClosable(false);
	    patientDetailsTabsNV.getTabs().addAll(examinationTabNV, patientHistoryTabNV);

	    //Patients - Inbox and Send Message
	    TabPane emailTabPaneNV = new TabPane();

	    //Inbox
	    Tab inboxTabNV = new Tab("Inbox");
	    inboxTabNV.setClosable(false);
	    ListView<String> inboxNV = new ListView<>();
	    inboxNV.getItems().addAll("Message 1", "Message 2", "Message 3");
	    inboxTabNV.setContent(inboxNV);

	    //Send a Message
	    Tab sendMessageTabNV = new Tab("Send a Message");
	    sendMessageTabNV.setClosable(false);

	    emailTabPaneNV.getTabs().addAll(inboxTabNV, sendMessageTabNV);

	    TextField typeMessageNV = new TextField();
	    typeMessageNV.setPromptText("Type your message here:");
	    Label sendAMessageToLabelNV = new Label( "Send a message to: ");

	    ToggleGroup sendMessageToNV = new ToggleGroup();

	    RadioButton patientButtonPV = new RadioButton("Patient");
	    patientButtonPV.setToggleGroup(sendMessageToNV);

	    HBox sendMessageToNVBox = new HBox(10, patientButtonPV);

	    Label usernameSendToNV = new Label("Username:");
	    TextField usernameSendToNVField = new TextField();

	    HBox user_sendToNV = new HBox(10);
	    user_sendToNV.setPadding(new Insets(20));
	    user_sendToNV.getChildren().addAll(usernameSendToNV, usernameSendToNVField);

	    Label passwordSendToNV = new Label("Password:");
	    PasswordField passwordSendToNVField = new PasswordField();

	    HBox pass_sendToNV = new HBox(10);
	    pass_sendToNV.setPadding(new Insets(20));
	    pass_sendToNV.getChildren().addAll(passwordSendToNV, passwordSendToNVField);

	    Button submitNV = new Button("Submit");

	    HBox buttonsBoxNV = new HBox(10);
	    buttonsBoxNV.setPadding(new Insets(20));
	    buttonsBoxNV.getChildren().addAll(submitNV);

	    VBox sendMessageNV = new VBox(10);
	    sendMessageNV.setPadding(new Insets(20));
	    sendMessageNV.getChildren().addAll(emailTabPaneNV,
	                                       typeMessageNV, 
	                                       sendAMessageToLabelNV, 
	                                       sendMessageToNVBox, 
	                                       user_sendToNV, 
	                                       pass_sendToNV, 
	                                       buttonsBoxNV);

	    sendMessageTabNV.setContent(sendMessageNV); //user_sendToNV, pass_sendToNV, buttonsBoxV

	    super.setTop(titleBoxNV);
	    super.setLeft(searchPatientBoxNV);
	    super.setCenter(patientDetailsTabsNV);
	    super.setRight(emailTabPaneNV);
	    
	    nurseLogout.setOnAction(e -> {
	    	sceneViewer.setLoginView();
	    });
	}
}