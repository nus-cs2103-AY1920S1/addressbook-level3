package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_STRING_COMMAND_ARG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNotes.SECRETDIARY;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.CardBook;
import seedu.address.model.Model;
import seedu.address.model.NoteBook;
import seedu.address.model.PasswordBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCardBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileStatus;
import seedu.address.model.note.MultipleSortByCond;
import seedu.address.model.note.Note;
import seedu.address.model.password.Password;
import seedu.address.model.person.Person;
import seedu.address.testutil.NoteBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddNotesCommandTest {

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNotesCommand(null, VALID_STRING_COMMAND_ARG));
        assertThrows(NullPointerException.class, () -> new AddNotesCommand(SECRETDIARY, null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Note validNote = new NoteBuilder().build();

        CommandResult commandResult = new AddNotesCommand(validNote,VALID_STRING_COMMAND_ARG).execute(modelStub);

        assertEquals(String.format(AddNotesCommand.MESSAGE_SUCCESS, validNote), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note validNote = new NoteBuilder().build();
        AddNotesCommand addNotesCommand = new AddNotesCommand(validNote,VALID_STRING_COMMAND_ARG);
        ModelStub modelStub = new ModelStubWithNote(validNote);

        assertThrows(CommandException.class, AddNotesCommand.MESSAGE_DUPLICATE_NOTE,
                () -> addNotesCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Note noteOne = new NoteBuilder().withTitle("noteOne").build();
        Note noteTwo = new NoteBuilder().withTitle("noteTwo").build();
        AddNotesCommand addNoteOneCommand = new AddNotesCommand(noteOne, VALID_STRING_COMMAND_ARG);
        AddNotesCommand addNoteTwoCommand = new AddNotesCommand(noteTwo, VALID_STRING_COMMAND_ARG);

        // same object -> returns true
        assertTrue(addNoteOneCommand.equals(addNoteOneCommand));

        // same values -> returns true
        AddNotesCommand addNoteOneCommandCopy = new AddNotesCommand(noteOne, VALID_STRING_COMMAND_ARG);
        assertTrue(addNoteOneCommand.equals(addNoteOneCommandCopy));

        // different types -> returns false
        assertFalse(addNoteOneCommand.equals(1));

        // null -> returns false
        assertFalse(addNoteOneCommand.equals(null));

        // different person -> returns false
        assertFalse(addNoteOneCommand.equals(addNoteTwoCommand));
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
        public void addFile(EncryptedFile file) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFileBook(ReadOnlyFileBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFileBook getFileBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFile(EncryptedFile file) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFile(EncryptedFile target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFile(EncryptedFile target, EncryptedFile editedFile) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFileStatus(EncryptedFile target, FileStatus status) {
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
        public ObservableList<EncryptedFile> getFilteredFileList() {
            throw new AssertionError("This method should not be called.");
        }

        public Path getCardBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFileList(Predicate<EncryptedFile> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFileBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCardBookFilePath(Path cardBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCardBook(ReadOnlyCardBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CardBook getCardBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCardDescription(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCardList(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyNoteBook getNoteBook() {
            return null;
        }

        @Override
        public void setNoteBook(ReadOnlyNoteBook noteBook) {

        }

        @Override
        public boolean hasNote(Note note) {
            return false;
        }

        @Override
        public void deleteNote(Note target) {

        }

        @Override
        public void addNote(Note note) {

        }

        @Override
        public void setNote(Note target, Note editedNote) {

        }

        @Override
        public ObservableList<Note> getFilteredNoteList() {
            return null;
        }

        @Override
        public void sortNoteBook() {

        }

        @Override
        public void editNoteSortByCond(MultipleSortByCond sortByConds) {

        }

        @Override
        public void updateFilteredNoteList(Predicate<Note> predicate) {

        }

        @Override
        public Predicate<Note> getFilteredNoteListPred() {
            return null;
        }

        @Override
        public Path getNoteBookFilePath() {
            return null;
        }

        @Override
        public void setNoteBookFilePath(Path noteBookFilePath) {

        }

        @Override
        public void commitNote(String command) {

        }

        @Override
        public String undoNote() {
            return null;
        }

        @Override
        public String redoNote() {
            return null;
        }

        @Override
        public Index getNoteIndex(Note note) {
            return null;
        }

        @Override
        public ObservableList<Password> getFilteredPasswordList() {
            return null;
        }

        @Override
        public void updateFilteredPasswordList(Predicate<Password> predicate) {

        }

        @Override
        public void addPassword(Password password) {

        }

        @Override
        public void deletePassword(Password password) {

        }

        @Override
        public PasswordBook getPasswordBook() {
            return null;
        }

        @Override
        public boolean hasPassword(Password password) {
            return false;
        }

        @Override
        public void setPasswordBookFilePath(Path passwordBookFilePath) {

        }

        @Override
        public Path getPasswordBookFilePath() {
            return null;
        }

        @Override
        public void setPassword(Password target, Password editedPassword) {

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
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingNoteAdded extends ModelStub {
        final ArrayList<Note> notesAdded = new ArrayList<>();

        @Override
        public boolean hasNote (Note note) {
            requireNonNull(note);
            return notesAdded.stream().anyMatch(note::isSameNote);
        }

        @Override
        public void addNote(Note note) {
            requireNonNull(note);
            notesAdded.add(note);
        }

        @Override
        public ReadOnlyNoteBook getNoteBook() {
            return new NoteBook();
        }
    }

}
