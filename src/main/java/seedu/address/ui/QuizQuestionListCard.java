package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.question.Question;

/**
 * An UI component that displays information of a {@code Question} in quiz.
 */
public class QuizQuestionListCard extends UiPart<Region> {
    private static final String FXML = "QuizQuestionListCard";

    public final Question question;

    @FXML
    private HBox cardPane;
    @FXML
    private Label subject;
    @FXML
    private Label id;
    @FXML
    private Label questionBody;

    public QuizQuestionListCard(Question question, int displayedIndex) {
        super(FXML);
        this.question = question;
        id.setText(displayedIndex + ". ");
        subject.setText(question.getSubject().toString());
        questionBody.setText(question.getQuestionBody().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuizQuestionListCard)) {
            return false;
        }

        // state check
        QuizQuestionListCard card = (QuizQuestionListCard) other;
        return id.getText().equals(card.id.getText())
                && question.equals(card.question);
    }
}
