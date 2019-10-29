package com.dukeacademy.ui;


import com.dukeacademy.model.program.TestCaseResult;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class ProgramEvaluationCard extends UiPart<Region> {
    private static final String FXML = "ProgramEvaluationCard.fxml";

    public final TestCaseResult testCaseResult;

    @FXML
    private Label testCaseId;

    @FXML
    private Label testCaseStatus;

    @FXML
    private Label failReason;

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
        updateFailReason(testCaseResult);
        updateInput(testCaseResult);
        updateExpectedOutput(testCaseResult);
        updateActualOutput(testCaseResult);
    }

    private void updateTestCaseId(int id) {
        testCaseId.setText(id + "");
    }

    private void updateTestCaseStatus(TestCaseResult testCaseResult) {
        boolean isPass = testCaseResult.isSuccessful();
        if (isPass) {
            testCaseStatus.setText("(PASS)");
        } else {
            testCaseStatus.setText("(FAIL)");
        }
    }

    private void updateFailReason(TestCaseResult testCaseResult) {
        failReason.setText("Incorrect Output");
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
