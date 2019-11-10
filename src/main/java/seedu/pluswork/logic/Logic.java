package seedu.pluswork.logic;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;

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
    CommandResult execute(String commandText) throws CommandException, ParseException, FileNotFoundException;

    /**
     * Returns the ProjectDashboard.
     *
     * @see seedu.pluswork.model.Model#getProjectDashboard()
     */
    ReadOnlyProjectDashboard getProjectDashboard();


    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Returns an unmodifiable view of the filtered list of members
     */
    ObservableList<Member> getFilteredMemberList();

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Task> getFilteredTaskListNotStarted();

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Task> getFilteredTaskListDoing();


    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Task> getFilteredTaskListDone();

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Task> getFilteredTaskListByDeadline();

    /**
     * Returns an unmodifiable view of the filtered list of inventories
     */
    ObservableList<Inventory> getFilteredInventoryList();

    ObservableList<TasMemMapping> getFilteredTasMemMappingList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getProjectDashboardFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the project statistics.
     */
    Statistics getStatistics();

    /**
     * Returns possible meeting times.
     */
    MeetingQuery getMeetingQuery();

    /**
     * Returns list of schedule meetings.
     */
    ObservableList<Meeting> getFilteredMeetingList();

    /**
     * Returns the current theme of +Work.
     */
    Theme getCurrentTheme();

    /**
     * Returns the clock format of +Work.
     */
    ClockFormat getClockFormat();

    /**
     * returns a list of suggestions for command
     * @param input
     * @return
     */
    LinkedList<String> getAutoCompleteResults(String input);


}
