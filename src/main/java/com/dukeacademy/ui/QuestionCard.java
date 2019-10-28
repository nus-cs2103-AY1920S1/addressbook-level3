package com.dukeacademy.ui;

import java.util.Comparator;

import com.dukeacademy.model.question.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Question}.
 */
class QuestionCard extends UiPart<Region> {

    private static final String FXML = "QuestionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on QuestionBank level 4</a>
     */

    private final Question question;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label difficulty;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    /**
     * Instantiates a new Question card.
     *
     * @param question       the question
     * @param displayedIndex the displayed index
     */
    public QuestionCard(Question question, int displayedIndex) {
        super(FXML);
        this.question = question;
        id.setText(displayedIndex + ". ");
        title.setText(question.getTitle());
        difficulty.setText("Difficulty: " + question.getDifficulty().toString());
        status.setText("Status: " + question.getStatus().toString());
        question.getTopics().stream()
                .sorted(Comparator.comparing(Enum::toString))
                .forEach(topic-> tags.getChildren().add(new Label(topic.toString())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionCard)) {
            return false;
        }

        // state check
        QuestionCard card = (QuestionCard) other;
        return id.getText().equals(card.id.getText())
                && question.equals(card.question);
    }
}
