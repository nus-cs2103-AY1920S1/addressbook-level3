package dream.fcard.util;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import dream.fcard.logic.stats.DeckStats;
import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.model.Deck;
import dream.fcard.model.State;

public class StatsDisplayUtil {

    /** Creates the TableView object from the given list of sessions. */
    public static TableView<Session> getSessionsTableView(SessionList sessionList) {
        ArrayList<Session> sessionArrayList = sessionList.getSessionArrayList();
        TableView<Session> sessionsTableView = new TableView<>();

        // temporary debug
        //for (Session session : sessionsList) {
        //    System.out.println("Start: " + session.getSessionStartString());
        //    System.out.println("End: " + session.getSessionEndString());
        //    System.out.println("Duration: " + session.getDurationString());
        //}

        sessionsTableView.setItems(FXCollections.observableArrayList(sessionArrayList));
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

        return sessionsTableView;
    }

    /** Creates the TableView object from the given State object, representing the list of decks. */
    public static TableView<DeckStats> getDeckStatsTableView(State state) {
        // for each deck in list of decks in state, get the DeckStats object
        ArrayList<DeckStats> deckStatsArrayList = new ArrayList<>();
        for (Deck deck : state.decks) {
            deckStatsArrayList.add(deck.getDeckStats());
        }
        TableView<DeckStats> deckStatsTableView = new TableView<>();
        deckStatsTableView.setItems(FXCollections.observableArrayList(deckStatsArrayList));
        deckStatsTableView.setPlaceholder(new Label("There are no decks!"));

        TableColumn<DeckStats, String> nameColumn = new TableColumn<>("Deck name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DeckStats, Integer> numCardsColumn = new TableColumn<>("No. of cards");
        numCardsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

        TableColumn<DeckStats, Integer> numSessionsColumn = new TableColumn<>("No. of sessions");
        numSessionsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSessions"));

        TableColumn<DeckStats, Double> avgScoreColumn = new TableColumn<>("Average score");
        avgScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));

        // todo: currently do not know which cards were tested in each exam
        // todo: on click, show the deck stats for the selected deck
        //TableColumn<DeckStats, String> startColumn = new TableColumn<>("Start");
        //startColumn.setCellValueFactory(new PropertyValueFactory<>("sessionStartString"));
        //
        //TableColumn<DeckStats, String> endColumn = new TableColumn<>("End");
        //endColumn.setCellValueFactory(new PropertyValueFactory<>("sessionEndString"));
        //
        //TableColumn<DeckStats, String> durationColumn = new TableColumn<>("Duration");
        //durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationString"));

        deckStatsTableView.getColumns().add(nameColumn);
        deckStatsTableView.getColumns().add(numCardsColumn);
        deckStatsTableView.getColumns().add(numSessionsColumn);
        deckStatsTableView.getColumns().add(avgScoreColumn);

        return deckStatsTableView;
    }
}
