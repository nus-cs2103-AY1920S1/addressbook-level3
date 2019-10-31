package tagline.ui.note;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import tagline.model.note.Note;
import tagline.ui.ResultView;

/**
 * The UI component that displays a note list as a result.
 */
public class NoteListResultView extends ResultView {
    private static final String FXML = "NoteListResultView.fxml";

    private NoteListPanel noteListPanel;

    @FXML
    private StackPane noteListPanelPlaceholder;

    public NoteListResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts(ObservableList<Note> noteList) {
        noteListPanel = new NoteListPanel(noteList);
        noteListPanelPlaceholder.getChildren().add(noteListPanel.getRoot());
    }
}
