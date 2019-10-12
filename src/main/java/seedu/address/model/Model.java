package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.card.Card;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.note.Note;
import seedu.address.model.password.Password;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

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
     * Replaces file book data with the data in {@code fileBook}.
     */
    void setFileBook(ReadOnlyFileBook fileBook);

    /** Returns the FileBook */
    ReadOnlyFileBook getFileBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
<<<<<<< HEAD
     * Returns true if a file with the same identity as {@code file} exists in the file book.
     */
    boolean hasFile(EncryptedFile file);

    /**
     * Deletes the given file.
     * The file must exist in the address book.
     */
    void deleteFile(EncryptedFile target);

    /**
     * Adds the given file.
     * {@code person} must not already exist in the file book.
     */
    void addFile(EncryptedFile file);

    /**
     * Replaces the given file {@code target} with {@code editedFile}.
     * {@code target} must exist in the file book.
     * The file identity of {@code editedPerson} must not be the same as another existing file in the file book.
     */
    void setFile(EncryptedFile target, EncryptedFile editedFile);

    /** Returns an unmodifiable view of the filtered person list
     *
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /** Returns an unmodifiable view of the filtered file list */
    ObservableList<EncryptedFile> getFilteredFileList();

    /**
     * Updates the filter of the filtered file list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFileList(Predicate<EncryptedFile> predicate);

    /**
     * Returns the user prefs' card book file path.
     */
    Path getCardBookFilePath();

    /**
     * Sets the user prefs' card book file path.
     */
    void setCardBookFilePath(Path cardBookFilePath);

    /**
     * Replaces card book data with the data in {@code cardBook}.
     */
    void setCardBook(CardBook cardBook);

    /** Returns the AddressBook */
    CardBook getCardBook();

    /**
     * Returns true if a card with the same identity as {@code card} exists in the card book.
     */
    boolean hasCard(Card card);

    /**
     * Deletes the given card.
     * The card must exist in the card book.
     */
    void deleteCard(Card target);

    /**
     * Adds the given card.
     * {@code card} must not already exist in the app.
     */
    void addCard(Card card);

    /** Returns an unmodifiable view of the filtered card list */
    ObservableList<Card> getFilteredCardList();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCardList(Predicate<Card> predicate);

    // NOTE

    /**
     * Returns the NoteBook
     */
    ReadOnlyNoteBook getNoteBook();

    /**
     * Replaces note book data with the data in {@code noteBook}.
     */
    void setNoteBook(ReadOnlyNoteBook noteBook);

    /**
     * Returns true if a note with the same identity as {@code note} exists in the note book.
     */
    boolean hasNote(Note note);

    /**
     * Deletes the given note.
     * The note must exist in the Note book.
     */
    void deleteNote(Note target);

    /**
     * Updates the given note.
     * The note must exist in the Note book.
     */

    void addNote(Note note);

    /**
     * Replaces the given note {@code target} with {@code editedNote}.
     * {@code target} must exist in the address book.
     * The note identity of {@code editedNote} must not be the same as another existing note in the note book.
     */
    void setNote(Note target, Note editedNote);

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

    public Path getNoteBookFilePath();

    public void setNoteBookFilePath(Path noteBookFilePath);

    ObservableList<Password> getFilteredPasswordList();

    void addPassword(Password password);

    /** Returns the PasswordBook */
    PasswordBook getPasswordBook();
}
