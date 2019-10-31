package seedu.revision.ui.answerables;

import java.util.Comparator;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.ui.UiPart;

/**
 * An UI component that displays information of a {@code Answerable}.
 */
public class AnswerableCardWithAnswers extends UiPart<Region> {

    private static Set<Answer> combinedAnswerSet;
    private static final String FXML = "AnswerableListCard.fxml";
    public final Answerable answerable;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    @FXML
    private HBox cardPane;
    @FXML
    private Label questionType;
    @FXML
    private Label question;
    @FXML
    private FlowPane answer;
    //    @FXML
    //    private FlowPane correctAnswers;
    //    @FXML
    //    private FlowPane wrongAnswers;
    @FXML
    private Label id;
    @FXML
    private Label questionNumber;
    @FXML
    private Label difficulty;
    @FXML
    private FlowPane categories;

    public AnswerableCardWithAnswers(Answerable answerable, int displayedIndex) {
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
        question.setText(answerable.getQuestion().value);
        difficulty.setText("Difficulty: " + answerable.getDifficulty().value);
        //        answerable.getCorrectAnswerSet().stream()
        //                .sorted(Comparator.comparing(correctAnswer -> correctAnswer.answer))
        //                .forEach(correctAnswer -> correctAnswers.getChildren().add(new Label(correctAnswer.answer)));
        //        answerable.getWrongAnswerSet().stream()
        //                .sorted(Comparator.comparing(wrongAnswer -> wrongAnswer.answer))
        //                .forEach(wrongAnswer -> wrongAnswers.getChildren().add(new Label(wrongAnswer.answer)));

        //To set the individual answers to the answer flowPane
        answerable.getCombinedAnswerList().stream()
                .sorted(Comparator.comparing(answer -> answer.toString()))
                .forEach(answer -> this.answer.getChildren().add(new Label(answer.toString())));

        //        answer.getChildren().add(new Label (answerable.getCombinedAnswerSet().toString()));

        answerable.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.categoryName))
                .forEach(category -> categories.getChildren().add(new Label(category.categoryName)));

    }

    public static Set<Answer> getCombinedAnswerSet() {
        return combinedAnswerSet;
    }

    public static void setCombinedAnswerSet(Set<Answer> combinedAnswerSet) {
        AnswerableCardWithAnswers.combinedAnswerSet = combinedAnswerSet;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerableCardWithAnswers)) {
            return false;
        }

        // state check
        AnswerableCardWithAnswers card = (AnswerableCardWithAnswers) other;
        return id.getText().equals(card.id.getText())
                && answerable.equals(card.answerable);
    }
}
