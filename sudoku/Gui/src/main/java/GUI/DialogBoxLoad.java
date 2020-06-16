package GUI;

import exceptions.ApplicationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

class DialogBoxLoad {

    private Controller controller;

    private Stage stage;
    private VBox layoutVbox;
    private GridPane gridPane;

    private ArrayList<Label> labels;

    DialogBoxLoad(Controller controller) {
        this.controller = controller;

        //stage
        stage = new Stage();
        stage.setTitle("Loading form database");

        //layout
        layoutVbox = new VBox();
        layoutVbox.setPadding(new Insets(10, 10, 0, 10));

        //gridPane
        gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(400 / 4);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(400 / 4);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(400 / 4);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(400 / 4);
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);
        gridPane.setVgap(10);

        //labels
        labels = new ArrayList<>();
        layoutVbox.getChildren().add(new Label("jdbc:sqlite:SudokuBoards.db"));
        layoutVbox.getChildren().add(new Label("org.sqlite.JDBC"));
        layoutVbox.getChildren().add(new Label(""));
    }

    void getAllBoards(Map<String, String> boards) {

        Set set = boards.entrySet(); //Converting to Set so that we can traverse
        int position = 0;

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;

            //name
            Label name = new Label(entry.getKey().toString());
            labels.add(name);
            gridPane.add(name, 0, position);
            GridPane.setHalignment(name, HPos.RIGHT);

            //dates
            Label date = new Label(entry.getValue().toString());
            gridPane.add(date, 1, position);
            GridPane.setHalignment(date, HPos.CENTER);

            //buttons load
            Button buttonLoad = new Button("load");
            buttonLoad.setPrefSize(60, 20);
            buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Node source = (Node) event.getSource();
                    try {
                        controller.loadDb(labels.get(GridPane.getRowIndex(source)).getText());

                    } catch (ApplicationException e) {
                        e.printStackTrace();
                    }
                    stage.close();
                }
            });
            gridPane.add(buttonLoad, 2, position);
            GridPane.setHalignment(buttonLoad, HPos.RIGHT);

            Button buttonDelete = new Button("delete");
            buttonDelete.setPrefSize(60, 20);
            buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Node source = (Node) event.getSource();
                    try {
                        controller.deleteDB(labels.get(GridPane.getRowIndex(source)).getText());
                    } catch (ApplicationException e) {
                        e.printStackTrace();
                    }
                    stage.close();
                }
            });

            gridPane.add(buttonDelete, 3, position);
            GridPane.setHalignment(buttonDelete, HPos.LEFT);

            RowConstraints row = new RowConstraints();
            row.setPrefHeight(25);
            gridPane.getRowConstraints().add(row);

            position++;
        }
    }

    void show() {
        layoutVbox.getChildren().add(gridPane);
        Scene scene = new Scene(layoutVbox);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);

        stage.setWidth(500);
        stage.setHeight(80 + (labels.size() + 1) * 55);
        stage.show();
    }
}