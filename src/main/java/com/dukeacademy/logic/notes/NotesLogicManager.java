package com.dukeacademy.logic.notes;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.data.Pair;
import com.dukeacademy.logic.notes.exceptions.NoteNotFoundException;
import com.dukeacademy.logic.program.exceptions.SubmissionChannelNotSetException;
import com.dukeacademy.model.notes.Note;
import com.dukeacademy.model.notes.NoteBank;
import com.dukeacademy.model.notes.StandardNoteBank;
import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;
import com.dukeacademy.storage.notes.NoteBankStorage;

import javafx.collections.ObservableList;
import javafx.scene.image.WritableImage;

/**
 * The standard implementation of the NotesLogic interface used by the application. This implementation immediately
 * saves any changes to the state of the Notes in the application to storage. It delegates the storage-related
 * operations to the NoteBankStorage class. It delegates specialized CRUD operations related to to the storing of user
 * sketch images to the SketchManager class.
 */
public class NotesLogicManager implements NotesLogic {
    // A default empty sketch image used in the creation and saving of new Notes
    private static final WritableImage DEFAULT_EMPTY_SKETCH = new WritableImage(2000, 2000);

    private final Logger logger;

    private final NoteBankStorage storage;
    private final NoteBank noteBank;
    private final SketchManager sketchManager;
    private final StandardObservable<Pair<Note, WritableImage>> selectedNoteAndSketch;

    private NoteSubmissionChannel noteSubmissionChannel;

    public NotesLogicManager(NoteBankStorage storage) {
        this.logger = LogsCenter.getLogger(NotesLogicManager.class);

        this.storage = storage;
        this.noteBank = this.loadNotesFromStorage();
        this.selectedNoteAndSketch = new StandardObservable<>();

        // Create SketchManager instance
        Path sketchStoragePath = storage.getNoteBankFilePath().getParent().resolve("sketches");
        this.sketchManager = new SketchManager(sketchStoragePath);
    }

    @Override
    public void setNoteSubmissionChannel(NoteSubmissionChannel noteSubmissionChannel) {
        this.noteSubmissionChannel = noteSubmissionChannel;
        logger.info("Successfully added note submission channel : " + noteSubmissionChannel);
    }

    @Override
    public ObservableList<Note> getAllNotesList() {
        return noteBank.getReadOnlyNotesObservableList();
    }

    @Override
    public void addNote(Note note) {
        this.addNoteWithSketch(note, DEFAULT_EMPTY_SKETCH);
    }

    @Override
    public void addNoteWithSketch(Note note, WritableImage sketch) {
        logger.info("Adding new note : " + note);

        // Attempts to save the given image as the sketch for the given note
        try {
            this.sketchManager.saveSketch(note.getSketchId(), sketch);
        } catch (IOException e) {
            logger.warning("Unable to save sketch : " + note.getSketchId());
        }

        // Adds and saves the new note to the NoteBank
        this.noteBank.addNote(note);
        this.saveNotesToStorage(this.noteBank);
    }

    @Override
    public Optional<Note> saveNoteFromNoteSubmissionChannel() {
        if (this.noteSubmissionChannel == null) {
            throw new SubmissionChannelNotSetException();
        }

        Pair<Note, WritableImage> noteAndSketchPair = this.noteSubmissionChannel.getNoteAndSketchPair();

        // Return an empty optional if there is no Note to be saved
        if (noteAndSketchPair == null) {
            logger.info("No current note set, skipping save...");
            return Optional.empty();
        }

        Note note = noteAndSketchPair.getHead();
        WritableImage sketch = noteAndSketchPair.getTail();

        // Note to be saved might already exist in the application
        Optional<Note> oldNote = this.getAllNotesList().stream()
                .filter(note::equals)
                .findFirst();

        // If it already exist, replace the old instance with the new one
        if (oldNote.isPresent()) {
            this.replaceNote(oldNote.get(), note, sketch);
            return Optional.of(note);
        }

        // Else, save the Note as a new Note
        this.addNoteWithSketch(note, sketch);
        logger.info("New note detected, creating new note : " + note);
        return Optional.of(note);

    }

