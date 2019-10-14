package com.dukeacademy.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Creates a Text Editor window for the user to input code.
 */
public class Editor extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    static private final String FXML = "Editor.fxml";

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextArea textOutput;

    public Editor() {
        super(FXML);
    }


    /**
     * Initializes the initial configurations for the Editor Window upon startup.
     */
    @FXML
    public void initialize() {
        textOutput.addEventFilter(KeyEvent.KEY_PRESSED, e1 -> {
            if (e1.getCode() == KeyCode.TAB) {
                String s = " ".repeat(4);
                textOutput.insertText(textOutput.getCaretPosition(), s);
                e1.consume();
            }
        });
    }

    /**
     * Saves file into user's computer upon clicking the "Save" button.
     * @param e the ActionEvent
     * @throws IOException when the user's file cannot be accessed
     */
    @FXML
    public void onSaveButtonClick(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save File");
        File selectedFile = chooser.showOpenDialog(stage);
        FileWriter fw = new FileWriter(selectedFile.getAbsolutePath());
        fw.write(textOutput.getText());
        fw.close();
    }

    /**
     * On click btn submit.
     *
     * @param e the e
     */
    @FXML
    public String onSubmitButtonClick(ActionEvent e) {
        System.out.println(textOutput.getText().strip());
        return textOutput.getText().strip();
    }
}
