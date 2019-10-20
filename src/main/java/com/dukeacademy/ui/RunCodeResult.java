package com.dukeacademy.ui;

import com.dukeacademy.model.program.TestCaseResult;
import com.dukeacademy.model.question.TestCase;

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
    private Label input;

    @FXML
    private Label expectedOutput;

    @FXML
    private Label userOutput;

    @FXML
    private TitledPane testCasePane;

    public RunCodeResult(TestCase testCase, TestCaseResult testCaseResult) {
        super(FXML);

        String inputString = testCase.getInput();
        input.setText(inputString);
        String expectedOutputString = testCaseResult.getExpectedOutput();
        expectedOutput.setText(expectedOutputString);
        String actualOutputString = testCaseResult.getActualOutput().get();
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
