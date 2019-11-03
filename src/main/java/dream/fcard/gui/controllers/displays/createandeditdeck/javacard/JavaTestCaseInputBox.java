package dream.fcard.gui.controllers.displays.createandeditdeck.javacard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.TestCase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * A controller for the container of rows of test cases used during JavaCard creation.
 */
public class JavaTestCaseInputBox extends ScrollPane {
    @FXML
    private VBox testCaseBox;

    private ArrayList<JavaTestCaseRow> testCaseRows;
    /**
     * Consumers to delete and add rows.
     */
    private Consumer<Boolean> addTestCase = b -> addNewRow();
    private Consumer<JavaTestCaseRow> deleteRow = row -> {
        if (testCaseRows.size() > 1) {
            testCaseRows.remove(row);
            renderRows();
        } else {
            Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need at least 1 test case!");
        }
    };

    public JavaTestCaseInputBox() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Displays/JavaTestCaseInputBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            testCaseRows = new ArrayList<>();
            addNewRow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Render the testcases onto the screen.
     */
    private void renderRows() {
        testCaseBox.getChildren().clear();
        for (int i = 0; i < testCaseRows.size(); i++) {
            JavaTestCaseRow row = testCaseRows.get(i);
            testCaseBox.getChildren().add(row);
        }
    }

    private void addNewRow() {
        JavaTestCaseRow row = new JavaTestCaseRow(addTestCase, deleteRow);
        testCaseRows.add(row);
        renderRows();
    }

    public ArrayList<TestCase> getTestCases() {
        ArrayList<TestCase> cases = new ArrayList<>();
        for (JavaTestCaseRow row : testCaseRows) {
            String input = row.getInput();
            String output = row.getOutput();
            TestCase testCase = new TestCase(input, output);
            cases.add(testCase);
        }
        return cases;
    }

    public void setTestCaseRows(ArrayList<TestCase> testCases) {
        testCaseRows = new ArrayList<>();
        for (TestCase testCase : testCases) {
            JavaTestCaseRow row = new JavaTestCaseRow(addTestCase, deleteRow);
            row.setInput(testCase.getInput());
            row.setOutput(testCase.getExpectedOutput());
            testCaseRows.add(row);
        }
        renderRows();
    }

    /**
     * Returns whether the input box contains at least one test case
     * @return a boolean
     */
    public boolean hasAtLeastOneTestCase() {
        boolean hasTestCases = false;
        for (JavaTestCaseRow row : testCaseRows) {
            if (row.hasTestCase()) {
                hasTestCases = true;
            }
        }
        return hasTestCases;
    }


}
