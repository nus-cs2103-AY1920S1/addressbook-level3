package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.logging.Logger;

import dream.fcard.core.commons.core.LogsCenter;
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
    private Label totalDuration;
    @FXML
    private Label averageDuration;
    @FXML
    private ScrollPane sessionsScrollPane;
    @FXML
    private TableView<Session> sessionsTableView;
    @FXML
    private ScrollPane deckTableScrollPane;
    @FXML
    private TableView<Deck> deckTableView;

    private UserStats userStats;

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
        displaySummaryStats();

        this.sessionsTableView = StatsDisplayUtil.getUserSessionsTableView();
        this.sessionsScrollPane.setContent(sessionsTableView);

        this.deckTableView = StatsDisplayUtil.getDeckTableView();
        this.deckTableScrollPane.setContent(deckTableView);
        allowDeckStatisticsWindowToBeOpened();
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displaySummaryStats() {
        int numSessions = userStats.getSessionList().getNumberOfSessions();
        this.totalSessions.setText(String.valueOf(numSessions));
        this.totalSessionsExplainer.setText("login"
            + (numSessions == 1 ? " session" : " sessions") + " all time");

        SessionList sublistForThisWeek = SessionListUtil.getSublistForThisWeek(
            userStats.getSessionList());
        int numSessionsThisWeek = sublistForThisWeek.getNumberOfSessions();
        this.sessionsThisWeek.setText(String.valueOf(numSessionsThisWeek));
        this.sessionsThisWeekExplainer.setText("login"
            + (numSessionsThisWeek == 1 ? " session" : " sessions") + " this week");

        String duration = userStats.getSessionList().getTotalDurationAsString();
        this.totalDuration.setText("Total login duration: " + duration);

        String averageDuration = userStats.getSessionList().getAverageDurationAsString();
        this.averageDuration.setText("Average duration per login: " + averageDuration);
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
