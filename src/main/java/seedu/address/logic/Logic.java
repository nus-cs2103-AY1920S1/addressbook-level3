package seedu.address.logic;

import java.nio.file.Path;

import java.util.ArrayList;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CommandRecord;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
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
    CommandResult execute(String commandText) throws CommandException, ParseException, AlfredModelHistoryException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of Teams */
    ObservableList<Participant> getFilteredParticipantList();

    /** Returns an unmodifiable view of the filtered list of Teams */
    ObservableList<Team> getFilteredTeamList();

    /** Returns an unmodifiable view of the filtered list of Teams */
    ObservableList<Mentor> getFilteredMentorList();

    /** Returns the Record of all Commands entered by User */
    ArrayList<CommandRecord> getCommandHistory() throws AlfredModelHistoryException;

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' ParticipantList file path.
     */
    Path getParticipantListFilePath();

    /**
     * Returns the user prefs' TeamList file path.
     */
    Path getTeamListFilePath();

    /**
     * Returns the user prefs' MentorList file path.
     */
    Path getMentorListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
