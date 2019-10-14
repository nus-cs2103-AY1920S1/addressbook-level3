package com.dukeacademy.ui;

import com.dukeacademy.model.solution.TestCase;
import com.dukeacademy.model.solution.TestCaseResult;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;

public class RunCodeResult extends UiPart<Region> {
    private static final String FXML = "RunCodeResult.fxml";

    @FXML
    private Label input;

    @FXML
    private Label expectedOutput;

    @FXML
    private Label userOutput;

    @FXML
    private TitledPane testCasePane;

    private TestCase testCase;
    private TestCaseResult testCaseResult;

    public RunCodeResult(TestCase testCase, TestCaseResult testCaseResult) {
        super(FXML);
        this.testCase = testCase;
        this.testCaseResult = testCaseResult;

        String inputString = testCase.getInput();
        input.setText(inputString);
        String expectedOutputString = testCaseResult.getExpectedOutput();
        expectedOutput.setText(expectedOutputString);
        String actualOutputString = testCaseResult.getActualOutput();
        userOutput.setText(actualOutputString);

        String title = "title of test case pane";

        if (testCaseResult.isSuccessful()) {
            title = testCasePane.getText() + " (PASSED)";
        } else {
            title = testCasePane.getText() + " (FAILED)";
        }

        testCasePane.setText(title);
    }
}
