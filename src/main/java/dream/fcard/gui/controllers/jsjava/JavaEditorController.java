package dream.fcard.gui.controllers.jsjava;

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

    private String filepath = "./src/main/java/dream/fcard/util/code/data/Main.java";

    /**
     * Runs the code in the text editor in Java and returns the output.
     * @throws IOException if code cannot be compiled.
     */
    @FXML
    private void runCode() throws IOException {
        String code = textEditor.getText();
        FileReadWrite.write(filepath, code);
        String result = JavaRunner.compileAndRun(filepath);
        consoleOutput.setText(result);

    }
}
