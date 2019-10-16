package tagline.model.note;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tagline.commons.core.GuiSettings;
import tagline.commons.core.LogsCenter;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.UserPrefs;

/**
 * Represents the in-memory model of the address book data.
 */
public class NoteModelManager implements NoteModel {
    private static final Logger logger = LogsCenter.getLogger(NoteModelManager.class);

    private final NoteBook noteBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Note> filteredNotes;

    /**
     * Initializes a NoteModelManager with the given noteBook and userPrefs.
     */
    public NoteModelManager(ReadOnlyNoteBook noteBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(noteBook, userPrefs);

        logger.fine("Initializing with note book: " + noteBook + " and user prefs " + userPrefs);

        this.noteBook = new NoteBook(noteBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredNotes = new FilteredList<>(this.noteBook.getNoteList());
    }

    public NoteModelManager() {
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
        // TODO change userPrefs
        return null; //userPrefs.getNoteBookFilePath();
    }

    @Override
    public void setNoteBookFilePath(Path noteBookFilePath) {
        requireNonNull(noteBookFilePath);
        // TODO change userPrefs
        //userPrefs.setNoteBookFilePath(noteBookFilePath);
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
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof NoteModelManager)) {
            return false;
        }

        // state check
        NoteModelManager other = (NoteModelManager) obj;
        return noteBook.equals(other.noteBook)
                //&& userPrefs.equals(other.userPrefs)
                && filteredNotes.equals(other.filteredNotes);
    }

}
