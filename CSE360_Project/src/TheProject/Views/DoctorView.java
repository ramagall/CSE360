package TheProject.Views;

import TheProject.Records.*;
import TheProject.Users.Patient;
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
	public DoctorView(SceneViewer sceneViewer, DoctorRecords doctorRecords, PatientRecords patientRecords) {
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

        // Inbox and Send Message
        TabPane emailTabPaneDV = new TabPane();

        // Inbox
        Tab inboxTabDV = new Tab("Inbox");
        inboxTabDV.setClosable(false);
        inboxDV = new ListView<>();
        inboxDV.getItems().addAll("Message 1", "Message 2", "Message 3");
        inboxTabDV.setContent(inboxDV);

        // Send a Message
        Tab sendMessageTabNV = new Tab("Send a Message");
        sendMessageTabNV.setClosable(false);
        VBox sendMessageContent = new VBox();
        sendMessageContent.getChildren().addAll(new Label("Sending message feature under development"));
        sendMessageTabNV.setContent(sendMessageContent);

        emailTabPaneDV.getTabs().addAll(inboxTabDV, sendMessageTabNV);

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