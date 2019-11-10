package com.dukeacademy.ui;

import com.dukeacademy.model.notes.Note;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Controller class for a ui component representing a list view of the user's Notes in the application. The component
 * will reflect the Notes found in the containing ObservableList.
 */
public class NoteListPanel extends UiPart<Region> {
    private static final String FXML = "NoteListPanel.fxml";

    @FXML
    private ListView<Note> notesListView;

    public NoteListPanel(ObservableList<Note> notesList) {
        super(FXML);
        notesListView.setItems(notesList);
        notesListView.setCellFactory(listView -> new NoteListCellView());
    }

    /**
     * Private class to represent a cell in the list view.
     */
    private static class NoteListCellView extends ListCell<Note> {
        @Override
        protected void updateItem(Note note, boolean empty) {
            super.updateItem(note, empty);

            if (empty || note == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            setGraphic(new NoteCard(note, this.getIndex() + 1).getRoot());
        }
    }

}
