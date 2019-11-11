package seedu.address.logic.calendar;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.calendar.commands.CommandResult;
import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.task.Task;

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
     * Returns the CalendarAddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyCalendarAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Task> getFilteredPersonList();

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
