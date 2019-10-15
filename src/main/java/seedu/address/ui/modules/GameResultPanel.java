package seedu.address.ui.modules;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.card.Card;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.ScoreGrade;
import seedu.address.ui.UiPart;

import java.util.List;

/**
 * Panel containing the game result.
 */
public class GameResultPanel extends UiPart<Region> {
    private static final String FXML = "GameResultPanel.fxml";

    private static final String BADGE_PATH = "/images/badges/";
    private static final Image BADGE_1_BNW = AppUtil.getImage(BADGE_PATH + "normal_badge_bnw.png");
    private static final Image BADGE_2_BNW = AppUtil.getImage(BADGE_PATH + "medium_badge_bnw.png");
    private static final Image BADGE_3_BNW = AppUtil.getImage(BADGE_PATH + "hard_badge_bnw.png");
    private static final Image BADGE_1_COLOR = AppUtil.getImage(BADGE_PATH + "normal_badge.png");
    private static final Image BADGE_2_COLOR = AppUtil.getImage(BADGE_PATH + "medium_badge.png");
    private static final Image BADGE_3_COLOR = AppUtil.getImage(BADGE_PATH + "hard_badge.png");

    @FXML
    private Label title;

    @FXML
    private ImageView badge1;

    @FXML
    private ImageView badge2;

    @FXML
    private ImageView badge3;

    @FXML
    private Label scoreText;

    @FXML
    private Label timeTakenText;

    @FXML
    private VBox wrongAnswersBox;

    @FXML
    private StackPane wrongAnswersList;

    public GameResultPanel(GameStatistics gameStatistics) {
        super(FXML);
        AnchorPane.setLeftAnchor(title, 0.0);
        title.setText(gameStatistics.getTitle());

        // set badges todo set depending on received badges
        badge1.setImage(BADGE_1_BNW);
        badge2.setImage(BADGE_2_BNW);
        badge3.setImage(BADGE_3_BNW);

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

        // init wrongAnswersBox
        if (gameStatistics.allCorrect()) {
            wrongAnswersBox.setVisible(false);
        } else {
            List<Card> wrongCards = gameStatistics.getWrongCards();
            CardBoxPanel cardBoxPanel = new CardBoxPanel(FXCollections.observableArrayList(wrongCards));
            wrongAnswersList.getChildren().add(cardBoxPanel.getRoot());
        }
    }
}
