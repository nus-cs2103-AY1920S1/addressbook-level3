package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.question.Question;

/**
 * An UI component that displays information of a {@code Question}.
 */
public class QuestionCard extends UiPart<Region> {
    private static final String FXML = "QuestionListCard.fxml";

    public final Question question;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label questionBody;
    @FXML
    private Label answer;
    @FXML
    private Label subject;
    @FXML
    private Label difficulty;

    public QuestionCard(Question question, int displayedIndex) {
        super(FXML);
        this.question = question;
        id.setText(displayedIndex + ". ");
        questionBody.setText(question.getQuestionBody().body);
        answer.setText(question.getAnswer().answer);
        subject.setText(question.getSubject().subject);
        difficulty.setText(question.getDifficulty().difficulty);
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
