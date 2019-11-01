package dream.fcard.gui.controllers.jsjava;

import dream.fcard.logic.storage.StorageManager;
import java.io.IOException;

import dream.fcard.util.FileReadWrite;
import dream.fcard.util.code.JavaRunner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * The controller for the Java Editor.
 */
public class JavaEditorController extends AnchorPane {
    @FXML
    private TextArea textEditor;
    @FXML
    private Button runCodeButton;
    @FXML
    private TextArea consoleOutput;

    private String fileName = "Main.java";

    /**
     * Runs the code in the text editor in Java and returns the output.
     * @throws IOException if code cannot be compiled.
     */
    @FXML
    private void runCode() throws IOException {
        String code = textEditor.getText();
        StorageManager.writeCode(fileName, code);
        String filepath = StorageManager.getCodePath(fileName);
        String compileOutput = JavaRunner.compile(filepath);
        if (!compileOutput.isBlank()) {
            consoleOutput.setText(compileOutput);
            return;
        } else {
            String output = JavaRunner.runJava(filepath);
            consoleOutput.setText(output);
        }
    }
}
