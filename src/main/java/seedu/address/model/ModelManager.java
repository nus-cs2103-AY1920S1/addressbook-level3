package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.note.Note;
import seedu.address.model.password.Password;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FileBook fileBook;
    private final CardBook cardBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<EncryptedFile> filteredFiles;
    private final FilteredList<Card> filteredCards;
    private final NoteBook noteBook;
    private final FilteredList<Note> filteredNotes;
    private final PasswordBook passwordBook;
    private final FilteredList<Password> filteredPasswords;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyFileBook fileBook,
                        CardBook cardBook, ReadOnlyNoteBook noteBook, PasswordBook passwordBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, fileBook, cardBook, passwordBook, userPrefs);
        logger.fine("Initializing with address book: " + addressBook
                + " and file book: " + fileBook
                + " and card book: " + cardBook
                + " and note book: " + noteBook
                + " and password book: " + passwordBook
                + " and user prefs: " + userPrefs);
        this.addressBook = new AddressBook(addressBook);
        this.fileBook = new FileBook(fileBook);
        this.cardBook = new CardBook(cardBook);
        this.noteBook = new NoteBook(noteBook);
        this.passwordBook = new PasswordBook(passwordBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredFiles = new FilteredList<>(this.fileBook.getFileList());
        filteredCards = new FilteredList<>(this.cardBook.getCardList());
        filteredNotes = new FilteredList<>(this.noteBook.getNoteList());
        filteredPasswords = new FilteredList<>(this.passwordBook.getPasswordList());

    }

    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        this(addressBook, new FileBook(), new CardBook(), new NoteBook(), new PasswordBook(), userPrefs);
    }


    public ModelManager() {
        this(new AddressBook(), new FileBook(), new CardBook(), new NoteBook(), new PasswordBook(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getNoteBookFilePath() {
        return userPrefs.getNoteBookFilePath();
    }

    @Override
    public void setNoteBookFilePath(Path noteBookFilePath) {
        requireNonNull(noteBookFilePath);
        userPrefs.setAddressBookFilePath(noteBookFilePath);
    }

    @Override
    public Path getCardBookFilePath() {
        return userPrefs.getCardBookFilePath();
    }

    @Override
    public void setCardBookFilePath(Path cardBookFilePath) {
        requireNonNull(cardBookFilePath);
        userPrefs.setCardBookFilePath(cardBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== FileBook ===================================================================================

    @Override
    public void setFileBook(ReadOnlyFileBook fileBook) {
        this.fileBook.resetData(fileBook);
    }

    @Override
    public ReadOnlyFileBook getFileBook() {
        return fileBook;
    }

    @Override
    public boolean hasFile(EncryptedFile file) {
        requireNonNull(file);
        return fileBook.hasFile(file);
    }

    @Override
    public void deleteFile(EncryptedFile target) {
        fileBook.removeFile(target);
    }

    @Override
    public void addFile(EncryptedFile file) {
        fileBook.addFile(file);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setFile(EncryptedFile target, EncryptedFile editedFile) {
        requireAllNonNull(target, editedFile);

        fileBook.setFile(target, editedFile);
    }

    //=========== Card =====================================================================================

    @Override
    public void setCardBook(CardBook cardBook) {
        this.cardBook.resetData(cardBook);
    }

    @Override
    public CardBook getCardBook() {
        return cardBook;
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cardBook.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        cardBook.removeCard(target);
    }

    @Override
    public void addCard(Card card) {
        cardBook.addCard(card);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered File List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code EncryptedFile} backed by the internal list of
     * {@code versionedFileBook}
     */
    @Override
    public ObservableList<EncryptedFile> getFilteredFileList() {
        return filteredFiles;
    }

    @Override
    public void updateFilteredFileList(Predicate<EncryptedFile> predicate) {
        requireNonNull(predicate);
        filteredFiles.setPredicate(predicate);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedCardBook}
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        return filteredCards;
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredCards.setPredicate(predicate);
    }

    @Override
    public ObservableList<Password> getFilteredPasswordList() {
        return filteredPasswords;
    }

    //=========== PasswordBook List Accessors =============================================================

    @Override
    public void addPassword(Password password) {
        passwordBook.addPassword(password);
    }

    @Override
    public void deletePassword(Password password) {
        passwordBook.removePassword(password);
    }

    @Override
    public PasswordBook getPasswordBook() {
        return passwordBook;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredFiles.equals(other.filteredFiles)
                && filteredCards.equals(other.filteredCards);
    }

    //=========== Notes =============================================================

    @Override
    public ReadOnlyNoteBook getNoteBook() {
        return noteBook;
    }

    @Override
    public void setNoteBook(ReadOnlyNoteBook noteBook) {
        this.noteBook.resetData(noteBook);
    }

    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return noteBook.hasNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        noteBook.removeNote(note);
    }

    @Override
    public void addNote(Note note) {
        noteBook.addNote(note);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);
        noteBook.setNote(target, editedNote);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
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


}
