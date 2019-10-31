// @@author shiweing
package tagline.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.Group;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.model.note.NoteModel;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.Tag;
import tagline.testutil.NoteBuilder;

class CreateNoteCommandTest {

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateNoteCommand(null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Note validNote = new NoteBuilder().build();

        CommandResult commandResult = new CreateNoteCommand(validNote).execute(modelStub);

        assertEquals(String.format(CreateNoteCommand.MESSAGE_SUCCESS, validNote),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.noteAdded);
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note validNote = new NoteBuilder().build();
        CreateNoteCommand createNoteCommand = new CreateNoteCommand(validNote);
        ModelStub modelStub = new ModelStubWithNote(validNote);

        assertThrows(CommandException.class,
                CreateNoteCommand.MESSAGE_DUPLICATE_NOTE, () -> createNoteCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Note note41 = new NoteBuilder().withNoteId(41).build();
        Note note42 = new NoteBuilder().withNoteId(42).build();
        CreateNoteCommand createNote41Command = new CreateNoteCommand(note41);
        CreateNoteCommand createNote42Command = new CreateNoteCommand(note42);

        // same object -> returns true
        assertTrue(createNote41Command.equals(createNote41Command));

        // same values -> returns true
        CreateNoteCommand createNote41CommandCopy = new CreateNoteCommand(note41);
        assertTrue(createNote41Command.equals(createNote41CommandCopy));

        // different types -> returns false
        assertFalse(createNote41Command.equals(1));

        // null -> returns false
        assertFalse(createNote41Command.equals(null));

        // different note -> returns false
        assertFalse(createNote41Command.equals(createNote42Command));
    }

    /**
     * A default note model stub that have all of the methods failing.
     */
    private class NoteModelStub implements NoteModel {

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
        public Path getNoteBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNoteBookFilePath(Path noteBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNoteBook(ReadOnlyNoteBook noteBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyNoteBook getNoteBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteNote(Note target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addNote(Note note) {
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
        public void tagNote(Note target, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void untagNote(Note target, Tag tag) {
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
    }

    /**
     * A NoteModel stub that contains a single note.
     */
    private class NoteModelStubWithNote extends NoteModelStub {
        private final Note note;

        NoteModelStubWithNote(Note note) {
            requireNonNull(note);
            this.note = note;
        }

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return this.note.isSameNote(note);
        }
    }

    /**
     * A Model stub that always accept the contact being added.
     */
    private class NoteModelStubAcceptingNoteAdded extends NoteModelStub {
        final ArrayList<Note> noteAdded = new ArrayList<>();

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return this.noteAdded.stream().anyMatch(note::isSameNote);
        }

        @Override
        public void addNote(Note note) {
            requireNonNull(note);
            noteAdded.add(note);
        }
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
        public void setGroupBookFilePath(Path groupBookFilePath) {
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
        public void tagNote(Note note, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void untagNote(Note note, Tag tag) {
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
        public boolean hasGroup(Group note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(Group note) {
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
        public ObservableList<Group> getFilteredGroupListWithPredicate(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains note model which contains a single note.
     */
    private class ModelStubWithNote extends ModelStub {
        final NoteModelStubWithNote noteModel;

        ModelStubWithNote(Note note) {
            noteModel = new NoteModelStubWithNote(note);
        }

        @Override
        public boolean hasNote(Note note) {
            return noteModel.hasNote(note);
        }
    }

    /**
     * A Model stub that always accept the note being added.
     */
    private class ModelStubAcceptingContactAdded extends ModelStub {
        final NoteModelStubAcceptingNoteAdded noteModel = new NoteModelStubAcceptingNoteAdded();
        final ArrayList<Note> noteAdded = noteModel.noteAdded;

        @Override
        public boolean hasNote(Note note) {
            return noteModel.hasNote(note);
        }

        @Override
        public void addNote(Note note) {
            noteModel.addNote(note);
        }

        @Override
        public void updateFilteredNoteList(Predicate<Note> predicate) {
        }
    }
}
