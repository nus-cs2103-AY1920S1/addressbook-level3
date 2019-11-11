package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.logging.Logger;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.stats.StatsHolder;
import dream.fcard.model.Deck;
import dream.fcard.util.stats.SessionListUtil;
import dream.fcard.util.stats.StatsDisplayUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

/**
 * Window to display statistics for a given Deck.
 */
public class DeckStatisticsWindow extends ScrollPane {
    @FXML
    private Label windowTitle;
    @FXML
    private Label numCards;
    @FXML
    private Label numCardsExplainer;
    @FXML
    private Label totalSessions;
    @FXML
    private Label totalSessionsExplainer;
    @FXML
    private Label totalDuration;
    @FXML
    private Label sessionsThisWeek;
    @FXML
    private Label sessionsThisWeekExplainer;
    @FXML
    private Label averageScore;
    @FXML
    private ScrollPane testSessionsScrollPane;
    @FXML
    private TableView<Session> testSessionsTableView;

    private Deck deck;
    private SessionList testSessionList;

    private Logger logger = LogsCenter.getLogger(DeckStatisticsWindow.class);

    /** Creates a new instance of DeckStatisticsWindow. */
    public DeckStatisticsWindow(Deck deck) {
        logger.info("Opening a statistics window for " + deck.getDeckName());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                .getResource("/view/Windows/DeckStatisticsWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        windowTitle.setText("My statistics for deck: " + deck.getDeckName());
        this.deck = deck;
        this.testSessionList = deck.getTestSessionList();

        displaySummaryStats();

        this.testSessionsTableView = StatsDisplayUtil.getTestSessionsTableView(deck);
        this.testSessionsScrollPane.setContent(testSessionsTableView);
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displaySummaryStats() {
        int numberOfCards = deck.getNumberOfCards();
        this.numCards.setText(String.valueOf(numberOfCards));
        this.numCardsExplainer.setText((numberOfCards == 1 ? " card" : " cards") + " in this deck");

        int numSessions = this.testSessionList.getNumberOfSessions();
        this.totalSessions.setText(String.valueOf(numSessions));
        this.totalSessionsExplainer.setText("test" + (numSessions == 1 ? " session" : " sessions")
            + " all time");

        SessionList sublistForThisWeek = SessionListUtil.getSublistForThisWeek(
            testSessionList);
        int numSessionsThisWeek = sublistForThisWeek.getNumberOfSessions();
        this.sessionsThisWeek.setText(String.valueOf(numSessionsThisWeek));
        this.sessionsThisWeekExplainer.setText("test"
            + (numSessionsThisWeek == 1 ? " session" : " sessions") + " this week");

        String duration = this.testSessionList.getTotalDurationAsString();
        if (numSessions == 0) {
            // do not set text for total test duration; keep it as "0 hours 0 minutes 0 seconds"
            return;
        }
        totalDuration.setText("Total test duration: " + duration);

        this.averageScore.setText(deck.getAverageScore());
    }
}
