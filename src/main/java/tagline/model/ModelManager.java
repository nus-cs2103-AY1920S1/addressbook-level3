package tagline.model;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.commons.core.LogsCenter;
import tagline.model.contact.AddressBook;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.contact.ContactManager;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.note.Note;
import tagline.model.note.NoteBook;
import tagline.model.note.NoteManager;
import tagline.model.note.ReadOnlyNoteBook;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ContactManager contactManager;
    private final NoteManager noteManager;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyNoteBook noteBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        contactManager = new ContactManager(addressBook);
        noteManager = new NoteManager(noteBook, userPrefs);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new AddressBook(), new NoteBook(), new UserPrefs());
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
    public void setNoteBookFilePath(Path noteBookFilePath) {
        requireNonNull(noteBookFilePath);
        userPrefs.setNoteBookFilePath(noteBookFilePath);
    }

    @Override
    public Path getNoteBookFilePath() {
        return userPrefs.getNoteBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        contactManager.setAddressBook(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return contactManager.getAddressBook();
    }

    @Override
    public boolean hasContact(Contact contact) {
        return contactManager.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        contactManager.deleteContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        contactManager.addContact(contact);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        contactManager.setContact(target, editedContact);
    }

    @Override
    public Optional<Contact> findContact(ContactId id) {
        return contactManager.findContact(id);
    }

    //=========== NoteBook ================================================================================

    @Override
    public void setNoteBook(ReadOnlyNoteBook noteBook) {
        noteManager.setNoteBook(noteBook);
    }

    @Override
    public ReadOnlyNoteBook getNoteBook() {
        return noteManager.getNoteBook();
    }

    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return noteManager.hasNote(note);
    }

    @Override
    public void deleteNote(Note target) {
        noteManager.deleteNote(target);
    }

    @Override
    public void addNote(Note note) {
        noteManager.addNote(note);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        noteManager.setNote(target, editedNote);
    }

    //=========== Filtered Contact List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return contactManager.getFilteredContactList();
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        contactManager.updateFilteredContactList(predicate);
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return noteManager.getFilteredNoteList();
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        noteManager.updateFilteredNoteList(predicate);
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
        return contactManager.equals(other.contactManager)
                && noteManager.equals(other.noteManager)
                && userPrefs.equals(other.userPrefs);
    }

}
