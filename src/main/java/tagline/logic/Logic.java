package tagline.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.contact.Contact;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.Group;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.Note;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.Tag;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the address book.
     *
     * @see tagline.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of contacts
     */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the note book.
     *
     * @see tagline.model.Model#getAddressBook()
     */
    ReadOnlyNoteBook getNoteBook();

    /** Returns an unmodifiable view of the filtered list of notes */
    ObservableList<Note> getFilteredNoteList();

    /**
     * Returns the user prefs' note book file path.
     */
    Path getNoteBookFilePath();

    /**
     * Returns the note book.
     *
     * @see tagline.model.Model#getGroupBook()
     */
    ReadOnlyGroupBook getGroupBook();

    /** Returns an unmodifiable view of the filtered list of groups */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Returns the user prefs' tag book file path.
     */
    Path getTagBookFilePath();

    /**
     * Returns the tag book.
     *
     * @see tagline.model.Model#getTagBook()
     */
    ReadOnlyTagBook getTagBook();

    /** Returns an unmodifiable view of the filtered list of tags */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Returns the user prefs' group book file path.
     */
    Path getGroupBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
