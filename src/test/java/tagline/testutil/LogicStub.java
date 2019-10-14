package tagline.testutil;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tagline.commons.core.GuiSettings;
import tagline.logic.Logic;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.ReadOnlyAddressBook;
import tagline.model.contact.Contact;

/**
 * A stub class for Logic which returns a fixed {@code CommandResult} which is settable.
 */
public class LogicStub implements Logic {
    private Path filePath;
    private CommandResult commandResult;
    private String exceptionString = null;

    public LogicStub(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Sets the command result returned by {@code execute()}.
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
        return filePath;
    }

    public GuiSettings getGuiSettings() {
        return new GuiSettings(); //default values
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        return; //do nothing
    }
}
