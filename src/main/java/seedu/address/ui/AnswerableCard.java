package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.Answerable;

/**
 * An UI component that displays information of a {@code Answerable}.
 */
public class AnswerableCard extends UiPart<Region> {

    private static final String FXML = "AnswerableListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Answerable answerable;
    public static int questionNumbering;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private FlowPane answer1;
    @FXML
    private Label id;
    @FXML
    private Label questionNumber;
    @FXML
    private Label difficulty;
    @FXML
    private Label category;
    @FXML
    private FlowPane tags;

    public AnswerableCard(Answerable answerable, int displayedIndex) {
        super(FXML);
        this.answerable = answerable;

        id.setText(displayedIndex + ". ");
        question.setText(answerable.getQuestion().fullQuestion);
        difficulty.setText(answerable.getDifficulty().value);
        category.setText(answerable.getCategory().value);

        //To set the individual answers to the answer flowPane
        answer1.getChildren().add(new Label (answerable.getAnswerSet().combinedAnswerSet.toString()));

        answerable.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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
        String fullString = String.format("%s %s", questionNumbering, answer.toStringNoBraces());
        return fullString;
    }
}
