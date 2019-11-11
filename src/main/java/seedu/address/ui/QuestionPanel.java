package seedu.address.ui;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.Question;
import seedu.address.ui.util.OptionType;

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
    private HashMap<OptionType, Integer> pollResults;

    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;
    @FXML
    private Label pollHintLabel;
    @FXML
    private VBox questionVbox;
    @FXML
    private VBox optionsBox;

    // Options
    @FXML
    private Label optionAlabel;
    @FXML
    private Label pollAlabel;
    @FXML
    private Label optionBlabel;
    @FXML
    private Label pollBlabel;
    @FXML
    private Label optionClabel;
    @FXML
    private Label pollClabel;
    @FXML
    private Label optionDlabel;
    @FXML
    private Label pollDlabel;


    public QuestionPanel(Question question) {
        super(FXML);
        this.question = question;

        questionVbox.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());

        questionLabel.setText("Question: " + question.getQuestion());
        if (question instanceof McqQuestion) { // Set options for mcq questions
            pollResults = new HashMap<>();
            pollResults.put(OptionType.OPTION_A, 0);
            pollResults.put(OptionType.OPTION_B, 0);
            pollResults.put(OptionType.OPTION_C, 0);
            pollResults.put(OptionType.OPTION_D, 0);
            pollHintLabel.setVisible(true);
            pollHintLabel.setText("Polling available!"
                + "\n(Use the '1-4' key to poll options A-D respectively)");

            optionsBox.setVisible(true);
            McqQuestion mcq = (McqQuestion) question;
            optionAlabel.setText("A) " + mcq.getOptionA());
            optionBlabel.setText("B) " + mcq.getOptionB());
            optionClabel.setText("C) " + mcq.getOptionC());
            optionDlabel.setText("D) " + mcq.getOptionD());
        } else {
            optionsBox.setVisible(false);
            optionsBox.setManaged(false);
            pollHintLabel.setManaged(false);
        }

        answerLabel.setText("Answer: " + question.getAnswer());
    }

    /**
     * Shows or hide the current answer of the question.
     */
    public void showHideAnswer() {
        answerLabel.setVisible(!answerLabel.isVisible());
    }

    /**
     * Adds the corresponding option pressed to the poll results.
     */
    public void addToPoll(OptionType option) {
        if (pollResults == null) { // Only allow mcq to poll
            return;
        }

        Integer result = pollResults.get(option);
        if (result < 50) { // Set limit to polling for an option
            result++;
            pollResults.put(option, result);
        }

        switch (option) {
        case OPTION_A:
            pollAlabel.setText(result.toString());
            pollAlabel.setPrefWidth(result * 10);
            pollAlabel.setVisible(true);
            break;
        case OPTION_B:
            pollBlabel.setText(result.toString());
            pollBlabel.setPrefWidth(result * 10);
            pollBlabel.setVisible(true);
            break;
        case OPTION_C:
            pollClabel.setText(result.toString());
            pollClabel.setPrefWidth(result * 10);
            pollClabel.setVisible(true);
            break;
        case OPTION_D:
            pollDlabel.setText(result.toString());
            pollDlabel.setPrefWidth(result * 10);
            pollDlabel.setVisible(true);
            break;
        default:
            break;
        }
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
