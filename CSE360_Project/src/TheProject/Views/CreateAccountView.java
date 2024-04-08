package TheProject.Views;

import TheProject.Records.*;
import TheProject.SceneViewer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateAccountView extends VBox {
	
	public CreateAccountView(SceneViewer sceneViewer, PatientRecords patientRecords){
	super();
	
	Label addAccountWelcome = new Label("Welcome! Please complete the following form.");
	
	Label CAIS_ERROR = new Label();
    
    TextField firstName = new TextField();
    firstName.setPromptText("First Name");
    TextField lastName = new TextField();
    lastName.setPromptText("Last Name");
    
    Label dobTitle = new Label("Enter Date of Birth:");
    TextField month = new TextField();
    month.setPromptText("MM");
    TextField day = new TextField();
    day.setPromptText("DD");
    TextField year = new TextField();
    year.setPromptText("YYYY");

    HBox dateOfBirth = new HBox(10);
    dateOfBirth.setPadding(new Insets(20));
    dateOfBirth.getChildren().addAll(month, day, year);

    Button caisEnterButton = new Button("Enter Info");
    
    caisEnterButton.setOnAction(e -> {
        String patient_name = firstName.getText() + lastName.getText();
        
        String m = month.getText();
        String d = day.getText();
        String y = year.getText();
        String patient_dob = month + "/" + day + "/" + year;

        if (patient_name.equals("")) {
        	CAIS_ERROR.setText("Please Enter first name and last name");
        }
        else if (m.equals("")) {
        	CAIS_ERROR.setText("Invalid Birth Month. Please Use (MM)");
        }
        else if (d.equals("")) {
        	CAIS_ERROR.setText("Invalid Birth Day. Please Use (DD)");
        }
        else if (y.equals("")) {
        	CAIS_ERROR.setText("Invalid Birth Year. Please Use (YYYY)");
        }
        else {
          sceneViewer.changeView(new PatientView(sceneViewer, patientRecords));
        }
      });
    
    super.setPadding(new Insets(20));
    super.getChildren().addAll(addAccountWelcome, firstName, lastName, dobTitle, dateOfBirth, caisEnterButton, CAIS_ERROR);
	}
    
}