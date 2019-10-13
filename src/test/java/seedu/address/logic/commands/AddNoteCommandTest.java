package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.NoteBuilder;
public class AddNoteCommandTest {

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNoteCommand(null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Note validNote = new NoteBuilder().build();
        
        CommandResult commandResult = new AddNoteCommand(validNote).execute(modelStub);
        
        assertEquals(String.format(AddNoteCommand.MESSAGE_SUCCESS, validNote), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }
    
    @Test
    public void constructor_noteTitleOfOnlyWhiteSpace_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withTitle("").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withTitle(" ").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withTitle("  ").build());
    }
    
    @Test
    public void constructor_noteContentOfOnlyWhiteSpace_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withContent("").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withContent(" ").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withContent("  ").build());
    }
    
    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note validNote = new NoteBuilder().build();
        AddNoteCommand addNoteCommand = new AddNoteCommand(validNote);
        ModelStub modelStub = new ModelStubWithNote(validNote);

        assertThrows(CommandException.class, AddNoteCommand.MESSAGE_DUPLICATE_NOTE,
                () -> addNoteCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Note sucks = new NoteBuilder().withTitle("Pipelining sucks").build();
        Note rocks = new NoteBuilder().withTitle("Pipelining rocks").build();
        AddNoteCommand addSucksCommand = new AddNoteCommand(sucks);
        AddNoteCommand addRocksCommand = new AddNoteCommand(rocks);

        // same object -> returns true
        assertTrue(addSucksCommand.equals(addSucksCommand));

        // same values -> returns true
        AddNoteCommand addSucksCommandCopy = new AddNoteCommand(sucks);
        assertTrue(addSucksCommand.equals(addSucksCommandCopy));

        // different types -> returns false
        assertFalse(addSucksCommand.equals(1));

        // null -> returns false
        assertFalse(addSucksCommand.equals(null));

        // different note -> returns false
        assertFalse(addSucksCommand.equals(addRocksCommand));
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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
    
    
        @Override
        public void addNote(Note note) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
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
        public void setNote(Note target, Note editedNote) {
            throw new AssertionError("This method should not be called.");
        }
        
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
     * A Model stub that contains a single person.
     */
    private class ModelStubWithNote extends ModelStub {
        private final Note note;

        ModelStubWithNote(Note note) {
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
     * A Model stub that always accept the note being added.
     */
    private class ModelStubAcceptingNoteAdded extends ModelStub {
        final ArrayList<Note> notesAdded = new ArrayList<>();

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return notesAdded.stream().anyMatch(note::isSameNote);
        }

        @Override
        public void addNote(Note note) {
            requireNonNull(note);
            notesAdded.add(note);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
