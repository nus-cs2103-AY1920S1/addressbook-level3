package dream.fcard.gui.controllers.windows;

import java.io.IOException;

import dream.fcard.logic.stats.SessionList;
import dream.fcard.model.Deck;
import dream.fcard.util.stats.SessionListUtil;

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
    private TableView<Deck> testSessionsTableView;

    private Deck deck;
    private SessionList testSessionList;

    /** Creates a new instance of DeckStatisticsWindow. */
    public DeckStatisticsWindow(Deck deck) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                .getResource("/view/Windows/DeckStatisticsWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            //TODO: replace with logger
            e.printStackTrace();
        }

        windowTitle.setText("My statistics for deck: " + deck.getDeckName());
        this.deck = deck;
        this.testSessionList = deck.getTestSessionList();
        //ArrayList<Deck> decks = State.getDecks();

        displaySummaryStats();

        //this.testSessionsTableView = StatsDisplayUtil.getTestSessionsTableView(deck);
        //this.sessionsScrollPane.setContent(sessionsTableView);
        //sessionsTableView = StatsDisplayUtil.getSessionsTableView(deckStats.getSessionList());
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displaySummaryStats() {
        SessionList testSessionList = deck.getTestSessionList();
        int numberOfCards = deck.getNumberOfCards();
        this.numCards.setText("Number of cards in deck: " + numberOfCards
            + (numberOfCards == 1 ? " card" : " cards"));

        SessionList sublistForThisWeek = SessionListUtil.getSublistForThisWeek(
            testSessionList);
        int numSessionsThisWeek = sublistForThisWeek.getNumberOfSessions();
        this.sessionsThisWeek.setText("Total test sessions this week: " + numSessionsThisWeek
            + (numSessionsThisWeek == 1 ? " session" : " sessions"));

        //int numSessions = testSessionList.getNumberOfSessions();
        //totalSessions.setText("Total login sessions: " + numSessions
        //    + (numSessions == 1 ? " session" : " sessions"));
        //
        //String duration = testSessionList.getTotalDurationAsString();
        //totalDuration.setText("Total login duration: " + duration);
    }
}
