package dream.fcard.gui.controllers.displays.createandeditdeck.javacard;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/**
 * A controller for a row of test case input boxes when creating a JavaCard.
 */
public class JavaTestCaseRow extends HBox {
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private Button addRowButton;
    @FXML
    private Button deleteRowButton;

    public JavaTestCaseRow(Consumer<Boolean> addNewRow, Consumer<JavaTestCaseRow> deleteThis) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/JavaTestCaseRow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            addRowButton.setOnAction(e -> addNewRow.accept(true));
            deleteRowButton.setOnAction(e -> deleteThis.accept(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInput() {
        return inputTextArea.getText();
    }

    public String getOutput() {
        return outputTextArea.getText();
    }

    public void setInput(String inputText) {
        this.inputTextArea.setText(inputText);
    }

    public void setOutput(String outputText) {
        this.outputTextArea.setText(outputText);
    }

    public boolean hasTestCase() {
        return !outputTextArea.getText().isBlank();
    }
}
