package com.dukeacademy.testutil;

import java.util.Optional;

import com.dukeacademy.data.Pair;
import com.dukeacademy.logic.notes.NoteSubmissionChannel;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.model.notes.Note;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.WritableImage;

/**
 * Mock implementation of NotesLogic interface for testing purposes. The only 2 supported operations are addNote(Note)
 * and saveNoteFromNoteSubmissionChannel(). saveNoteFromNoteSubmissionChannel will replace the current note,
 * BEFORE_SAVE_NOTE with the AFTER_SAVE_NOTE.
 */
public class MockNotesLogic implements NotesLogic {
    public static final Note BEFORE_SAVE_NOTE = new Note("test", "before save");
    public static final Note AFTER_SAVE_NOTE = BEFORE_SAVE_NOTE.withNewNoteContents("after save");

    private ObservableList<Note> notes;
    private StandardObservable<Pair<Note, WritableImage>> currentNoteObservable;

    public MockNotesLogic() {
        this.notes = FXCollections.observableArrayList();
        this.notes.add(BEFORE_SAVE_NOTE);
        this.currentNoteObservable = new StandardObservable<>();
        this.currentNoteObservable.setValue(new Pair<>(BEFORE_SAVE_NOTE, null));
    }

    @Override
    public void setNoteSubmissionChannel(NoteSubmissionChannel noteSubmissionChannel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ObservableList<Note> getAllNotesList() {
        return notes;
    }

    @Override
    public void addNoteWithSketch(Note note, WritableImage sketch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addNote(Note note) {
        this.notes.add(note);
    }

    @Override
    public Optional<Note> saveNoteFromNoteSubmissionChannel() {
        this.notes.remove(BEFORE_SAVE_NOTE);
        this.notes.add(AFTER_SAVE_NOTE);
        this.currentNoteObservable.setValue(new Pair<>(AFTER_SAVE_NOTE, null));
        return Optional.of(AFTER_SAVE_NOTE);
    }

    @Override
    public void replaceNote(Note oldNote, Note newNote, WritableImage newSketch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Pair<Note, WritableImage>> getSelectedNote() {
        return this.currentNoteObservable;
    }

    @Override
    public Note selectNote(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void selectNote(Note note) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Note deleteNote(int id) {
        throw new UnsupportedOperationException();
    }
}
