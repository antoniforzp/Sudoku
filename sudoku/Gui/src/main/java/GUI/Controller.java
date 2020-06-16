package GUI;

import exceptions.ApplicationException;
import exceptions.DaoException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import difficulties.Easy;
import difficulties.Hard;
import difficulties.Medium;

import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import org.apache.log4j.Logger;
import serialization.FileSudokuDao;
import serialization.JdbcSudokuDao;
import sudokulogic.Backtracking;
import sudokulogic.SudokuBoard;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // EXCERCISE 8 PARTS ----------------

    @FXML
    private Button easyButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;
    @FXML
    private Button loadButtonFile;
    @FXML
    private Button saveButtonFile;
    @FXML
    private Button loadButtonDB;
    @FXML
    private Button saveButtonDB;
    @FXML
    private Button checkButton;
    @FXML
    public ComboBox<String> combobox;
    private ObservableList<String> list = FXCollections.observableArrayList("Polish", "English");
    @FXML
    private Text authors;

    //====BUNDLE CONFIGURATION======================================================================
    private enum Language {ENGLISH, POLISH}

    private ResourceBundle engBundleText = ResourceBundle.getBundle("bundles.languages");
    private ResourceBundle plBundleText = ResourceBundle.getBundle("bundles.languages", new Locale("pl", "Poland"));
    private ResourceBundle bundleText = engBundleText;


    //END OF EXCERCISE 8 PARTS ----------

    //BEGINNING OF EXCERCISE 11 PARTS ----------


    private static final ResourceBundle engBundleException = ResourceBundle.getBundle("logExceptions");
    private ResourceBundle plBundleException = ResourceBundle.getBundle("logExceptions", new Locale("pl", "Poland"));
    private ResourceBundle bundleException = engBundleException;

    private static final ResourceBundle engBundleMessage = ResourceBundle.getBundle("logMessages");
    private ResourceBundle plBundleMessage = ResourceBundle.getBundle("logMessages", new Locale("pl", "Poland"));
    private ResourceBundle bundleMessage = engBundleMessage;

    //====UTILITY CONFIGURATION====================================================================
    private final int size = 9;
    private TextField[][] textFields = new TextField[size][size];
    private SimpleIntegerProperty[][] integerProperties = new SimpleIntegerProperty[size][size];

    private SudokuBoard board = new SudokuBoard();

    //====LAYOUT CONFIGURATION====================================================================
    @FXML
    public BorderPane rootPane;
    private GridPane grid = new GridPane();

    //====FILE CHOOSER/WRITER=====================================================================
    private Stage fileChooserStage = new Stage();
    private FileChooser fileChooser = new FileChooser();

    //====OTHER===================================================================================
    private static Logger logger = Logger.getLogger(Controller.class.getName());
    //hierarchy: debug < info < warn < error < fatal

    public Controller() {
        logger.info(bundleMessage.getString("info.controller"));
    }


    //END OF EXCERCISE 11 PARTS ---------

    private SudokuBoard generate() {

        SudokuBoard board = new SudokuBoard();
        Backtracking solver = new Backtracking();
        solver.solve(board);

        return board;
    }

    //EXCERCISE 8 NEXT PARTS --------

    //====INITIALIZE===============================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //======================TEXT FIELDS CONFIGURATION==========================================
        //setting tracking changes
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TextField field = new TextField();
                field.setId("textField");
                field.setAlignment(Pos.CENTER);

                integerProperties[i][j] = new SimpleIntegerProperty();
                Bindings.bindBidirectional(field.textProperty(), integerProperties[i][j], new NumberStringConverter());

                //textField listener
                field.textProperty().addListener((observable, oldValue, newValue) -> {
                    //keep content in order in textFields

                    try {
                        if (!oldValue.equals("") && !newValue.equals("")) {
                            if (Integer.parseInt(newValue) <= size && Integer.parseInt(newValue) >= 0)
                                field.setText(newValue);
                            else {
                                field.setText("0");
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        field.setText(oldValue);
                    }
                });

                //textField formatter
                DecimalFormat format = new DecimalFormat("#");
                field.setTextFormatter(new TextFormatter<>(c ->
                {
                    if (c.getControlNewText().isEmpty()) {
                        return c;
                    }
                    ParsePosition parsePosition = new ParsePosition(0);
                    Object object = format.parse(c.getControlNewText(), parsePosition);

                    if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                        return null;
                    } else {
                        return c;
                    }
                }));

                textFields[i][j] = field;
                grid.add(textFields[i][j], i, j);
            }
        }

        //======================GRID AND LAYOUT CONFIGURATION==========================================
        //setting layout of gridPane full of TextFields
        combobox.setItems(list);

        grid.setHgap(2);
        grid.setVgap(2);

        HBox hBox = new HBox();

        hBox.getChildren().add(grid);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);
        vBox.setAlignment(Pos.CENTER);

        rootPane.setCenter(vBox);
    }

    //====BUTTONS===============================================================================
    public void verifyAll() {
        TextField field;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                field = textFields[j][i];
                if (field.isEditable() && !field.getText().equals("0") && !field.getText().equals("")) {
                    int fieldValue = Integer.parseInt(field.getText());

                    if (check(fieldValue, i, j)) {
                        logger.info(bundleMessage.getString("check.valueOk") + fieldValue);
                        board.getField(i, j).setValue(fieldValue);
                    } else {
                        logger.info(bundleMessage.getString("check.valueNO") + fieldValue);
                    }
                    textFields[j][i] = field;
                }
                setTextField(i, j);
            }
        }
    }

    //====DIFFICULTIES===============================================================================
    public void hardButton() {
        logger.info(bundleMessage.getString("info.hard"));
        board = generate();
        try {
            displayBoard();
        } catch (DaoException e) {
            logger.error(bundleException.getString(DaoException.null_board), e);
        }

        Hard hard = new Hard();
        hard.prepareBoard(board);

        setAllTextFields();
    }

    public void mediumButton() {
        logger.info(bundleMessage.getString("info.medium"));
        board = generate();
        try {
            displayBoard();
        } catch (DaoException e) {
            logger.error(bundleException.getString(DaoException.null_board), e);
        }

        Medium medium = new Medium();
        medium.prepareBoard(board);

        setAllTextFields();
    }

    public void easyButton() {
        logger.info(bundleMessage.getString("info.easy"));
        System.out.println("You chose easy");
        board = generate();
        try {
            displayBoard();
        } catch (DaoException e) {
            logger.error(bundleException.getString(DaoException.null_board), e);
        }

        Easy easy = new Easy();
        easy.prepareBoard(board);

        setAllTextFields();
    }

    //END OF EXCERCISE 8 NEXT PARTS --------------


    /*EXCERCISE 12/13 PARTS BUT ALSO PARTS OF EXCERCISE
     9/10 ------------ */

    //====DAO TO FILE===============================================================================
    public void loadButtonFile() {
        String path = "";

        //====FILE CHOOSER CONFIGURATION=====================================================================
        fileChooser.setInitialDirectory(new File("files"));
        fileChooser.setTitle("Load sudoku from file");
        File sudokuLoad = fileChooser.showOpenDialog(fileChooserStage);

        clearTextFields();

        try {
            path = sudokuLoad.getPath();
            FileSudokuDao daoTool = new FileSudokuDao();
            board = daoTool.read(path);

            displayBoard();

        } catch (NullPointerException e) {
            logger.error(bundleException.getString(DaoException.error_open));
        } catch (DaoException e) {
            logger.error(bundleException.getString(DaoException.error_open), e);
        } catch (ApplicationException e) {
            logger.error(bundleException.getString(DaoException.null_resourceBundle), e);
        }

        logger.info(bundleMessage.getString("info.loaded") + path);
        setAllTextFields();
    }

    public void saveButtonFile() {
        DialogBoxSave dialogBox = new DialogBoxSave(this, true);
        dialogBox.show();
    }

    void saveFile(String boardName) {

        String path = "files/";

        try {
            FileSudokuDao daoTool = new FileSudokuDao();
            daoTool.write(board, path + boardName);

        } catch (DaoException e) {
            logger.error(bundleException.getString(DaoException.error_open), e);
        } catch (ApplicationException e) {
            logger.error(bundleException.getString(DaoException.null_resourceBundle), e);
        }
        logger.info(bundleMessage.getString("info.saved") + path + boardName);
    }

    //====DAO TO DATABASE===========================================================================

    public void saveButtonDB() {
        DialogBoxSave dialogBox = new DialogBoxSave(this, false);
        dialogBox.show();
    }

    //pass date of creation
    void saveDb(String boardName) throws ApplicationException {
        JdbcSudokuDao daoTool = new JdbcSudokuDao();
        daoTool.write(board, boardName);

        logger.info(bundleMessage.getString("info.saved") + " to database");
    }

    //---------------------------------------------------------------------------

    public void loadButtonDB() throws ApplicationException {

        JdbcSudokuDao daoTool = new JdbcSudokuDao();

        DialogBoxLoad dialogBox = new DialogBoxLoad(this);
        dialogBox.getAllBoards(daoTool.selectAll());
        dialogBox.show();
    }

    void loadDb(String boardName) throws ApplicationException {
        JdbcSudokuDao daoTool = new JdbcSudokuDao();
        board = daoTool.read(boardName);

        displayBoard();
        clearTextFields();
        setAllTextFields();
    }

    //---------------------------------------------------------------------------

    void deleteDB(String boardName) throws ApplicationException {
        JdbcSudokuDao daoTool = new JdbcSudokuDao();
        daoTool.delete(boardName);
    }

    //END OF EXCERCISE 12/13 PARTS ------------

    //EXCERCISE 8 NEXT PARTS -------------


    //====TEXT FIELDS===============================================================================
    private void setAllTextFields() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setTextField(i, j);
            }
        }
    }

    private void setTextField(int posX, int posY) {
        String temp = Integer.toString(board.getField(posX, posY).getValue());
        TextField field = textFields[posY][posX];

        field.setText(temp);
        field.setEditable(false);
        field.setStyle("-fx-background-color: white");

        if (temp.equals("0")) {
            field.setStyle("-fx-background-color: #ffb366");
            field.setEditable(true);
        }
        textFields[posY][posX] = field;
    }

    private void clearTextFields() {
        logger.info(bundleMessage.getString("info.clearing"));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                textFields[j][i].setText("");
                textFields[j][i].setEditable(false);
                textFields[j][i].setStyle("-fx-background-color: white");
            }
        }
    }

    //====CHECK VALUES=============================================================================
    private boolean check(int num, int posX, int posY) {
        return board.getColumn(posX).verify(num)
                && board.getRow(posY).verify(num)
                && board.getBox((int) Math.floor(posX), (int) Math.floor(posY)).verify(num);
    }


    //END OF EXCERCISE 8 NEXT PARTS -----------

    //EXCERCISE 9/10 PARTS -----------


    //====CHANGE LANGUAGE==========================================================================
    private void changeLanguage(Language language) {
        switch (language) {
            case ENGLISH:
                bundleText = engBundleText;
                bundleException = engBundleException;
                bundleMessage = engBundleMessage;
                logger.info(bundleMessage.getString("info.language") + "English");
                break;

            case POLISH:
                bundleText = plBundleText;
                bundleException = plBundleException;
                bundleMessage = plBundleMessage;
                logger.info(bundleMessage.getString("info.language") + "Polski");
                break;
        }
        loadButtonFile.setText(bundleText.getString("button.load"));
        saveButtonFile.setText(bundleText.getString("button.save"));

        loadButtonDB.setText(bundleText.getString("button.load"));
        saveButtonDB.setText(bundleText.getString("button.save"));

        easyButton.setText(bundleText.getString("button.easy"));
        mediumButton.setText(bundleText.getString("button.medium"));
        hardButton.setText(bundleText.getString("button.hard"));

        saveButtonFile.setText(bundleText.getString("button.save"));
        checkButton.setText(bundleText.getString("button.check"));
        combobox.setPromptText(bundleText.getString("combobox.prompter"));

        authors.setText(bundleText.getString("label.authors"));
    }

    public void changeLanguage() {
        if (combobox.getValue().equals("Polish")) {
            changeLanguage(Language.POLISH);
        }
        if (combobox.getValue().equals("English")) {
            changeLanguage(Language.ENGLISH);
        }
    }

    //====TERMINAL==============================================================================
    private void displayBoard() throws DaoException {
        if (board == null) {
            throw new DaoException(DaoException.null_board);
        }
        logger.info(board.toString());
    }
}