package com.dukeacademy.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.dukeacademy.commons.core.LogsCenter;

public class Editor extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    private static final String FXML = "Editor.fxml";

    @FXML
    private Button btn_Save;

    @FXML
    private Button btn_Submit;

    @FXML
    private TextArea textOutput;

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

    @FXML
    public void onClick_btn_Save(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save File");
        File selectedFile = chooser.showOpenDialog(stage);
        FileWriter FW = new FileWriter(selectedFile.getAbsolutePath());
        FW.write(textOutput.getText());
        FW.close();
    }

    /**
     * On click btn submit.
     *
     * @param e the e
     */
    @FXML
    public String onClick_btn_Submit(ActionEvent e) {
        System.out.println(textOutput.getText().strip());
        return textOutput.getText().strip();
    }

    public Editor() {
        super(FXML);
    }
}
