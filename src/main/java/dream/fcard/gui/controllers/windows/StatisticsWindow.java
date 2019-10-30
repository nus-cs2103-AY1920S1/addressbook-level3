package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.Stats;

/**
 * Window to display user's statistics.
 */
public class StatisticsWindow extends VBox {
    @FXML
    private Label totalSessions;
    @FXML
    private Label sessionsThisWeek;
    @FXML
    private TableView<Session> sessionsTableView;

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
    }

    private void displayStats() {
        // retrieve and display total number of login sessions
        int numSessions = Stats.getNumberOfLoginSessions();
        totalSessions.setText("Total sessions: " + numSessions +
            (numSessions == 1 ? " session" : " sessions"));

        // todo: nullPointerException here... why?
        // retrieve and display list of sessions
        //ArrayList<Session> sessionsList = Stats.getLoginSessions().getSessionArrayList(); // todo: fix
        //sessionsListView.setItems(FXCollections.observableArrayList(sessionsList));
    }

    private void displaySessionsTableView() {
        ArrayList<Session> sessionsList = Stats.getLoginSessions().getSessionArrayList(); // todo: fix
        sessionsTableView.setItems(FXCollections.observableArrayList(sessionsList));
        sessionsTableView.setPlaceholder(new Label("There are no recorded sessions yet!"));

    }
}
