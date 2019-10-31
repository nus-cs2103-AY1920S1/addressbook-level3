package com.dukeacademy.ui;

import com.dukeacademy.model.program.TestCaseResult;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Controller class for Program Evaluation Card. Each card represents a specific test case and shows how
 * well the user's program performed against it.
 */
public class ProgramEvaluationCard extends UiPart<Region> {
    private static final String FXML = "ProgramEvaluationCard.fxml";

    public final TestCaseResult testCaseResult;

    @FXML
    private Label testCaseId;

    @FXML
    private Label testCaseStatus;

    @FXML
    private Label feedback;

    @FXML
    private Label input;

    @FXML
    private Label expectedOutput;

    @FXML
    private Label actualOutput;

    public ProgramEvaluationCard(TestCaseResult testCaseResult, int displayedIndex) {
        super(FXML);
        this.testCaseResult = testCaseResult;
        updateTestCaseId(displayedIndex);
        updateTestCaseStatus(testCaseResult);
        updateFeedback(testCaseResult);
        updateInput(testCaseResult);
        updateExpectedOutput(testCaseResult);
        updateActualOutput(testCaseResult);
    }

    private void updateTestCaseId(int id) {
        testCaseId.setText(id + "");
    }

    /**
     * Updates the test case status, either PASS or FAIL on the program evaluation card, based on the
     * isSuccessful boolean attribute stored in a test case result object.
     * @param testCaseResult test case result object
     */
    private void updateTestCaseStatus(TestCaseResult testCaseResult) {
        boolean isPass = testCaseResult.isSuccessful();

        if (isPass) {
            testCaseStatus.setText("(PASS)");
        } else {
            testCaseStatus.setText("(FAIL)");
        }
    }

    /**
     * Updates the feedback given to the user for a specfic test case, based on the isSuccessful boolean
     * attribute stored in a test case result object.
     * @param testCaseResult test case result object
     */
    private void updateFeedback(TestCaseResult testCaseResult) {
        boolean isPass = testCaseResult.isSuccessful();

        if (isPass) {
            feedback.setText("Correct Output");
        } else {
            feedback.setText("Incorrect Output");
        }
    }

    private void updateInput(TestCaseResult testCaseResult) {
        String inputString = testCaseResult.getInput();
        input.setText(inputString);
    }

    private void updateExpectedOutput(TestCaseResult testCaseResult) {
        String expectedOutputString = testCaseResult.getExpectedOutput();
        expectedOutput.setText(expectedOutputString);
    }

    private void updateActualOutput(TestCaseResult testCaseResult) {
        String actualOutputString = testCaseResult.getActualOutput().get();
        actualOutput.setText(actualOutputString);
    }
}
