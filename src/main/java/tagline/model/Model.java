package tagline.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.note.Note;
import tagline.model.note.ReadOnlyNoteBook;

/**
 * The API of the Model component.
 */
public interface Model {
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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the address
     * book.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * Finds a contact with given {@code id} if it exists in the address book.
     */
    Optional<Contact> findContact(ContactId id);

    /**
     * Returns an unmodifiable view of the filtered contact list
     */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);

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

    /**
     * Returns the NoteBook
     */
    ReadOnlyNoteBook getNoteBook();

    /**
     * Returns true if a contact with the same identity as {@code note} exists in the note book.
     */
    boolean hasNote(Note note);

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

    /**
     * Deletes the given note.
     * The note must exist in the note book.
     */
    void deleteNote(Note target);

    /**
     * Returns an unmodifiable view of the filtered note list
     */
    ObservableList<Note> getFilteredNoteList();

    /**
     * Updates the filter of the filtered note list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredNoteList(Predicate<Note> predicate);
}
