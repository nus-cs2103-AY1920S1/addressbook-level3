package tagline.ui.note;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tagline.commons.core.LogsCenter;
import tagline.model.note.Note;
import tagline.ui.UiPart;

/**
 * Panel containing the list of notes.
 */
public class NoteListPanel extends UiPart<Region> {
    private static final String FXML = "NoteListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NoteListPanel.class);

    @FXML
    private ListView<Note> noteListView;

    public NoteListPanel(ObservableList<Note> noteList) {
        super(FXML);
        noteListView.setItems(noteList);
        noteListView.setCellFactory(listView -> new NoteListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Note} using a {@code NoteListCard}.
     */
    class NoteListViewCell extends ListCell<Note> {
        @Override
        protected void updateItem(Note note, boolean empty) {
            super.updateItem(note, empty);

            if (empty || note == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new NoteListCard(note).getRoot());
            }
        }
    }
}
