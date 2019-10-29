package dream.fcard.gui.controllers.displays;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.State;
import dream.fcard.model.TestCase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * A Ui element that allows users to upload test cases for Java.
 */
public class TestCaseUploader extends ScrollPane {
    @FXML
    private VBox testCaseInputBox;

    private ArrayList<JavaTestCaseInputRow> inputRows;
    private Consumer<JavaTestCaseInputRow> deleteRow = row -> {
        if (inputRows.size() > 1) {
            inputRows.remove(row);
            renderRows();
        } else {
            @SuppressWarnings("unchecked")
            Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);
            displayMessage.accept("You need at least 1 test case!");
        }
    };
    private Consumer<Boolean> addNewRow = b -> addNewRow();

    public TestCaseUploader() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/TestCaseUploader.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            inputRows = new ArrayList<>();
            addNewRow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Take the rows in the inputRows and render them.
     */
    private void renderRows() {
        testCaseInputBox.getChildren().clear();
        for (int i = 0; i < inputRows.size(); i++) {
            JavaTestCaseInputRow row = inputRows.get(i);
            row.setTestCaseNumberLabel(i + 1); //switch to 1-indexing
            testCaseInputBox.getChildren().add(row);
        }
    }

    /**
     * Add a new JavaTestCaseInputRow to the test case uploader.
     */
    private void addNewRow() {
        @SuppressWarnings("unchecked")
        Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);
        clearMessage.accept(true);
        JavaTestCaseInputRow row = new JavaTestCaseInputRow(addNewRow, deleteRow);
        inputRows.add(row);
        renderRows();
    }

    /**
     * Used by CardEditingWindow to remove the default first option.
     */
    public void deleteFirstRow() {
        inputRows.remove(0);
    }

    /**
     * Return all the files in the uploader, bundled into TestCase objects.
     * @return an array list of test cases.
     */
    public ArrayList<TestCase> getTestCases() {
        ArrayList<TestCase> testCases = new ArrayList<>();
        inputRows.forEach(row -> testCases.add(row.getTestCase()));
        return testCases;
    }

    /**
     * Detects if any file has not been uploaded, or if any output file is 0b.
     * Note that 0b input test files are acceptable for when the user doesn't need input.
     * @return whether any output file is 0b or any file has not been uploaded.
     */
    public boolean hasNoMissingFiles() {
        boolean hasNoMissing = true;
        for (JavaTestCaseInputRow row : inputRows) {
            if (row.hasNoInputFile() || row.hasEmptyOrMissingOutputFile()) {
                hasNoMissing = false;
            }
        }
        return hasNoMissing;
    }
}
