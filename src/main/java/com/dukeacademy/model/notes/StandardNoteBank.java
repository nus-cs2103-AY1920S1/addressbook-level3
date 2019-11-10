package com.dukeacademy.model.notes;

import java.util.Collection;
import java.util.stream.IntStream;

import com.dukeacademy.logic.notes.exceptions.NoteNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The standard implementation of NoteBank interface used by the application.
 */
public class StandardNoteBank implements NoteBank {
    private final ObservableList<Note> notesList = FXCollections.observableArrayList();
    private final ObservableList<Note> unmodifiableNotesList = FXCollections.unmodifiableObservableList(notesList);

    public StandardNoteBank() {}

    public StandardNoteBank(Collection<Note> notes) {
        this();
        this.notesList.addAll(notes);
    }

    @Override
    public ObservableList<Note> getReadOnlyNotesObservableList() {
        return this.unmodifiableNotesList;
    }

    @Override
    public void addNote(Note note) {
        this.notesList.add(note);
    }

    @Override
    public void replaceNote(Note oldNote, Note newNote) {
        int oldNoteIndex = findNoteIndex(oldNote);
        notesList.remove(oldNoteIndex);
        notesList.add(oldNoteIndex, newNote);
    }

    @Override
    public void deleteNote(Note oldNote) {
        int oldNoteIndex = findNoteIndex(oldNote);
        notesList.remove(oldNoteIndex);
    }

    /**
     * Helper method to scan the list of Notes in the NoteBank for a given Note instance. Returns the index
     * corresponding to that particular Note instance in the list. NoteNotFoundRuntimeException is thrown if
     * the Note is found in the NoteBank.
     * @param note the note instance to be find
     * @return the index of the given note instance
     */
    private int findNoteIndex(Note note) {
        return IntStream.range(0, notesList.size())
                .filter(i -> notesList.get(i).equals(note))
                .findFirst()
                .orElseThrow(NoteNotFoundException::new);
    }
}
