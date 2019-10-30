package dream.fcard.gui.controllers.displays.createandeditdeck.javacard;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.State;
import dream.fcard.model.TestCase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A UI element for uploading test cases for the Java Card.
 */
public class JavaTestCaseInputRow extends GridPane {
    @FXML
    private Label testCaseNumberLabel;
    @FXML
    private Button uploadInputFileButton;
    @FXML
    private Label inputFileNameLabel;
    @FXML
    private Button uploadOutputFileButton;
    @FXML
    private Label outputFileNameLabel;
    @FXML
    private Button addNewRowButton;
    @FXML
    private Button deleteRowButton;

    private TestCase testCase;
    private File input;
    private File output;
    @SuppressWarnings("unchecked")
    private Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);

    public JavaTestCaseInputRow(Consumer<Boolean> addNewRow, Consumer<JavaTestCaseInputRow> deleteRow) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/TestCaseInputRow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            uploadInputFileButton.setOnAction(e -> getFile(true));
            uploadOutputFileButton.setOnAction(e -> getFile(false));
            addNewRowButton.setOnAction(e -> addNewRow.accept(true));
            deleteRowButton.setOnAction(e -> deleteRow.accept(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TestCase getTestCase() {
        TestCase temp = new TestCase(input, output);
        return this.testCase;
    }

    public void setTestCaseNumberLabel(int i) {
        testCaseNumberLabel.setText(Integer.toString(i));
    }

    private void getFile(boolean isInput) {
        final Stage dialog = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File file = fileChooser.showOpenDialog(dialog);
        if (isInput) {
            input = file;
            inputFileNameLabel.setText(input.getName());
        } else {
            output = file;
            outputFileNameLabel.setText(output.getName());
        }
    }

    /**
     * Detects if user has not uploaded an input file or uploaded a non-existent file. Also alerts
     * the user to exactly which row the missing file is in.
     *
     * @return true if there is no input file, false if there is an input file.
     */
    public boolean hasNoInputFile() {
        if (input == null || !input.exists()) {
            displayMessage.accept("Empty input file detected in row " + getRow());
            return true;
        }
        return false;
    }

    /**
     * Returns whether this input row has empty files or un-uploaded files.
     * @return
     */
    public boolean hasEmptyOrMissingOutputFile() {
        if (output == null || !output.exists() || output.length() == 0) {
            displayMessage.accept("Empty/missing output file detected in row " + getRow());
            return true;
        }
        return false;
    }

    private String getRow() {
        String row = testCaseNumberLabel.getText();
        return row.substring(0, row.length() - 1);
    }
}
