package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.languages");
        loader.setResources(bundle);

        try {
            BorderPane borderPane = loader.load();
            Scene scene = new Scene(borderPane);

            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            primaryStage.setWidth(1000);
            primaryStage.setHeight(600);
            primaryStage.setResizable(false);

            primaryStage.setTitle("Sudoku");
            primaryStage.setScene(scene);
            primaryStage.isResizable();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}