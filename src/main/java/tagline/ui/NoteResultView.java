package tagline.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import tagline.model.note.Note;

/**
 * The UI component that displays a note list as a result.
 */
public class NoteResultView extends ResultView {
    private static final String FXML = "NoteResultView.fxml";

    private NoteListPanel noteListPanel;

    @FXML
    private StackPane noteListPanelPlaceholder;

    public NoteResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts(ObservableList<Note> noteList) {
        noteListPanel = new NoteListPanel(noteList);
        noteListPanelPlaceholder.getChildren().add(noteListPanel.getRoot());
    }
}
