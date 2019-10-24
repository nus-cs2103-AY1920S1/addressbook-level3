package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.note.Notes;

import java.util.logging.Logger;

public class NotesListPanel extends UiPart<Region> {
    private static final String FXML = "NotesListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NotesListPanel.class);

    @FXML
    private ListView<Notes> notesListView;

    public NotesListPanel(ObservableList<Notes> notesList) {
        super(FXML);
        notesListView.setItems(notesList);
        notesListView.setCellFactory(listView -> new NotesListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Earnings} using a {@code EarningsCard}.
     */
    class NotesListViewCell extends ListCell<Notes> {
        @Override
        protected void updateItem(Notes notes, boolean empty) {
            super.updateItem(notes, empty);
            if (empty || notes == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new NotesCard(notes, getIndex() + 1).getRoot());
            }
        }
    }
}