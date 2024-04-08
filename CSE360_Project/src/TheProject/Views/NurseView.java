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

public class NurseView extends BorderPane {
    private ListView<String> patientListNV;
    private TextField searchedPatientFieldNV;
    private TabPane patientDetailsTabsNV;
    private ListView<String> inboxNV;
// test
    public NurseView(SceneViewer sceneViewer, NurseRecords nurseRecords) {
        super();

        Label welcomeNV = new Label("Nurse View.");
        HBox titleBoxNV = new HBox(welcomeNV);

        // Patients list
        patientListNV = new ListView<>();
        patientListNV.getItems().addAll("Patient 1", "Patient 2", "Patient 3");
        searchedPatientFieldNV = new TextField();
        searchedPatientFieldNV.setPromptText("Search Patient");
        Button searchPatientNV = new Button("Search");
        Button nurseLogout = new Button("Logout");
        VBox searchPatientBoxNV = new VBox(searchedPatientFieldNV, patientListNV, searchPatientNV, nurseLogout);

        // Tabs for patients - center
        patientDetailsTabsNV = new TabPane();

        // Inbox and Send Message
        TabPane emailTabPaneNV = new TabPane();

        // Inbox
        Tab inboxTabNV = new Tab("Inbox");
        inboxTabNV.setClosable(false);
        inboxNV = new ListView<>();
        inboxNV.getItems().addAll("Message 1", "Message 2", "Message 3");
        inboxTabNV.setContent(inboxNV);

        // Send a Message
        Tab sendMessageTabNV = new Tab("Send a Message");
        sendMessageTabNV.setClosable(false);
        VBox sendMessageContent = new VBox();
        sendMessageContent.getChildren().addAll(new Label("Sending message feature under development"));
        sendMessageTabNV.setContent(sendMessageContent);

        emailTabPaneNV.getTabs().addAll(inboxTabNV, sendMessageTabNV);

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
                updatePatientDetails(newValue);
            }
        });
    }

    // Method to update patient details based on the selected patient
    private void updatePatientDetails(String selectedPatient) {
        // Clear previous content
        patientDetailsTabsNV.getTabs().clear();

        // Vitals Tab
        Tab vitalsTabNV = new Tab("Vitals");

        // Patient Name Label
        Label nameLabel = new Label(selectedPatient);
        nameLabel.setStyle("-fx-font-weight: bold");
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        nameLabel.setAlignment(javafx.geometry.Pos.CENTER);

        // Text Fields for Vitals
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
        VBox vitalsContent = new VBox(50, nameLabel, weightField, temperatureField, heightField, bloodPressureField, inputInfoButton);
        vitalsContent.setPadding(new Insets(10));
        vitalsContent.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        vitalsTabNV.setContent(vitalsContent);
        vitalsTabNV.setClosable(false);
        
        
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

        // VBox to hold Allergies components
        VBox allergiesContent = new VBox(10, allergyField, inputAllergyButton);
        allergiesTabNV.setContent(allergiesContent);
        allergiesTabNV.setClosable(false);

        patientDetailsTabsNV.getTabs().add(allergiesTabNV);

        // Placeholder for patient history information
        Tab patientHistoryTabNV = new Tab("Patient History");
        ListView<String> patientHistoryListView = new ListView<>();
        patientHistoryListView.getItems().addAll("Patient history data for " + selectedPatient);
        patientHistoryTabNV.setContent(patientHistoryListView);
        patientHistoryTabNV.setClosable(false);
        patientDetailsTabsNV.getTabs().add(patientHistoryTabNV);
    }
}