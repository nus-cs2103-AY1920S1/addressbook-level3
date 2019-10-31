package seedu.scheduler.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;

import seedu.scheduler.commons.core.GuiSettings;
import seedu.scheduler.logic.commands.CommandResult;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ReadOnlyList;
import seedu.scheduler.model.Schedule;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;

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

    /* TODO: REMOVE THE FOLLOWING LINES AFTER THEIR USAGE IS REMOVED */
    Path getAddressBookFilePath();

    /* TODO: REMOVE ABOVE LINES */

    // ==================================IntervieweeList and InterviewerList ======================================

    /**
     * Returns the uniterable, unmodifiable, unfiltered list of interviewees.
     */
    ReadOnlyList<Interviewee> getIntervieweeList();

    /**
     * Returns the uniterable, unmodifiable, unfiltered list of Interviewers.
     */
    ReadOnlyList<Interviewer> getInterviewerList();

    /**
     * Returns an iterable, unmodifiable view of the unfiltered list of all Interviewees.
     *
     * @see Model#getUnfilteredIntervieweeList()
     */
    ObservableList<Interviewee> getUnfilteredIntervieweeList();

    /**
     * Returns an iterable, unmodifiable view of the unfiltered list of all Interviewers.
     *
     * @see Model#getUnfilteredInterviewerList()
     */
    ObservableList<Interviewer> getUnfilteredInterviewerList();

    /**
     * Returns an iterable, unmodifiable view of the filtered list of Interviewees.
     *
     * @see Model#getMutableIntervieweeList()
     */
    ObservableList<Interviewee> getFilteredIntervieweeList();

    /**
     * Returns an iterable, unmodifiable view of the filtered list of Interviewers.
     *
     * @see Model#getMutableInterviewerList()
     */
    ObservableList<Interviewer> getFilteredInterviewerList();

    /**
     * Returns the user prefs' file path to the list of Interviewees.
     */
    Path getIntervieweeListFilePath();

    /**
     * Returns the user prefs' file path to the list of Interviewers.
     */
    Path getInterviewerListFilePath();

    // ============================================ Schedule ===================================================

    /**
     * Returns the list of Schedules.
     *
     * @see Model#getSchedulesList()
     */
    List<Schedule> getSchedulesList();

    /** Returns a list of @code{ObservableList} objects, each representing a Schedule table*/
    List<ObservableList<ObservableList<String>>> getObservableLists();

    /** Returns a list of lists of column titles, each list of column titles belong to a Schedule table*/
    List<List<String>> getTitlesLists();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
