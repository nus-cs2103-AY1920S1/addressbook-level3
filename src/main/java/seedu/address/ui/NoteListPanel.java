package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of lecture notes.
 */
public class NoteListPanel extends UiPart<Region> {
    private static final String FXML = "NoteListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NoteListPanel.class);

    @FXML
    private ListView<Person> noteListView;

    public NoteListPanel(ObservableList<Person> noteList) {
        super(FXML);
        noteListView.setItems(noteList);
        noteListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Note} using a {@code NoteCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person note, boolean empty) {
            super.updateItem(note, empty);

            if (empty || note == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new NoteCard(note, getIndex() + 1).getRoot());
            }
        }
    }
}
