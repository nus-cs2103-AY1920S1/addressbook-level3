package seedu.system.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.system.commons.core.GuiSettings;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;

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
     * Returns the Data.
     *
     * @see seedu.system.model.Model#getPersons()
     */
    ReadOnlyData getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Competition> getFilteredCompetitionList();

    /**
     * Returns an unmodifiable view of the filtered list of participations
     */
    ObservableList<Participation> getFilteredParticipationList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
