package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.util.AppUtil;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.ui.UiPart;

/**
 * Panel containing the word bank statistics
 */
public class WordBankStatisticsPanel extends UiPart<Region> {

    public static final int PROGRESS_GAMES_NUM = 30;

    private static final String FXML = "WordBankStatisticsPanel.fxml";

    static final String BADGE_PATH = "/images/badges/";
    static final Image BADGE_1_BNW = AppUtil.getImage(BADGE_PATH + "normal_badge_bnw.png");
    static final Image BADGE_2_BNW = AppUtil.getImage(BADGE_PATH + "medium_badge_bnw.png");
    static final Image BADGE_3_BNW = AppUtil.getImage(BADGE_PATH + "hard_badge_bnw.png");
    static final Image BADGE_1_COLOR = AppUtil.getImage(BADGE_PATH + "normal_badge.png");
    static final Image BADGE_2_COLOR = AppUtil.getImage(BADGE_PATH + "medium_badge.png");
    static final Image BADGE_3_COLOR = AppUtil.getImage(BADGE_PATH + "hard_badge.png");

    @FXML
    Label title;

    @FXML
    ImageView badge1;

    @FXML
    ImageView badge2;

    @FXML
    ImageView badge3;

    @FXML
    Label playedTimesText;

    @FXML
    Label highScoreText;

    @FXML
    Label fastestClearText;

    @FXML
    StackPane progressChartPlaceholder;

    @FXML
    StackPane mostMissedListPlaceholder;

    public WordBankStatisticsPanel(WordBankStatistics wbStatistics) {
        super(FXML);
        this.title.setText(wbStatistics.getWordBankName());

        // todo set depending on received badges
        this.badge1.setImage(BADGE_1_BNW);
        this.badge2.setImage(BADGE_2_BNW);
        this.badge3.setImage(BADGE_3_BNW);

        this.playedTimesText.setText(wbStatistics.getGamesPlayed()
            + (wbStatistics.getGamesPlayed() == 1
                ? " time"
                : " times"));
        this.highScoreText.setText(wbStatistics.getHighestScore() + "");
        this.fastestClearText.setText(wbStatistics.getFastestClear().isPresent()
            ? String.format("%.2fs", wbStatistics.getFastestClear().get())
            : " - ");

        // init progress chart
        progressChartPlaceholder.getChildren().add(
                new WordBankProgressChart(wbStatistics, PROGRESS_GAMES_NUM).getRoot());


        // todo set most missed cards
    }
}
