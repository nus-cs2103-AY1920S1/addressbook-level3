package dream.fcard.gui.components;

import java.io.IOException;

import dream.fcard.util.code.JavascriptRunner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * The application for the JavaScript Editor.
 */
public class JsEditorController {
    @FXML
    private TextArea textEditor;
    @FXML
    private Button runCodeButton;
    @FXML
    private TextArea consoleOutput;


    /**
     * Runs the code in the text editor in JavaScript and returns the output.
     * @throws IOException if code cannot be compiled.
     */
    @FXML
    private void runCode() throws IOException {
        String code = textEditor.getText();
        String result = JavascriptRunner.evaluateString(code);
        consoleOutput.setText(result);
    }
}
