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
import tagline.model.group.Group;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.Note;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.Tag;
import tagline.testutil.contact.TypicalContacts;
import tagline.testutil.group.TypicalGroups;
import tagline.testutil.note.TypicalNotes;
import tagline.testutil.tag.TypicalTags;

/**
 * A stub class for Logic which returns a fixed {@code CommandResult} which is settable.
 */
public class LogicStub implements Logic {
    private Path addressBookFilePath;
    private Path noteBookFilePath;
    private Path groupBookFilePath;
    private Path tagBookFilePath;
    private CommandResult commandResult;
    private String exceptionString = null;

    public LogicStub(Path addressBookFilePath, Path noteBookFilePath, Path groupBookFilePath, Path tagBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
        this.noteBookFilePath = noteBookFilePath;
        this.groupBookFilePath = groupBookFilePath;
        this.tagBookFilePath = tagBookFilePath;
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

    public ReadOnlyGroupBook getGroupBook() {
        return TypicalGroups.getTypicalGroupBook();
    }

    public ObservableList<Group> getFilteredGroupList() {
        return new FilteredList<>(TypicalGroups.getTypicalGroupBook().getGroupList());
    }

    public Path getGroupBookFilePath() { //test folder
        return groupBookFilePath;
    }

    public ReadOnlyTagBook getTagBook() {
        return TypicalTags.getTypicalTagBook();
    }

    public ObservableList<Tag> getFilteredTagList() {
        return new FilteredList<>(TypicalTags.getTypicalTagBook().getTagList());
    }

    public Path getTagBookFilePath() { //test folder
        return groupBookFilePath;
    }

    public GuiSettings getGuiSettings() {
        return new GuiSettings(); //default values
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        return; //do nothing
    }
}
