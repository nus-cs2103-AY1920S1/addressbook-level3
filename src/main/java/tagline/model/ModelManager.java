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
import tagline.model.group.Group;
import tagline.model.group.GroupBook;
import tagline.model.group.GroupManager;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.Note;
import tagline.model.note.NoteBook;
import tagline.model.note.NoteId;
import tagline.model.note.NoteManager;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.Tag;
import tagline.model.tag.TagBook;
import tagline.model.tag.TagManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ContactManager contactManager;
    private final NoteManager noteManager;
    private final GroupManager groupManager;
    private final TagManager tagManager;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyNoteBook noteBook,
                        ReadOnlyGroupBook groupBook, ReadOnlyTagBook tagBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
            + ", note book: " + noteBook + ", group book: " + groupBook + ", tag book: " + tagBook
            + " and user prefs " + userPrefs);

        contactManager = new ContactManager(addressBook);
        noteManager = new NoteManager(noteBook);
        groupManager = new GroupManager(groupBook);
        tagManager = new TagManager(tagBook);

        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        this(addressBook, new NoteBook(), new GroupBook(), new TagBook(), userPrefs);
    }

    public ModelManager(ReadOnlyNoteBook noteBook, ReadOnlyUserPrefs userPrefs) {
        this(new AddressBook(), noteBook, new GroupBook(), new TagBook(), userPrefs);
    }

    public ModelManager() {
        this(new AddressBook(), new NoteBook(), new GroupBook(), new TagBook(), new UserPrefs());
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
    public void setNoteBookFilePath(Path noteBookFilePath) {
        requireNonNull(noteBookFilePath);
        userPrefs.setNoteBookFilePath(noteBookFilePath);
    }

    @Override
    public Path getNoteBookFilePath() {
        return userPrefs.getNoteBookFilePath();
    }

    @Override
    public void setGroupBookFilePath(Path groupBookFilePath) {
        requireNonNull(groupBookFilePath);
        userPrefs.setGroupBookFilePath(groupBookFilePath);
    }

    @Override
    public Path getGroupBookFilePath() {
        return userPrefs.getGroupBookFilePath();
    }

    @Override
    public void setTagBookFilePath(Path tagBookFilePath) {
        requireNonNull(tagBookFilePath);
        userPrefs.setGroupBookFilePath(tagBookFilePath);
    }

    @Override
    public Path getTagBookFilePath() {
        return userPrefs.getTagBookFilePath();
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
    public ObservableList<Contact> getFilteredContactListWithPredicate(Predicate<Contact> predicate) {
        return contactManager.getFilteredContactListWithPredicate(predicate);
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
    public void addNote(Note note) {
        noteManager.addNote(note);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        noteManager.setNote(target, editedNote);
    }

    @Override
    public void deleteNote(Note target) {
        noteManager.deleteNote(target);
    }

    @Override
    public Optional<Note> findNote(NoteId noteId) {
        return noteManager.findNote(noteId);
    }

    @Override
    public void tagNote(NoteId target, Tag tag) {
        noteManager.tagNote(target, tag);
    }

    @Override
    public void untagNote(NoteId target, Tag tag) {
        noteManager.untagNote(target, tag);
    }

    //=========== Filtered Note List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Note} backed by the internal list of
     * {@code versionedNoteBook}
     */
    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return noteManager.getFilteredNoteList();
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        noteManager.updateFilteredNoteList(predicate);
    }

    //=========== GroupBook ================================================================================

    @Override
    public void setGroupBook(ReadOnlyGroupBook groupBook) {
        groupManager.setGroupBook(groupBook);
    }

    @Override
    public ReadOnlyGroupBook getGroupBook() {
        return groupManager.getGroupBook();
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groupManager.hasGroup(group);
    }

    @Override
    public void addGroup(Group group) {
        groupManager.addGroup(group);
    }

    @Override
    public void setGroup(Group target, Group editedGroup) {
        groupManager.setGroup(target, editedGroup);
    }

    @Override
    public void deleteGroup(Group target) {
        groupManager.deleteGroup(target);
    }

    //=========== Filtered Group List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code versionedGroupBook}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return groupManager.getFilteredGroupList();
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        groupManager.updateFilteredGroupList(predicate);
    }

    @Override
    public ObservableList<Group> getFilteredGroupListWithPredicate(Predicate<Group> predicate) {
        return groupManager.getFilteredGroupListWithPredicate(predicate);
    }

    //=========== TagBook ================================================================================

    @Override
    public void setTagBook(ReadOnlyTagBook tagBook) {
        tagManager.setTagBook(tagBook);
    }

    @Override
    public ReadOnlyTagBook getTagBook() {
        return tagManager.getTagBook();
    }

    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tagManager.hasTag(tag);
    }

    @Override
    public void addTag(Tag tag) {
        tagManager.addTag(tag);
    }

    @Override
    public void deleteTag(Tag target) {
        tagManager.deleteTag(target);
    }

    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code versionedTagBook}
     */
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return tagManager.getFilteredTagList();
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        tagManager.updateFilteredTagList(predicate);
    }

    @Override
    public ObservableList<Tag> getFilteredTagListWithPredicate(Predicate<Tag> predicate) {
        return tagManager.getFilteredTagListWithPredicate(predicate);
    }

    @Override
    public Tag createOrFindTag(Tag tag) {
        if (tagManager.hasTag(tag)) {
            return findTag(tag).get();
        }

        tagManager.addTag(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findTag(Tag tag) {
        return tagManager.findTag(tag);
    }
    //========================================================================================================

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
            && groupManager.equals(other.groupManager)
            && tagManager.equals(other.tagManager)
            && userPrefs.equals(other.userPrefs);
    }
}
