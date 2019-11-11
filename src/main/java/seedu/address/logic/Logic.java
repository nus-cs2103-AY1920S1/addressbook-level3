package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.note.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;


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
    CommandResult execute(String commandText, boolean isUnknown) throws CommandException, ParseException;

    /**
     * Returns the TutorAid.
     *
     * @see seedu.address.model.Model#getTutorAid()
     */
    ReadOnlyTutorAid getTutorAid();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of earnings */
    ObservableList<Earnings> getFilteredEarningsList();

    /** Returns an unmodifiable view of the filtered list of CommandObjects */
    ObservableList<CommandObject> getFilteredCommandsList();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();

    /** Returns an unmodifiable view of the filtered list of reminders */
    ObservableList<Reminder> getFilteredReminderList();

    /** Returns an unmodifiable view of the filtered list of notes */
    ObservableList<Notes> getFilteredNotesList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getTutorAidFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
