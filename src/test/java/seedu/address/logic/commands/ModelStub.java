package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
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

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
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
    public void editNoteSortByCond(MultipleSortByCond sortByCond) {

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
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPassword(Password password) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePassword(Password password) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public PasswordBook getPasswordBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPassword(Password password) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPasswordBookFilePath(Path passwordBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getPasswordBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPassword(Password target, Password editedPassword) {
        throw new AssertionError("This method should not be called.");
    }
}
