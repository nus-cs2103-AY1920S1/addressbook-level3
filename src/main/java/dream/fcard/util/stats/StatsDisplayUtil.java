//@@author nattanyz
package dream.fcard.util.stats;

import java.util.ArrayList;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.stats.UserStatsHolder;
import dream.fcard.model.Deck;
import dream.fcard.model.StateHolder;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/** Utilities related to displaying statistics in the GUI. */
public class StatsDisplayUtil {

    /** Creates the TableView object from the given list of sessions. */
    public static TableView<Session> getSessionsTableView() {
        SessionList sessionList = UserStatsHolder.getUserStats().getSessionList();
        ArrayList<Session> sessionsArrayList = sessionList.getSessionArrayList();
        TableView<Session> sessionsTableView = new TableView<>();

        // temporary debug
        for (Session session : sessionsArrayList) {
            System.out.println("Start: " + session.getSessionStartString());
            System.out.println("End: " + session.getSessionEndString());
            System.out.println("Duration: " + session.getDurationString());
        }

        sessionsTableView.setItems(FXCollections.observableArrayList(sessionsArrayList));

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

    /** Creates the TableView object representing the list of decks. */
    public static TableView<Deck> getDeckTableView() {
        // for each deck in list of decks in state, get the DeckStats object
        ArrayList<Deck> decks = StateHolder.getState().getDecks();

        TableView<Deck> deckTableView = new TableView<>();
        deckTableView.setItems(FXCollections.observableArrayList(decks));
        deckTableView.setPlaceholder(new Label("There are no decks!"));

        TableColumn<Deck, String> nameColumn = new TableColumn<>("Deck name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("deckName"));

        TableColumn<Deck, Integer> numCardsColumn = new TableColumn<>("No. of cards");
        numCardsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

        TableColumn<Deck, Integer> numSessionsColumn = new TableColumn<>("No. of sessions");
        numSessionsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSessions"));

        //TableColumn<Deck, Double> avgScoreColumn = new TableColumn<>("Average score");
        //avgScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));

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

        deckTableView.getColumns().add(nameColumn);
        deckTableView.getColumns().add(numCardsColumn);
        deckTableView.getColumns().add(numSessionsColumn);
        //deckTableView.getColumns().add(avgScoreColumn);

        return deckTableView;
    }
}