    @Override
    public void replaceNote(Note oldNote, Note newNote, WritableImage newSketch) {
        requireNonNull(newSketch);

        logger.info("Replacing old note : " + oldNote + " with new note : " + newNote);

        // First attempt to save the given image as the new sketch for the Note
        UUID sketchId = newNote.getSketchId();
        try {
            this.sketchManager.saveSketch(sketchId, newSketch);
        } catch (IOException e) {
            logger.warning("Unable to save sketch : " + sketchId);
        }

        // Replace the old Note and save the updates
        this.noteBank.replaceNote(oldNote, newNote);
        this.saveNotesToStorage(this.noteBank);

        // Replace the currently-selected Note-Sketch pair if it was changed
        Pair<Note, WritableImage> newlySelectedNoteSketchPair = new Pair<>(newNote, newSketch);
        this.replaceSelectedNoteIfMatch(newNote, newlySelectedNoteSketchPair);
    }

    @Override
    public Observable<Pair<Note, WritableImage>> getSelectedNote() {
        return this.selectedNoteAndSketch;
    }

    @Override
    public Note selectNote(int id) {
        try {
            Note selectedNote = this.getAllNotesList().get(id);
            this.selectNote(selectedNote);
            return selectedNote;
        } catch (IndexOutOfBoundsException e) {
            throw new NoteNotFoundException();
        }
    }

    @Override
    public void selectNote(Note note) {
        // First check if given Note exists in the application
        Note selectedNote = this.getAllNotesList().stream()
                .filter(note::equals)
                .findFirst()
                .orElseThrow(NoteNotFoundException::new);

        // Attempts to load the sketch corresponding to the note
        WritableImage sketch;
        try {
            sketch = this.sketchManager.loadSketch(selectedNote.getSketchId());
        } catch (IOException e) {
            logger.warning("Unable to load sketch : " + selectedNote.getSketchId());
            sketch = null;
        }

        // Sets the new currently-selected Note-Sketch pair
        this.selectedNoteAndSketch.setValue(new Pair<>(selectedNote, sketch));
    }

    @Override
    public Note deleteNote(int id) {
        // Check if the given id is valid based on the invariant
        if (id < 0 || id >= this.getAllNotesList().size()) {
            throw new NoteNotFoundException();
        }

        // Delete and save changes to storage
        Note selectedNote = this.getAllNotesList().get(id);
        this.noteBank.deleteNote(selectedNote);
        this.saveNotesToStorage(this.noteBank);

        // Remove the currently-selected Note-Sketch pair if it was deleted
        this.replaceSelectedNoteIfMatch(selectedNote, null);

        // Delete the sketch image file corresponding to the deleted note
        this.sketchManager.deleteSketch(selectedNote.getSketchId());

        return selectedNote;
    }

    /**
     * Helper method used to replace the currently-selected Note-Sketch pair if the note is equal to the Note given by
     * the first argument.
     *
     * @param match       the Note to be checked for a match
     * @param replacement the new Note-Sketch pair
     */
    private void replaceSelectedNoteIfMatch(Note match, Pair<Note, WritableImage> replacement) {
        this.selectedNoteAndSketch.getValue()
                .map(Pair::getHead)
                .filter(note -> note.equals(match))
                .ifPresent(note -> this.selectedNoteAndSketch.setValue(replacement));
    }

    /**
     * Helper method to load the application's previously stored NoteBank from storage.
     *
     * @return the previously stored NoteBank instance
     */
    private NoteBank loadNotesFromStorage() {
        logger.info("Storage instance built, trying to load notes...");
        try {
            return this.storage.readNoteBank().get();
        } catch (IOException | DataConversionException | NullPointerException e) {
            logger.info("Unable to load note bank from : " + storage.getNoteBankFilePath()
                    + ".\n Creating new note bank...");
            return new StandardNoteBank();
        }
    }

    /**
     * Helper method to save a NoteBank instance to storage.
     *
     * @param noteBank the NoteBank instance to be saved
     */
    private void saveNotesToStorage(NoteBank noteBank) {
        try {
            storage.saveNoteBank(noteBank);
        } catch (IOException e) {
            logger.warning("Unable to save note bank to :" + storage.getNoteBankFilePath());
        }
    }
}
