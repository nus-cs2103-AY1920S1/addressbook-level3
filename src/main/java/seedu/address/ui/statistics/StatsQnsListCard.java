package seedu.address.ui.statistics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.quiz.QuizResult;
import seedu.address.ui.panels.PanelComponent;

/**
 * An UI component that displays information of a {@code QuizResult}.
 */
public class StatsQnsListCard extends PanelComponent<Region> {
    private static final String FXML = "StatsQnsListCard.fxml";

    public final QuizResult quizResult;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label questionBody;
    @FXML
    private Label answer;
    @FXML
    private Label result;
    @FXML
    private Label subject;
    @FXML
    private Label difficulty;
    @FXML
    private Label date;

    public StatsQnsListCard(QuizResult quizResult, int displayedIndex) {
        super(FXML);
        this.quizResult = quizResult;
        id.setText(displayedIndex + ". ");
        questionBody.setText(quizResult.getQuestionBody().body);
        answer.setText("Your answer: " + quizResult.getAnswer().answer);
        result.setText("Result: " + quizResult.getResultToString());
        subject.setText("Subject: " + quizResult.getSubject().subject);
        difficulty.setText("Difficulty: " + quizResult.getDifficulty().difficulty);
        date.setText("Attempted on: " + quizResult.getQuizTime());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.ui.panels.QuestionListCard)) {
            return false;
        }

        // state check
        StatsQnsListCard card = (StatsQnsListCard) other;
        return id.getText().equals(card.id.getText())
                && quizResult.equals(card.quizResult);
    }
}
