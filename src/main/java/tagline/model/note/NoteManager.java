package tagline.model.note;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tagline.commons.core.GuiSettings;
import tagline.commons.core.LogsCenter;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.UserPrefs;
import tagline.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class NoteManager implements NoteModel {
    private static final Logger logger = LogsCenter.getLogger(NoteManager.class);

    private final NoteBook noteBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Note> filteredNotes;

    /**
     * Initializes a NoteManager with the given noteBook and userPrefs.
     */
    public NoteManager(ReadOnlyNoteBook noteBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(noteBook, userPrefs);

        logger.fine("Initializing with note book: " + noteBook + " and user prefs " + userPrefs);

        this.noteBook = new NoteBook(noteBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredNotes = new FilteredList<>(this.noteBook.getNoteList());
    }

    public NoteManager(ReadOnlyNoteBook noteBook) {
        this(noteBook, new UserPrefs());
    }

    public NoteManager(ReadOnlyUserPrefs userPrefs) {
        this(new NoteBook(), userPrefs);
    }

    public NoteManager() {
        this(new NoteBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getNoteBookFilePath() {
        return userPrefs.getNoteBookFilePath();
    }

    @Override
    public void setNoteBookFilePath(Path noteBookFilePath) {
        requireNonNull(noteBookFilePath);
        userPrefs.setNoteBookFilePath(noteBookFilePath);
    }

    //=========== NoteBook ================================================================================

    @Override
    public void setNoteBook(ReadOnlyNoteBook noteBook) {
        this.noteBook.resetData(noteBook);
    }

    @Override
    public ReadOnlyNoteBook getNoteBook() {
        return noteBook;
    }

    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return noteBook.hasNote(note);
    }

    @Override
    public void deleteNote(Note target) {
        noteBook.removeNote(target);
    }

    @Override
    public void addNote(Note note) {
        noteBook.addNote(note);
        updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        noteBook.setNote(target, editedNote);
    }

    @Override
    public Optional<Note> findNote(NoteId noteId) {
        return noteBook.findNote(noteId);
    }

    @Override
    public void tagNote(NoteId target, Tag tag) {
        noteBook.tagNote(target, tag);
    }

    @Override
    public void untagNote(NoteId target, Tag tag) {
        noteBook.untagNote(target, tag);
    }

    @Override
    public void removeTag(Tag tag) {
        noteBook.removeTag(tag);
    }

    //=========== Filtered Note List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Note} backed by the internal list of
     * {@code versionedNoteBook}
     */
    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return filteredNotes;
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
    }

    @Override
    public void refreshFilteredNoteList() {
        Predicate<? super Note> savedPredicate = filteredNotes.getPredicate();
        filteredNotes.setPredicate(PREDICATE_SHOW_NO_NOTES);
        filteredNotes.setPredicate(savedPredicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof NoteManager)) {
            return false;
        }

        // state check
        NoteManager other = (NoteManager) obj;
        return noteBook.equals(other.noteBook)
                //&& userPrefs.equals(other.userPrefs)
                && filteredNotes.equals(other.filteredNotes);
    }

}
