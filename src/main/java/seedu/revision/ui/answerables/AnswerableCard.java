package seedu.revision.ui.answerables;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.ui.UiPart;

/**
 * An UI component that displays information of a {@code Answerable}.
 */
public class AnswerableCard extends UiPart<Region> {

    private static int questionNumbering;
    private static final String FXML = "AnswerableListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Answerable answerable;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private FlowPane answer;
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
        id.setText(displayedIndex + ". ");
        question.setText(answerable.getQuestion().value);
        difficulty.setText("Difficulty: " + answerable.getDifficulty().value);
        answerable.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.categoryName))
                .forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
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

    private static String convert (Answer answer, Integer questionNumbering) {
        String fullString = String.format("%s %s", questionNumbering, answer.toString());
        return fullString;
    }
}
