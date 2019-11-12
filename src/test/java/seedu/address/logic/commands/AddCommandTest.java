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
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.CardBook;
import seedu.address.model.Model;
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
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
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
        public void commitNoteBook(String command) {

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
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
