package seedu.revision.ui.answerables;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.ui.UiPart;

/**
 * An UI component that displays information of a {@code Answerable}.
 */
public class AnswerableCard extends UiPart<Region> {

    private static final String FXML = "AnswerableListCard.fxml";
    public final Answerable answerable;

    @FXML
    private HBox cardPane;
    @FXML
    private Label questionType;
    @FXML
    private Label question;
    @FXML
    private FlowPane answerPane;
    @FXML
    private Label id;
    @FXML
    private Label questionNumber;
    @FXML
    private Label difficulty;
    @FXML
    private FlowPane categories;

    public AnswerableCard(Answerable answerable, int displayedIndex) {
        super(FXML);
        this.answerable = answerable;
        if (answerable instanceof Mcq) {
            questionType.setText("Question type: MCQ");
        } else if (answerable instanceof TrueFalse) {
            questionType.setText("Question type: T/F");
        } else {
            questionType.setText("Question type: SAQ");
        }
        id.setText(displayedIndex + ". ");
        question.setText(answerable.getQuestion().question);
        difficulty.setText("Difficulty: " + answerable.getDifficulty().difficulty);
        answerable.getCorrectAnswerList().stream()
                .sorted(Comparator.comparing(correctAnswer -> correctAnswer.answer))
                .forEach(correctAnswer -> answerPane.getChildren().add(correctLabel(correctAnswer.answer)));
        answerable.getWrongAnswerList().stream()
                .sorted(Comparator.comparing(wrongAnswer -> wrongAnswer.answer))
                .forEach(wrongAnswer -> answerPane.getChildren().add(new Label(wrongAnswer.answer)));

        answerable.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.category))
                .forEach(category -> categories.getChildren().add(new Label(category.category)));

    }

    private Label correctLabel(String text) {
        Label correctL = new Label(text);
        correctL.setStyle("-fx-background-color: #42b883");
        return correctL;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerableCard)) {
            return false;
        }

        // state check
        AnswerableCard card = (AnswerableCard) other;
        return id.getText().equals(card.id.getText())
                && answerable.equals(card.answerable);
    }
}

