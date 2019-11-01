package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.ArrayList;

import dream.fcard.logic.stats.DeckStats;
import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.Stats;
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

    /** Creates a new instance of StatisticsWindow. */
    public StatisticsWindow() {
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

        displayStats();
        displaySessionsTableView();
        displayDeckStatsTableView();
    }

    /** Retrieves and displays numerical stats, like the total number of login sessions. */
    private void displayStats() {
        int numSessions = Stats.getNumberOfLoginSessions();
        totalSessions.setText("Total sessions: " + numSessions
            + (numSessions == 1 ? " session" : " sessions"));


        String duration = Stats.getTotalDurationOfSessionsAsString();
        totalDuration.setText("Total duration: " + duration);
    }

    /** Creates the TableView object from the list of login sessions. */
    private void displaySessionsTableView() {
        ArrayList<Session> sessionsList = Stats.getLoginSessionsAsArrayList();
        // temporary debug
        for (Session session : sessionsList) {
            System.out.println("Start: " + session.getSessionStartString());
            System.out.println("End: " + session.getSessionEndString());
            System.out.println("Duration: " + session.getDurationString());
        }

        sessionsTableView.setItems(FXCollections.observableArrayList(sessionsList));
        sessionsTableView.setPlaceholder(new Label("There are no recorded sessions yet!"));

        TableColumn<Session, String> startColumn = new TableColumn<>("Start");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("sessionStartString"));

        TableColumn<Session, String> endColumn = new TableColumn<>("End");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("sessionEndString"));

        TableColumn<Session, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationString"));

        sessionsTableView.getColumns().add(startColumn);
        sessionsTableView.getColumns().add(endColumn);
        sessionsTableView.getColumns().add(durationColumn);
    }

    /** Creates the TableView object showing DeckStats for each Deck. */
    private void displayDeckStatsTableView() {
        // todo
    }
}
