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
    private Label totalSessions;
    @FXML
    private Label totalDuration;
    @FXML
    private Label sessionsThisWeek;
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
        this.numCards.setText("Number of cards in deck: " + numberOfCards
            + (numberOfCards == 1 ? " card" : " cards"));

        SessionList sublistForThisWeek = SessionListUtil.getSublistForThisWeek(
            testSessionList);
        int numSessionsThisWeek = sublistForThisWeek.getNumberOfSessions();
        this.sessionsThisWeek.setText("Total test sessions this week: " + numSessionsThisWeek
            + (numSessionsThisWeek == 1 ? " session" : " sessions"));

        int numSessions = this.testSessionList.getNumberOfSessions();
        totalSessions.setText("Total test sessions: " + numSessions
            + (numSessions == 1 ? " session" : " sessions"));

        String duration = this.testSessionList.getTotalDurationAsString();
        totalDuration.setText("Total test duration: " + duration);
    }
}
