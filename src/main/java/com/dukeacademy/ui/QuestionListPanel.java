package com.dukeacademy.ui;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.question.Question;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class QuestionListPanel extends UiPart<Region> {
    private static final String FXML = "QuestionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionListPanel.class);

    @FXML
    private ListView<Question> questionListView;

    /**
     * Instantiates a new Question list panel.
     *
     * @param questionList the question list
     */
    public QuestionListPanel(ObservableList<Question> questionList) {
        super(FXML);
        questionListView.setItems(questionList);
        questionListView.setCellFactory(listView -> new QuestionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Question} using a {@code QuestionCard}.
     */
    private static class QuestionListViewCell extends ListCell<Question> {
        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuestionCard(question).getRoot());
            }
        }
    }

}
