package seedu.address.ui.modules;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.card.Card;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.ScoreGrade;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.ui.UiPart;


/**
 * Panel containing the game result.
 */
public class GameResultPanel extends UiPart<Region> {

    private static final String FXML = "GameResultPanel.fxml";

    @FXML
    private Label title;

    @FXML
    private StackPane badgesRowPlaceholder;

    @FXML
    private Label scoreText;

    @FXML
    private Label timeTakenText;

    @FXML
    private Label gameFeedbackHeader;

    @FXML
    private VBox wrongAnswersBox;

    @FXML
    private StackPane wrongAnswersList;

    @FXML
    private Label highScoreText;

    @FXML
    private Label fastestClearText;

    @FXML
    private StackPane progressChartPlaceholder;

    // todo this can be separated into several ui elements. currently very long method.
    public GameResultPanel(GameStatistics gameStatistics, WordBankStatistics wbStatistics) {
        super(FXML);
        AnchorPane.setLeftAnchor(title, 0.0);
        title.setText(gameStatistics.getTitle());

        badgesRowPlaceholder.getChildren().add(new BadgesRow(true, true, false).getRoot());

        // init score text
        int score = gameStatistics.getScore();
        ScoreGrade grade = gameStatistics.getGrade();
        scoreText.setText(score + "");
        switch (grade) {
        case HIGH:
            scoreText.setStyle("-fx-text-fill: #ADFF2F;");
            break;
        case MEDIUM:
            break;
        case LOW:
            scoreText.setStyle("-fx-text-fill: #FF69B4;");
            break;
        default:
            throw new IllegalArgumentException("This happens if there is an enum value not put as a case block");
        }

        // init time taken text
        timeTakenText.setText(String.format("%.2fs", gameStatistics.getSecTaken()));

        // init high score text
        highScoreText.setText(wbStatistics.getHighestScore().toString());

        // init fastest clear text
        fastestClearText.setText(
                wbStatistics.getFastestClear().isPresent()
                        ? String.format("%.2fs", wbStatistics.getFastestClear().get())
                        : " - ");

        // init wrongAnswersBox
        if (gameStatistics.allCorrect()) {
            wrongAnswersBox.setMaxHeight(0);
            gameFeedbackHeader.setText("Good Job! You answered everything correctly!");
        } else {
            List<Card> wrongCards = gameStatistics.getWrongCards();
            CardBoxPanel cardBoxPanel = new CardBoxPanel(FXCollections.observableArrayList(wrongCards));
            wrongAnswersList.getChildren().add(cardBoxPanel.getRoot());
            gameFeedbackHeader.setText("Remember these!");
        }

        // init progress chart
        progressChartPlaceholder.getChildren().add(
                new WordBankProgressChart(wbStatistics).getRoot());
    }
}
