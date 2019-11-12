package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Mode;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCardBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.ReadOnlyPasswordBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.ExpiringCard;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.note.Note;
import seedu.address.model.password.Password;
import seedu.address.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    ReadOnlyNoteBook getNoteBook();


    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Note> getFilteredNoteList();

    /**
     * Returns the FileBook.
     *
     * @see seedu.address.model.Model#getFileBook()
     */
    ReadOnlyFileBook getFileBook();

    /** Returns an unmodifiable view of the filtered list of files */
    ObservableList<EncryptedFile> getFilteredFileList();

    /**
     * Returns the user prefs' file book file path.
     */
    Path getFileBookFilePath();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' note book file path.
     */
    Path getNoteBookFilePath();


    /**
     * Returns the CardBook.
     *
     * @see seedu.address.model.Model#getCardBook()
     */
    ReadOnlyCardBook getCardBook();

    /** Returns an unmodifiable view of the filtered list of cards */
    ObservableList<Card> getFilteredCardList();

    /** Returns an unmodifiable view of the list of expiring cards
     * @return*/
    ObservableList<ExpiringCard> getExpiringCardList();

    /**
     * Returns the user prefs' card book file path.
     */
    Path getCardBookFilePath();
    /**
     *
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);


    void setMode(Mode newMode);

    Mode getMode();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Password> getFilteredPasswordList();

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getPasswordBook()
     */
    ReadOnlyPasswordBook getPasswordBook();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getPasswordBookFilePath();
}
