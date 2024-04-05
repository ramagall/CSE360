package TheProject;
import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	SceneViewer sceneViewer = new SceneViewer(800, 600);
        sceneViewer.setLoginView();

        primaryStage.setTitle("Welcome to CSE360_Project_Fall");
        primaryStage.setScene(sceneViewer);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}