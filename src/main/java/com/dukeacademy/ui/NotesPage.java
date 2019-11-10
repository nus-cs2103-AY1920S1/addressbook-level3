package com.dukeacademy.ui;

import com.dukeacademy.data.Pair;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.model.notes.Note;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Controller class for a Ui component representing an entire page dedicated to the display and editing of the user's
 * Notes. It consists of a ListView of all the user's notes, a drawing canvas for the user to make sketches and a
 * TextArea for the user to enter text notes. The NotesPage's behavior and information depends on the containing
 * NotesLogic instance.
 */
public class NotesPage extends UiPart<Region> {
    private static final String FXML = "NotesPage.fxml";

    @FXML
    private StackPane notesListPanelPlaceholder;

    @FXML
    private AnchorPane canvasPlaceholder;

    @FXML
    private TextArea noteTextContent;

    @FXML
    private TextField currentNoteTitle;

    private NotesCanvas notesCanvas;
    private Note selectedNote;

    /**
     * Constructor, the NotesPage will display all the notes represented in the given NotesLogic. The currently-opened
     * Note in the NotesPage will also reflect the currently-selected Note in the given NotesLogic.
     * @param notesLogic the NotesLogic instance
     */
    public NotesPage(NotesLogic notesLogic) {
        super(FXML);

        // Sets this NotesPage as the NoteSubmissionChannel for the given NotesLogic
        notesLogic.setNoteSubmissionChannel(this::getCurrentNoteAndSketch);

        // Initiates the drawing canvas
        notesCanvas = new NotesCanvas();
        canvasPlaceholder.getChildren().add(notesCanvas.getRoot());

        // Instantiates the notes list view to reflect the NotesLogic
        NoteListPanel noteListPanel = new NoteListPanel(notesLogic.getAllNotesList());
        noteListPanel.getRoot().addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
        notesListPanelPlaceholder.getChildren().add(noteListPanel.getRoot());

        // Listens to the currently-selected Note of the NotesLogic to open
        notesLogic.getSelectedNote().addListener(this::loadNoteAndSketch);
    }

    /**
     * Helper method to get and consolidate the current Note-Sketch pair from the User's inputs in the drawing canvas
     * and TextArea.
     * @return the Note-Sketch pair
     */
    private Pair<Note, WritableImage> getCurrentNoteAndSketch() {
        if (selectedNote == null) {
            return null;
        }

        Note currentNote = selectedNote.withNewNoteContents(noteTextContent.getText().strip());
        selectedNote = currentNote;

        return new Pair<>(currentNote, notesCanvas.getImage());
    }

    /**
     * Helper method to load the given Note-Sketch pair into the corresponding Ui components.
     * @param noteAndSketchPair the Note-Sketch pair to be loaded
     */
    private void loadNoteAndSketch(Pair<Note, WritableImage> noteAndSketchPair) {
        // If the given pair is null, empty the ui components and set them to uneditable
        if (noteAndSketchPair == null) {
            clearCurrentNote();
            toggleEditable(false);
            return;
        }


        Note note = noteAndSketchPair.getHead();
        WritableImage sketch = noteAndSketchPair.getTail();

        // Load the text contents of the note
        selectedNote = note;
        currentNoteTitle.setText(note.getTitle());
        noteTextContent.setText(note.getContent());

        // Load the sketch
        if (sketch != null) {
            notesCanvas.clearCanvas();
            notesCanvas.drawImage(sketch);
        } else {
            notesCanvas.clearCanvas();
        }

        // Set the ui components to be editable
        toggleEditable(true);
    }

    /**
     * Helper method to toggle the drawing canvas and note text content's TextArea to editable/uneditable.
     * @param isEditable editable boolean flag
     */
    private void toggleEditable(boolean isEditable) {
        if (!isEditable) {
            noteTextContent.setMouseTransparent(true);
            noteTextContent.setFocusTraversable(false);

            canvasPlaceholder.setMouseTransparent(true);
            canvasPlaceholder.setFocusTraversable(false);
        } else {
            noteTextContent.setMouseTransparent(false);
            noteTextContent.setFocusTraversable(true);

            canvasPlaceholder.setMouseTransparent(false);
            canvasPlaceholder.setFocusTraversable(true);
        }
    }

    /**
     * Helper method to clear the ui components of the previous Note's data.
     */
    private void clearCurrentNote() {
        this.selectedNote = null;
        this.currentNoteTitle.setText(null);
        this.noteTextContent.setText(null);
        this.notesCanvas.clearCanvas();
    }
}
