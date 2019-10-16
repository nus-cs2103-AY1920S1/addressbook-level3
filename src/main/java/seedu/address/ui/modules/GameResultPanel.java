package seedu.address.ui.modules;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.card.Card;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.ScoreData;
import seedu.address.statistics.ScoreGrade;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.ui.UiPart;

import java.util.List;

/**
 * Panel containing the game result.
 */
public class GameResultPanel extends UiPart<Region> {

    public static final int PROGRESS_GAMES_NUM = 30;
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
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                if (object.intValue() != object.doubleValue() || object.intValue() % 5 != 0)
                    return "";
                return "" + object.intValue();
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });
        xAxis.setTickUnit(1);
        xAxis.setMinorTickVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(Math.max(PROGRESS_GAMES_NUM, wbStatistics.getGamesPlayed()) + 0.5);
        xAxis.setLowerBound(Math.max(0, wbStatistics.getGamesPlayed() - PROGRESS_GAMES_NUM + 0.5));

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(ScoreData.MAX_SCORE + 9); // give some room at the top
        yAxis.setLowerBound(ScoreData.MIN_SCORE);
        yAxis.setTickUnit(10);

        LineChart<Number,Number> progressChart = new LineChart<>(xAxis,yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        List<ScoreData> scoreStats = wbStatistics.getScoreStats();
        for (int i = Math.max(0, scoreStats.size() - PROGRESS_GAMES_NUM); i < scoreStats.size(); ++i) {
            int gameIndex = i + 1;
            int curScore = scoreStats.get(i).getScore();
            series.getData().add(new XYChart.Data<>(gameIndex, curScore));
        }

        progressChart.setLegendVisible(false);
        progressChart.getData().add(series);
        progressChart.setMinHeight(200);

        progressChartPlaceholder.getChildren().add(progressChart);
    }
}
