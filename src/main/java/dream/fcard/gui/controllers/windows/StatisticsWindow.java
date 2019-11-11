package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.logging.Logger;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.logic.stats.DeckStats;
import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.stats.UserStats;
import dream.fcard.logic.stats.StatsHolder;
import dream.fcard.model.Deck;
import dream.fcard.util.stats.SessionListUtil;
import dream.fcard.util.stats.StatsDisplayUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

/**
 * Window to display user's statistics.
 */
public class StatisticsWindow extends ScrollPane {
    @FXML
    private Label totalSessions;
    @FXML
    private Label totalSessionsExplainer;
    @FXML
    private Label sessionsThisWeek;
    @FXML
    private Label sessionsThisWeekExplainer;
    @FXML
    private Label numDecksTotal;
    @FXML
    private Label numDecksTotalExplainer;
    @FXML
    private Label numCardsTotal;
    @FXML
    private Label numCardsTotalExplainer;
    @FXML
    private Label totalDuration;
    @FXML
    private Label averageDuration;
    @FXML
    private ScrollPane sessionsScrollPane;
    @FXML
    private TableView<Session> sessionsTableView;
    @FXML
    private Label testSessionsTotal;
    @FXML
    private Label testSessionsTotalExplainer;
    @FXML
    private Label testSessionsThisWeek;
    @FXML
    private Label testSessionsThisWeekExplainer;
    @FXML
    private Label averageScore;
    @FXML
    private ScrollPane deckTableScrollPane;
    @FXML
    private TableView<Deck> deckTableView;

    private UserStats userStats;
    private DeckStats deckStats;

    private Logger logger = LogsCenter.getLogger(StatisticsWindow.class);

    /** Creates a new instance of StatisticsWindow. */
    public StatisticsWindow() {
        logger.info("Opening a StatisticsWindow...");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                .getResource("/view/Windows/StatisticsWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.userStats = StatsHolder.getUserStats();
        this.deckStats = StatsHolder.getDeckStats();
        displaySummaryStats();

        this.sessionsTableView = StatsDisplayUtil.getUserSessionsTableView();
        this.sessionsScrollPane.setContent(sessionsTableView);

        this.deckTableView = StatsDisplayUtil.getDeckTableView();
        this.deckTableScrollPane.setContent(deckTableView);
        allowDeckStatisticsWindowToBeOpened();
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displaySummaryStats() {
        SessionList totalSessionList = userStats.getSessionList();

        int numSessions = totalSessionList.getNumberOfSessions();
        this.totalSessions.setText(String.valueOf(numSessions));
        this.totalSessionsExplainer.setText("login"
            + (numSessions == 1 ? " session" : " sessions") + " all time");

        SessionList sublistForThisWeek = SessionListUtil.getSublistForThisWeek(totalSessionList);
        int numSessionsThisWeek = sublistForThisWeek.getNumberOfSessions();
        this.sessionsThisWeek.setText(String.valueOf(numSessionsThisWeek));
        this.sessionsThisWeekExplainer.setText("login"
            + (numSessionsThisWeek == 1 ? " session" : " sessions") + " this week");

        int numDecks = deckStats.getTotalNumberOfDecks();
        this.numDecksTotal.setText(String.valueOf(numDecks));
        this.numDecksTotalExplainer.setText(numDecks == 1 ? " deck" : "decks");

        int numCards = deckStats.getTotalNumberOfCards();
        this.numCardsTotal.setText(String.valueOf(numCards));
        this.numCardsTotalExplainer.setText(numCards == 1 ? " card" : " cards");

        String duration = userStats.getSessionList().getTotalDurationAsString();
        this.totalDuration.setText("Total login duration: " + duration);

        String averageDuration = userStats.getSessionList().getAverageDurationAsString();
        this.averageDuration.setText("Average duration per login: " + averageDuration);

        SessionList totalTestSessionList = deckStats.getTotalSessionList();
        int numTestSessions = totalTestSessionList.getNumberOfSessions();
        this.testSessionsTotal.setText(String.valueOf(numTestSessions));
        this.testSessionsTotalExplainer.setText("total test "
            + (numTestSessions == 1 ? "session" : "sessions") + " all time");

        SessionList testSessionSublistForThisWeek = SessionListUtil.getSublistForThisWeek(
            totalTestSessionList);
        int numTestSessionsThisWeek = testSessionSublistForThisWeek.getNumberOfSessions();
        this.testSessionsThisWeek.setText(String.valueOf(numTestSessionsThisWeek));
        this.testSessionsThisWeekExplainer.setText("total test "
            + (numTestSessionsThisWeek == 1 ? "session" : "sessions") + " this week");

        this.averageScore.setText(deckStats.getAverageScore());
    }

    /** Allows the relevant DeckStatisticsWindow to be opened when a row of the deckTableView is double-clicked. */
    private void allowDeckStatisticsWindowToBeOpened() {
        //@@author nattanyz-reused
        // solution adapted from https://stackoverflow.com/questions/30191264/javafx-tableview-
        // how-to-get-the-row-i-clicked
        this.deckTableView.setRowFactory(tv -> {
            TableRow<Deck> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2) {
                    Deck selectedDeck = row.getItem();
                    StatsDisplayUtil.openDeckStatisticsWindow(selectedDeck);
                }
            });
            return row;
        });
        //@@author
    }
}
