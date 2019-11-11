package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import tagline.commons.core.GuiSettings;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactBuilder;
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

public class CreateContactCommandTest {

    @Test
    public void constructor_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateContactCommand(null));
    }

    @Test
    public void execute_contactAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Contact validContact = new ContactBuilder().build();

        CommandResult commandResult = new CreateContactCommand(validContact).execute(modelStub);

        assertEquals(String.format(CreateContactCommand.MESSAGE_SUCCESS, validContact),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validContact), modelStub.contactsAdded);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact validContact = new ContactBuilder().build();
        CreateContactCommand createContactCommand = new CreateContactCommand(validContact);
        ModelStub modelStub = new ModelStubWithContact(validContact);

        assertThrows(CommandException.class,
            CreateContactCommand.MESSAGE_DUPLICATE_CONTACT, () -> createContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Contact alice = new ContactBuilder().withName("Alice").build();
        Contact bob = new ContactBuilder().withName("Bob").build();
        CreateContactCommand addAliceCommand = new CreateContactCommand(alice);
        CreateContactCommand addBobCommand = new CreateContactCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreateContactCommand addAliceCommandCopy = new CreateContactCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different contact -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getNoteBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNoteBookFilePath(Path noteBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getGroupBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroupBookFilePath(Path noteBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNoteBook(ReadOnlyNoteBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyNoteBook getNoteBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroupBook(ReadOnlyGroupBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGroupBook getGroupBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Contact> findContact(ContactId id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactListWithPredicate(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteNote(Note target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNote(Note target, Note editedNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Note> findNote(NoteId noteId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void tagNote(NoteId target, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void untagNote(NoteId target, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Note> getFilteredNoteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredNoteList(Predicate<Note> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void refreshFilteredNoteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Group> findGroupsWithMember(MemberId memberId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroupName(GroupName groupName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroup(Group target, Group editedGroup) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Tag createOrFindTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Tag> findTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupListWithPredicate(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTagBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTagBookFilePath(Path tagBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTagBook(ReadOnlyTagBook tagBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTagBook getTagBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagListWithPredicate(Predicate<Tag> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single contact.
     */
    private class ModelStubWithContact extends ModelStub {
        private final Contact contact;

        ModelStubWithContact(Contact contact) {
            requireNonNull(contact);
            this.contact = contact;
        }

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return this.contact.equals(contact);
        }
    }

    /**
     * A Model stub that always accept the contact being added.
     */
    private class ModelStubAcceptingContactAdded extends ModelStub {
        final ArrayList<Contact> contactsAdded = new ArrayList<>();

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return contactsAdded.stream().anyMatch(contact::equals);
        }

        @Override
        public void addContact(Contact contact) {
            requireNonNull(contact);
            contactsAdded.add(contact);
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
        }

        @Override
        public void updateFilteredNoteList(Predicate<Note> predicate) {
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
