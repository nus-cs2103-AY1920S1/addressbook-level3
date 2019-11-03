package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import dream.fcard.logic.stats.DeckStats;
import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.UserStats;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.util.StatsDisplayUtil;

/**
 * Window to display user's statistics.
 */
public class StatisticsWindow extends VBox {
    @FXML
    private Label totalSessions;
    @FXML
    private Label totalDuration;
    @FXML
    private Label sessionsThisWeek;
    @FXML
    private TableView<Session> sessionsTableView;
    @FXML
    private TableView<Deck> deckTableView;

    private UserStats userStats;

    /** Creates a new instance of StatisticsWindow. */
    public StatisticsWindow(UserStats userStats) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                .getResource("/view/Windows/StatisticsWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            //TODO: replace with logger
            e.printStackTrace();
        }

        this.userStats = userStats;
        //ArrayList<Deck> decks = State.getDecks();

        displaySummaryStats();
        this.sessionsTableView = StatsDisplayUtil.getSessionsTableView(userStats.getSessionList());
        this.deckTableView = StatsDisplayUtil.getDeckTableView(State.getState());
        displayDeckStatsTableView();
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displaySummaryStats() {
        int numSessions = userStats.getNumberOfSessions();
        this.totalSessions.setText("Total login sessions: " + numSessions
            + (numSessions == 1 ? " session" : " sessions"));


        String duration = userStats.getTotalDurationOfSessionsAsString();
        this.totalDuration.setText("Total login duration: " + duration);
    }

    /** Creates the TableView object showing DeckStats for each Deck. */
    private void displayDeckStatsTableView() {
        // todo
    }
}
