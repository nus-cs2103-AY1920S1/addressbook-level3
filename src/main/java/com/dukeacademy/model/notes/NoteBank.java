package com.dukeacademy.model.notes;

import javafx.collections.ObservableList;

/**
 * Interface to encapsulate the storage of all the notes created by the user. It provides basic CRUD operations for
 * notes, allowing notes to be read and monitored using a JavaFx ObservableList, added, deleted and replaced.
 */
public interface NoteBank {
    /**
     * Returns a JavaFX ObservableList instance that contains all of the notes in the note bank instance at any time.
     * @return the representative JavaFX ObservableList
     */
    ObservableList<Note> getReadOnlyNotesObservableList();

    /**
     * Adds a new Note instance to the NoteBank.
     * @param note the Note instance to be added
     */
    void addNote(Note note);

    /**
     * Replaces an existing Note instance in the NoteBank with a new Note.
     * @param oldNote the old Note to be replaced
     * @param newNote the new Note to be added
     */
    void replaceNote(Note oldNote, Note newNote);

    /**
     * Deletes an existing Note instance in the NoteBank.
     * @param oldNote the old Note instance to be deleted.
     */
    void deleteNote(Note oldNote);
}
