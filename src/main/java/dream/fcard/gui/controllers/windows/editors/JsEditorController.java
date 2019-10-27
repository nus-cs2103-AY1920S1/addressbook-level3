package dream.fcard.gui.controllers.windows.editors;

import java.io.IOException;

import dream.fcard.util.code.JavascriptRunner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * The controller for the JavaScript Editor.
 */
public class JsEditorController extends AnchorPane {
    @FXML
    private TextArea textEditor;
    @FXML
    private Button runCodeButton;
    @FXML
    private TextArea consoleOutput;


    /**
     * Runs the code in the text editor in Java and returns the output.
     * @throws IOException if code cannot be compiled.
     */
    @FXML
    private void runCode() throws IOException {
        String code = textEditor.getText();
        String result = JavascriptRunner.evaluateString(code);
        consoleOutput.setText(result);
    }
}
