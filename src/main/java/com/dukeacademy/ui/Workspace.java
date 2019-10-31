package com.dukeacademy.ui;

import com.dukeacademy.logic.program.UserProgramChannel;
import com.dukeacademy.model.program.TestResult;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.observable.Observable;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Controller for the workspace page. The workspace page is where the user attempts questions and views his results.
 */
public class Workspace extends UiPart<Region> {
    private static final String FXML = "Workspace.fxml";

    @FXML
    private StackPane problemStatementPanelPlaceholder;

    @FXML
    private StackPane programEvaluationPanelPlaceholder;

    @FXML
    private AnchorPane editorPlaceholder;

    private ProblemStatementPanel problemStatementPanel;
    private Editor editor;

    public Workspace(Observable<Question> attemptingQuestion, Observable<TestResult> resultObservable) {
        super(FXML);

        problemStatementPanel = new ProblemStatementPanel();
        attemptingQuestion.addListener(question -> {
            if (question != null) {
                this.problemStatementPanel.setProblemStatement(question.getDescription());
            }
        });
        problemStatementPanelPlaceholder.getChildren().add(problemStatementPanel.getRoot());

        ProgramEvaluationPanel programEvaluationPanel = new ProgramEvaluationPanel(resultObservable);
        programEvaluationPanelPlaceholder.getChildren().add(programEvaluationPanel.getRoot());

        editor = new Editor(attemptingQuestion);
        editorPlaceholder.getChildren().add(editor.getRoot());
    }

    public UserProgramChannel getUserProgramChannel() {
        return editor::getUserProgram;
    }
}
