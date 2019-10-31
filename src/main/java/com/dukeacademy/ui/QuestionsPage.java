package com.dukeacademy.ui;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.observable.Observable;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Controller for questions page. The questions page is where the user is able to browse all available questions
 * and open individual questions for viewing.
 */
public class QuestionsPage extends UiPart<Region> {
    private static final String FXML = "QuestionsPage.fxml";

    @FXML
    private StackPane questionListPanelPlaceholder;

    @FXML
    private StackPane problemStatementDisplayPlaceholder;

    private ProblemStatementPanel problemStatementPanel;

    public QuestionsPage(ObservableList<Question> questions, Observable<Question> selectedQuestion) {
        super(FXML);

        QuestionListPanel questionListPanel = new QuestionListPanel(questions);
        questionListPanelPlaceholder.getChildren().add(questionListPanel.getRoot());

        problemStatementPanel = new ProblemStatementPanel();
        problemStatementDisplayPlaceholder.getChildren().add(problemStatementPanel.getRoot());

        selectedQuestion.addListener(question -> {
            if (question != null) {
                problemStatementPanel.setProblemStatement(question.getDescription());
            }
        });
    }
}
