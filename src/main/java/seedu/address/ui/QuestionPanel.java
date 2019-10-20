package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.Question;

/**
 * Displays Question objects.
 */
public class QuestionPanel extends UiPart<Region> {

    private static final String FXML = "QuestionPanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As
     * a consequence, UI elements' variable names cannot be set to such keywords or an exception
     * will be thrown by JavaFX during runtime.
     */

    private final Question question;

    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;

    public QuestionPanel(Question question) {
        super(FXML);
        this.question = question;

        questionLabel.setText("Question: " + question.getQuestion());
        if (question instanceof McqQuestion) { // Set options for mcq questions
            McqQuestion mcq = (McqQuestion) question;
            questionLabel.setText(questionLabel.getText() + "\n\nA) " + mcq.getOptionA());
            questionLabel.setText(questionLabel.getText() + "\nB) " + mcq.getOptionB());
            questionLabel.setText(questionLabel.getText() + "\nC) " + mcq.getOptionC());
            questionLabel.setText(questionLabel.getText() + "\nD) " + mcq.getOptionD());
        }

        answerLabel.setText("Answer: " + question.getAnswer());
    }

    /**
     * Shows the current answer of the question.
     */
    public void showAnswer() {
        answerLabel.setVisible(true);
    }

    /**
     * Sets visibility of question panel.
     */
    public void setVisible(boolean isVisible) {
        getRoot().setVisible(isVisible);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionPanel)) {
            return false;
        }

        // state check
        QuestionPanel questionPanel = (QuestionPanel) other;
        return questionLabel.getText().equals(questionPanel.questionLabel.getText())
            && question.equals(questionPanel.question);
    }
}
