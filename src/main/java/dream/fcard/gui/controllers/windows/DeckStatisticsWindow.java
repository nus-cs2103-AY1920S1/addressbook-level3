package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import dream.fcard.logic.stats.DeckStats;
import dream.fcard.logic.stats.Session;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.util.StatsDisplayUtil;

/**
 * Window to display statistics for a given Deck.
 */
public class DeckStatisticsWindow extends VBox {
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
    private TableView<Session> sessionsTableView;

    private Deck deck;
    private DeckStats deckStats;

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

        windowTitle.setText("Statistics for deck: " + deck.getDeckName());
        this.deck = deck;
        this.deckStats = deck.getDeckStats();
        //ArrayList<Deck> decks = State.getDecks();

        displaySummaryStats();
        sessionsTableView = StatsDisplayUtil.getSessionsTableView(deckStats.getSessionList());
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displaySummaryStats() {
        int numberOfCards = deckStats.getNumberOfCards();
        numCards.setText("Number of cards in deck: " + numberOfCards
            + (numberOfCards == 1 ? "card" : "cards"));

        int numSessions = deckStats.getNumberOfSessions();
        totalSessions.setText("Total login sessions: " + numSessions
            + (numSessions == 1 ? " session" : " sessions"));

        String duration = deckStats.getTotalDurationOfSessionsAsString();
        totalDuration.setText("Total login duration: " + duration);
    }
}
