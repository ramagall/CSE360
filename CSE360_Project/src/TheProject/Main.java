package TheProject;


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	SceneViewer sceneViewer = new SceneViewer(800, 600);
        sceneViewer.setLoginView();
        sceneViewer.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        primaryStage.setTitle("CSE360_Project_Fall");
        primaryStage.setScene(sceneViewer);
        primaryStage.show();
    }
    
    // matt was here
    //hi matt

    public static void main(String[] args) {
        launch(args);
    }
}
