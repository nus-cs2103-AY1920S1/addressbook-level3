package tagline.testutil;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tagline.commons.core.GuiSettings;
import tagline.logic.Logic;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.contact.Contact;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.note.Note;
import tagline.model.note.ReadOnlyNoteBook;

/**
 * A stub class for Logic which returns a fixed {@code CommandResult} which is settable.
 */
public class LogicStub implements Logic {
    private Path addressBookFilePath;
    private Path noteBookFilePath;
    private CommandResult commandResult;
    private String exceptionString = null;

    public LogicStub(Path addressBookFilePath, Path noteBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
        this.noteBookFilePath = noteBookFilePath;
    }

    /**
     * Sets the command result returned by {@code execute()}.
     *
     * @param commandResult The CommandResult to return
     */
    public void setCommandResult(CommandResult commandResult) {
        this.commandResult = commandResult;
    }

    /**
     * Sets {@code execute()} to throw a CommandException containing a given string.
     */
    public void setThrowException(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    /**
     * Simulates an execution of the command and returns the set {@code CommandResult}.
     */
    public CommandResult execute(String commandText) throws CommandException {
        if (exceptionString != null) {
            throw new CommandException(exceptionString);
        }

        requireNonNull(commandResult);
        return commandResult;
    }

    public ReadOnlyAddressBook getAddressBook() {
        return TypicalContacts.getTypicalAddressBook();
    }

    public ObservableList<Contact> getFilteredContactList() {
        return new FilteredList<>(TypicalContacts.getTypicalAddressBook().getContactList());
    }

    public Path getAddressBookFilePath() { //test folder
        return addressBookFilePath;
    }

    public ReadOnlyNoteBook getNoteBook() {
        return TypicalNotes.getTypicalNoteBook();
    }

    public ObservableList<Note> getFilteredNoteList() {
        return new FilteredList<>(TypicalNotes.getTypicalNoteBook().getNoteList());
    }

    public Path getNoteBookFilePath() { //test folder
        return noteBookFilePath;
    }

    public GuiSettings getGuiSettings() {
        return new GuiSettings(); //default values
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        return; //do nothing
    }
}
