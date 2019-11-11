package tagline.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import tagline.commons.core.GuiSettings;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.Group;
import tagline.model.group.GroupName;
import tagline.model.group.MemberId;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.Tag;

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
     * Returns an unmodifiable copy of the filtered contact list with a set predicate.
     */
    ObservableList<Contact> getFilteredContactListWithPredicate(Predicate<Contact> predicate);

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
     * Finds a {@code Note} in the note book based on the {@code noteId}.
     *
     * @return Optional object if corresponding note is found, empty otherwise
     */
    Optional<Note> findNote(NoteId noteId);

    /**
     * Tags a note.
     */
    void tagNote(NoteId target, Tag tag);

    /**
     * Untags a note.
     */
    void untagNote(NoteId target, Tag tag);

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

    /**
     * Refreshes the filtered note list to force listener updates.
     */
    void refreshFilteredNoteList();

    /**
     * Returns the user prefs' group book file path.
     */
    Path getGroupBookFilePath();

    /**
     * Sets the user prefs' group book file path.
     */
    void setGroupBookFilePath(Path groupBookFilePath);

    /**
     * Replaces group book data with the data in {@code groupBook}.
     */
    void setGroupBook(ReadOnlyGroupBook groupBook);

    /**
     * Returns the GroupBook
     */
    ReadOnlyGroupBook getGroupBook();

    /**
     * Returns true if a Group with the same identity as {@code group} exists in the group book.
     */
    boolean hasGroup(Group group);

    /**
     * Returns a list of groups that contain the given contact id as one of their members.
     */
    public List<Group> findGroupsWithMember(MemberId memberId);

    /**
     * Returns true if a Group with the same name as {@code groupName} exists in the group book.
     */
    boolean hasGroupName(GroupName groupName);

    /**
     * Adds the given group.
     * {@code group} must not already exist in the group book.
     */
    void addGroup(Group group);

    /**
     * Replaces the given group {@code target} with {@code editedGroup}.
     * {@code target} must exist in the group book.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the group book.
     */
    void setGroup(Group target, Group editedGroup);

    /**
     * Deletes the given group.
     * The group must exist in the group book.
     */
    void deleteGroup(Group target);

    /**
     * Returns an unmodifiable view of the filtered group list
     */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Registers the tag in the TagManager if it is not.
     *
     * @return the tag.
     */
    Tag createOrFindTag(Tag tag);

    /**
     * Find and returns optional {@code Tag} from TagManager if exists.
     * Returns empty Optional otherwise.
     */
    Optional<Tag> findTag(Tag tag);

    /**
     * Returns an unmodifiable copy of the filtered group list with a set predicate.
     */
    ObservableList<Group> getFilteredGroupListWithPredicate(Predicate<Group> predicate);

    /**
     * Returns the user prefs' tag book file path.
     */
    Path getTagBookFilePath();

    /**
     * Sets the user prefs' tag book file path.
     */
    void setTagBookFilePath(Path tagBookFilePath);

    /**
     * Replaces tag book data with the data in {@code tagBook}.
     */
    void setTagBook(ReadOnlyTagBook tagBook);

    /**
     * Returns the TagBook
     */
    ReadOnlyTagBook getTagBook();

    /**
     * Returns true if a Tag with the same identity as {@code tag} exists in the tag book.
     */
    boolean hasTag(Tag tag);

    /**
     * Adds the given tag.
     * {@code tag} must not already exist in the tag book.
     */
    void addTag(Tag tag);

    /**
     * Deletes the given tag.
     * The tag must exist in the tag book.
     */
    void deleteTag(Tag target);

    /**
     * Returns an unmodifiable view of the filtered tag list
     */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    /**
     * Returns an unmodifiable copy of the filtered tag list with a set predicate.
     */
    ObservableList<Tag> getFilteredTagListWithPredicate(Predicate<Tag> predicate);
}
