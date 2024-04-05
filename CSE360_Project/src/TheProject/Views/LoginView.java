package TheProject.Views;

import TheProject.SceneViewer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {
	public LoginView(SceneViewer sceneViewer) {
		super();
		
		Label messageLabel = new Label();
		
		Label loginTitle = new Label("Login Existing User");
	    TextField usernameField = new TextField();
	    usernameField.setPromptText("Username");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Password");

	    // Create Account text components
	    Label createTitle = new Label("Create Patient Account");
	    TextField newUsername = new TextField();
	    newUsername.setPromptText("New Username");
	    PasswordField newPassword = new PasswordField();
	    newPassword.setPromptText("New Password");
	    PasswordField checkPassword = new PasswordField();
	    checkPassword.setPromptText("Confirm Password");

	    // Login buttons
	    Button loginButton = new Button("Login");
	    RadioButton selectDoctor = new RadioButton();
	    Label selectD = new Label("Doctor");
	    RadioButton selectNurse = new RadioButton();
	    Label selectN = new Label("Nurse");
	    RadioButton selectPatient = new RadioButton();
	    Label selectP = new Label("Patient");
	    
	    loginButton.setOnAction(e -> { // We will fill this out after working through the create account page.
	      });

	    // Create Account buttons
	    Button createButton = new Button("Create Account");
	    
	    createButton.setOnAction(e -> { 
	        String name = newUsername.getText();
	        String pass1 = newPassword.getText();
	        String pass2 = checkPassword.getText();
	        
	        if (name.equals("")) {
	          messageLabel.setText("Please Enter Username");
	        }

	        else if (pass1.equals("")) {
	          messageLabel.setText("Please Enter Password");
	        }

	        else {
	          if (pass1.equals(pass2)) {
	            /* We will add here a check for */
	        	sceneViewer.changeView(new CreateAccountView(sceneViewer));
	          } else {
	            messageLabel.setText("Passwords do not match >:(");
	          }
	        }
	      });
	    
	    // Select Buttons
	    ToggleGroup select = new ToggleGroup();
	    selectDoctor.setToggleGroup(select);
	    selectNurse.setToggleGroup(select);
	    selectPatient.setToggleGroup(select);

	    // Layout for login form
	    HBox selectButtons = new HBox(10);
	    selectButtons.setPadding(new Insets(20));
	    selectButtons.getChildren().addAll(selectDoctor, selectD, selectNurse, selectN, selectPatient, selectP);

	    VBox loginLayout = new VBox(10);
	    loginLayout.setPadding(new Insets(20));
	    loginLayout.getChildren().addAll(loginTitle, usernameField, passwordField, selectButtons, loginButton);

	    // Layout for create account
	    VBox createLayout = new VBox(10);
	    createLayout.setPadding(new Insets(20));
	    createLayout.getChildren().addAll(createTitle, newUsername, newPassword, checkPassword, createButton);

	     // Initial screen layout
	    HBox initialScreen = new HBox(10);
	    initialScreen.setPadding(new Insets(20));
	    initialScreen.getChildren().addAll(createLayout, loginLayout);

	    super.setPadding(new Insets(20));
	    super.getChildren().addAll(initialScreen, messageLabel);
	    
	    
	}
}
