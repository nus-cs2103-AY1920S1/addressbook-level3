package com.dukeacademy.logic.notes;

import java.util.Optional;

import com.dukeacademy.data.Pair;
import com.dukeacademy.model.notes.Note;
import com.dukeacademy.observable.Observable;

import javafx.collections.ObservableList;
import javafx.scene.image.WritableImage;

/**
 * Interface to encapsulate all CRUD operations related to Notes needed by the application. It also stores a
 * currently-selected Note to represent an opened Note in the application along with its corresponding sketch. An
 * invariant to be maintained by the NotesLogic instance is for all Notes to be identified by an integer id which
 * corresponds to its index in the ObservableList returned by getAllNotesList().
 */
public interface NotesLogic {
    /**
     * Sets a channel which allows the NotesLogic instance to retrieve Notes for saving.
     * @param noteSubmissionChannel the NoteSubmissionChannel to be set
     */
    void setNoteSubmissionChannel(NoteSubmissionChannel noteSubmissionChannel);

    /**
     * Returns a JavaFX ObservableList instance containing all of the user's Notes in the application at any point.
     * @return the representative ObservableList instance.
     */
    ObservableList<Note> getAllNotesList();

    /**
     * Adds a new Note along with its corresponding sketch image to the application.
     * @param note the note to be added
     * @param sketch the note's corresponding sketch image
     */
    void addNoteWithSketch(Note note, WritableImage sketch);

    /**
     * Adds a new Note to the application. This new note is stored with an empty sketch image by default.
     * @param note the note to be added
     */
    void addNote(Note note);

    /**
     * Retrieves a Note from the NoteSubmissionChannel and saves it to the application. SubmissionChannelNotSetException
     * is thrown if a NoteSubmissionChannel was not previously set.
     * @return an Optional instance of the newly saved Note
     */
    Optional<Note> saveNoteFromNoteSubmissionChannel();

    /**
     * Replaces an existing Note instance in the application with a new Note. The new note takes the id of the
     * note that it replaces based on the invariant. The sketch of the old Note is also replaced by the given
     * image. The new image cannot be null.
     * @param oldNote the old Note instance to be replaced
     * @param newNote the new Note instance
     * @param newSketch the new sketch (cannot be null)
     */
    void replaceNote(Note oldNote, Note newNote, WritableImage newSketch);

    /**
     * Returns an Observable instance storing the currently-selected Note of the NotesLogic instance.
     * @return the Observable instance containing the currently-selected Note
     */
    Observable<Pair<Note, WritableImage>> getSelectedNote();

    /**
     * Sets the currently-selected Note in the NotesLogic instance to the Note identified by the given id based on the
     * invariant. A NoteNotFoundException is thrown if there is no Note corresponding to the given id.
     * @param id the id of the new Note to be selected
     * @return the newly selected Note
     */
    Note selectNote(int id);

    /**
     * Sets the currently-selected Note in the NotesLogic instance to the given Note. The given Note must have
     * previously been saved in the application. A NoteNotFoundException is thrown if there is no corresponding Note.
     * @param note the Note instance to be selected
     */
    void selectNote(Note note);

    /**
     * Deletes the Note instance identified by the given id based on the invariant. A NoteNotFoundException is thrown
     * if there is no Note corresponding to the given id.
     * @param id the id of the Note instance to be deleted
     * @return the deleted Note
     */
    Note deleteNote(int id);
}
