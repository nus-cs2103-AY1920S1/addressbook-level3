package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.profile.Model;
import seedu.address.profile.ReadOnlyUserProfile;
import seedu.address.profile.person.Person;
import seedu.address.profile.records.Record;

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
     * Returns UserProfile.
     *
     * @see Model#getUserProfile()
     */
    ReadOnlyUserProfile getDukeCooks();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of records */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Returns the user prefs' Duke Cooks file path.
     */
    Path getDukeCooksFilePath();

    /**
     * Returns the user prefs' Health Records file path.
     */
    Path getHealthRecordsFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
