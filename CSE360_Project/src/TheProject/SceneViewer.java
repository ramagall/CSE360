package TheProject;

import TheProject.Records.*;
import TheProject.Views.LoginView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SceneViewer extends Scene {
	
	public PatientRecords patientRecords;
	
	public SceneViewer(double height, double width) {
		super(new VBox(), height, width);
		// We will also initialize a new patient, doctor, and nurse here
	}
	
	public SceneViewer(VBox vbox, double height, double weight) {
		super(vbox, height, weight);
	}
	
	public void changeView(VBox vbox) {
		super.setRoot(vbox);
	}
	
	public void changeView(Pane pane) {
        super.setRoot(pane);
	}
	
	public void setLoginView() {
		changeView(new LoginView(this));
	}
	
}