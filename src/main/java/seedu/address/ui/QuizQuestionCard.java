package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.Question;

/**
 * An UI component that displays information of a {@code Question}.
 */
public class QuizQuestionCard extends UiPart<Region> {

    private static final String FXML = "QuizQuestionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As
     * a consequence, UI elements' variable names cannot be set to such keywords or an exception
     * will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on
     * AddressBook level 4</a>
     */

    public final Question question;

    @FXML
    private HBox cardPane;
    @FXML
    private Label questionLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label id;

    // For MCQ type
    @FXML
    private FlowPane optionFp;
    @FXML
    private Label optionALabel;
    @FXML
    private Label optionBLabel;
    @FXML
    private Label optionCLabel;
    @FXML
    private Label optionDLabel;

    public QuizQuestionCard(Question question) {
        super(FXML);

        this.question = question;
        id.setVisible(false);
        id.setManaged(true);
        initialiseCard(question);
    }

    public QuizQuestionCard(Question question, int displayedIndex) {
        super(FXML);

        this.question = question;
        initialiseCard(question);
        id.setText(displayedIndex + ". ");
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
        QuizQuestionCard card = (QuizQuestionCard) other;
        return questionLabel.getText().equals(card.questionLabel.getText())
                && question.equals(card.question);
    }

    /**
     * Initialises the variables.
     * @param question object to be used in the card.
     */
    private void initialiseCard(Question question) {
        questionLabel.setText(question.getQuestion());

        if (question instanceof McqQuestion) {
            McqQuestion mcq = (McqQuestion) question;
            optionALabel.setText("A) " + mcq.getOptionA());
            optionBLabel.setText("B) " + mcq.getOptionB());
            optionCLabel.setText("C) " + mcq.getOptionC());
            optionDLabel.setText("D) " + mcq.getOptionD());
            typeLabel.setText("MCQ");

            optionFp.setVisible(true);
        } else {
            typeLabel.setText("Open Ended");
            optionFp.setManaged(false); // Free occupied space
        }
    }
}

