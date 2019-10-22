package com.dukeacademy.ui;

import java.util.List;

import com.dukeacademy.model.program.TestCaseResult;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;


/**
 * Controller class for run code result, an evaluation of user's code submission against the test cases specified by
 * the question.
 */
public class RunCodeResult extends UiPart<Region> {
    private static final String FXML = "RunCodeResult.fxml";

    @FXML
    private Label input1;
    @FXML
    private Label expectedOutput1;
    @FXML
    private Label actualOutput1;

    @FXML
    private Label input2;
    @FXML
    private Label expectedOutput2;
    @FXML
    private Label actualOutput2;

    @FXML
    private Label input3;
    @FXML
    private Label expectedOutput3;
    @FXML
    private Label actualOutput3;

    @FXML
    private TitledPane testCasePane1;
    @FXML
    private TitledPane testCasePane2;
    @FXML
    private TitledPane testCasePane3;

    public RunCodeResult(List<TestCaseResult> testCaseResults) {
        super(FXML);

        fillPane1(testCaseResults.get(0));
        fillPane2(testCaseResults.get(1));
        fillPane3(testCaseResults.get(2));

        showGrading1(testCaseResults.get(0));
        showGrading2(testCaseResults.get(1));
        showGrading3(testCaseResults.get(2));
    }

    public void fillPane1(TestCaseResult testCaseResult) {
        String inputString = testCaseResult.getInput();
        input1.setText(inputString);
        String expectedOutputString = testCaseResult.getExpectedOutput();
        expectedOutput1.setText(expectedOutputString);
        String actualOutputString = testCaseResult.getActualOutput().get();
        actualOutput1.setText(actualOutputString);
    }

    public void fillPane2(TestCaseResult testCaseResult) {
        String inputString = testCaseResult.getInput();
        input2.setText(inputString);
        String expectedOutputString = testCaseResult.getExpectedOutput();
        expectedOutput2.setText(expectedOutputString);
        String actualOutputString = testCaseResult.getActualOutput().get();
        actualOutput2.setText(actualOutputString);
    }

    public void fillPane3(TestCaseResult testCaseResult) {
        String inputString = testCaseResult.getInput();
        input3.setText(inputString);
        String expectedOutputString = testCaseResult.getExpectedOutput();
        expectedOutput3.setText(expectedOutputString);
        String actualOutputString = testCaseResult.getActualOutput().get();
        actualOutput3.setText(actualOutputString);
    }

    public void showGrading1(TestCaseResult testCaseResult) {
        if (testCaseResult.isSuccessful()) {
            testCasePane1.setText("Test Case 1 (PASSED)");
        } else {
            testCasePane1.setText("Test Case 1 (FAILED)");
        }
    }

    public void showGrading2(TestCaseResult testCaseResult) {
        if (testCaseResult.isSuccessful()) {
            testCasePane2.setText("Test Case 2 (PASSED)");
        } else {
            testCasePane2.setText("Test Case 2 (FAILED)");
        }
    }

    public void showGrading3(TestCaseResult testCaseResult) {
        if (testCaseResult.isSuccessful()) {
            testCasePane3.setText("Test Case 3 (PASSED)");
        } else {
            testCasePane3.setText("Test Case 3 (FAILED)");
        }
    }
}
