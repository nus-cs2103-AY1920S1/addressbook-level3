package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.ArrayList;

import dream.fcard.logic.stats.DeckStats;
import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.UserStats;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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
    private TableView<DeckStats> deckStatsTableView;

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
        displayStats();
        sessionsTableView = userStats.getSessionsTableView();
        displayDeckStatsTableView();
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displayStats() {
        int numSessions = userStats.getNumberOfSessions();
        totalSessions.setText("Total sessions: " + numSessions
            + (numSessions == 1 ? " session" : " sessions"));


        String duration = userStats.getTotalDurationOfSessionsAsString();
        totalDuration.setText("Total duration: " + duration);
    }

    /** Creates the TableView object showing DeckStats for each Deck. */
    private void displayDeckStatsTableView() {
        // todo
    }
}
