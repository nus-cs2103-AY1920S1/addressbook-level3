package tagline.model.note;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.model.ReadOnlyUserPrefs;
//import tagline.model.note.Note;

/**
 * The API of the NoteModel component.
 */
public interface NoteModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Note> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' note book file path.
     */
    Path getNoteBookFilePath();

    /**
     * Sets the user prefs' note book file path.
     */
    void setNoteBookFilePath(Path noteBookFilePath);

    /**
     * Replaces note book data with the data in {@code noteBook}.
     */
    void setNoteBook(ReadOnlyNoteBook noteBook);

    /** Returns the NoteBook */
    ReadOnlyNoteBook getNoteBook();

    /**
     * Returns true if a note with the same identity as {@code note} exists in the note book.
     */
    boolean hasNote(Note note);

    /**
     * Deletes the given note.
     * The note must exist in the note book.
     */
    void deleteNote(Note target);

    /**
     * Adds the given note.
     * {@code note} must not already exist in the note book.
     */
    void addNote(Note note);

    /**
     * Replaces the given note {@code target} with {@code editedNote}.
     * {@code target} must exist in the note book.
     * The note identity of {@code editedNote} must not be the same as another existing note in the note book.
     */
    void setNote(Note target, Note editedNote);

    /** Returns an unmodifiable view of the filtered note list */
    ObservableList<Note> getFilteredNoteList();

    /**
     * Updates the filter of the filtered note list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredNoteList(Predicate<Note> predicate);
}
