package GUI;

import exceptions.ApplicationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

class DialogBoxSave {

    private Stage stage;
    private VBox layoutVbox;
    private GridPane gridPane;

    DialogBoxSave(Controller controller, boolean toFile) {

        //stage
        stage = new Stage();
        if (toFile) {
            stage.setTitle("Save new board to file");
        } else {
            stage.setTitle("Save new board to SQLite database");
        }

        //layout
        layoutVbox = new VBox();
        layoutVbox.setPadding(new Insets(10, 10, 0, 10));

        //gridPane
        gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(300 / 2);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(300 / 4);
        col2.setHalignment(HPos.LEFT);
        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.setHgap(10);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        Date date = new Date(System.currentTimeMillis());
        layoutVbox.getChildren().add(new Label("generated: " + formatter.format(date)));

        //textfield
        TextField textField = new TextField();
        textField.setPromptText("new board name");
        textField.setEditable(true);
        textField.setId("saveTextField");
        if (toFile) {
            textField.setText("board_" + formatter.format(date) + ".txt");
        }
        gridPane.add(textField, 0, 0);
        GridPane.setHalignment(textField, HPos.CENTER);

        //button
        Button button = new Button("save");
        button.setPrefSize(60, 20);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    if (toFile) {
                        controller.saveFile(textField.getText());
                    } else {
                        controller.saveDb(textField.getText());
                    }
                } catch (ApplicationException e) {
                    e.printStackTrace();
                }
                stage.close();
            }
        });
        gridPane.add(button, 1, 0);
        GridPane.setHalignment(button, HPos.CENTER);
    }

    void show() {
        layoutVbox.getChildren().add(gridPane);
        Scene scene = new Scene(layoutVbox);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);

        stage.setWidth(500);
        stage.setHeight(120);
        stage.show();
    }
}

