package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileStatus;
import seedu.address.model.note.MultipleSortByCond;
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

    Predicate<EncryptedFile> PREDICATE_SHOW_ALL_FILES = unused -> true;

    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

    Predicate<Note> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    Predicate<Password> PREDICATE_SHOW_ALL_PASSWORDS = unused -> true;

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

    /**
     * Returns the FileBook
     */
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

    /**
     * Sets the status of the given file to the new status specified.
     */
    void setFileStatus(EncryptedFile target, FileStatus newStatus);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /**
     * Returns an unmodifiable view of the filtered file list
     */
    ObservableList<EncryptedFile> getFilteredFileList();

    /**
     * Updates the filter of the filtered file list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFileList(Predicate<EncryptedFile> predicate);

    /**
     * Returns the user prefs' file book file path.
     */
    Path getFileBookFilePath();

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
    void setCardBook(ReadOnlyCardBook cardBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyCardBook getCardBook();

    /**
     * Returns true if a card with the same identity as {@code card} exists in the card book.
     */
    boolean hasCard(Card card);

    /**
     * Returns true if a card with the same description as {@code card} exists in the card book.
     */
    boolean hasCardDescription(Card card);

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

    /**
     * Returns an unmodifiable view of the filtered card list
     */
    ObservableList<Card> getFilteredCardList();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     *
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

    Predicate<Note> getFilteredNoteListPred();

    Path getNoteBookFilePath();

    void setNoteBookFilePath(Path noteBookFilePath);

    void sortNoteBook();

    void editNoteSortByCond(MultipleSortByCond sortByCond);

    String undoNote();

    String redoNote();

    void commitNoteBook(String command);

    Index getNoteIndex(Note note);

    ObservableList<Password> getFilteredPasswordList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPasswordList(Predicate<Password> predicate);

    void addPassword(Password password);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePassword(Password password);

    /**
     * Returns the PasswordBook
     */
    PasswordBook getPasswordBook();

    /**
     * Returns true if a password with the same identity as {@code password} exists in the password book.
     */
    boolean hasPassword(Password password);

    /**
     * Sets the user prefs' password book file path.
     */
    void setPasswordBookFilePath(Path passwordBookFilePath);

    /**
     * Returns the user prefs' password book file path.
     */
    Path getPasswordBookFilePath();

    /**
     * Replaces the given password {@code target} with {@code editedPassword}.
     * {@code target} must exist in the password book.
     * The password identity of {@code editedPassword} must not be the same as another existing password
     * in the password book.
     */
    void setPassword(Password target, Password editedPassword);
}
