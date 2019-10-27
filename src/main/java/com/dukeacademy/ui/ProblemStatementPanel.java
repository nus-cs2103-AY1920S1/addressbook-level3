package com.dukeacademy.ui;

import static java.util.Objects.requireNonNull;

import com.dukeacademy.observable.Observable;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;



/**
 * The type Problem statement panel.
 */
class ProblemStatementPanel extends UiPart<Region> {
    private static final String FXML = "ProblemStatement.fxml";

    @FXML
    private TextArea problemStatementDisplay;

    /**
     * Instantiates a new Problem Statement Display.
     */
    public ProblemStatementPanel() {
        super(FXML);
    }

    /**
     * Instantiates a new problem statement panel.
     *
     * @param problemStatement the problem statement
     */
    public ProblemStatementPanel(Observable<String> problemStatement) {
        super(FXML);
        problemStatement.addListener(description -> {
            problemStatementDisplay.setText(description);
        });
        problemStatementDisplay.setWrapText(true);
    }

    /**
     * Displays problem statement for a particular question.
     *
     * @param problemStatement the feedback to user
     */
    public void setProblemStatement(String problemStatement) {
        requireNonNull(problemStatement);
        problemStatementDisplay.setText(problemStatement);
    }
}
