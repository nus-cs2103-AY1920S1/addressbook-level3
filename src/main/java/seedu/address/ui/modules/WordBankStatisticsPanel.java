package seedu.address.ui.modules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbankstats.CardStatistics;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.ui.UiPart;

/**
 * Panel containing the word bank statistics
 */
public class WordBankStatisticsPanel extends UiPart<Region> {

    private static final int HARDEST_CARDS_NUM_SHOW = 5;

    private static final String FXML = "WordBankStatisticsPanel.fxml";

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label title;

    @FXML
    private StackPane badgesRowPlaceholder;

    @FXML
    private Label playedTimesText;

    @FXML
    private Label highScoreText;

    @FXML
    private Label fastestClearText;

    @FXML
    private StackPane progressChartPlaceholder;

    @FXML
    private VBox mostMissedBox;

    @FXML
    private VBox mostMissedList;

    public WordBankStatisticsPanel(WordBankStatistics wbStatistics, ReadOnlyWordBank wordBank) {
        super(FXML);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.title.setText(wbStatistics.getWordBankName());

        badgesRowPlaceholder.getChildren().add(new BadgesRow(wbStatistics.isReceivedBadgeEasy(),
                wbStatistics.isReceivedBadgeNormal(), wbStatistics.isReceivedBadgeHard()).getRoot());

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
                new WordBankProgressChart(wbStatistics).getRoot());

        // init most missed list
        Map<CardStatistics, Card> mostMissedMap = new TreeMap<>((x, y) -> {
            double xCorrectRate = x.getNumCorrect() * 1.0 / x.getNumShown();
            double yCorrectRate = y.getNumCorrect() * 1.0 / y.getNumShown();
            if (Math.abs(xCorrectRate - yCorrectRate) < 1e-9) {
                if (y.getNumShown() == x.getNumShown()) {
                    return x.getCardId().compareTo(y.getCardId());
                } else {
                    return y.getNumShown() - x.getNumShown();
                }
            } else if (xCorrectRate > yCorrectRate) {
                return 1;
            } else {
                return -1;
            }
        });

        List<CardStatistics> mostMissedCardStats = wbStatistics.getMostMissedCardStats();
        for (CardStatistics cardStats : mostMissedCardStats) {
            wordBank.getCardList().stream()
                    .filter(x -> x.getId().equals(cardStats.getCardId()))
                    .findFirst()
                    .ifPresent(x -> {
                        mostMissedMap.put(cardStats, x);
                    });
        }
        if (mostMissedMap.isEmpty()) {
            mostMissedBox.setVisible(false);
            mostMissedBox.setMaxHeight(0);
        } else {
            mostMissedList.getChildren().addAll(mostMissedMap.entrySet().stream()
                    .limit(HARDEST_CARDS_NUM_SHOW)
                    .map(x -> new CardWithInfoCard(x.getValue(), x.getKey()).getRoot())
                    .collect(Collectors.toList()));
        }

    }
}
