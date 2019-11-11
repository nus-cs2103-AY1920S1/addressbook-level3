package seedu.scheduler.model;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.scheduler.commons.core.GuiSettings;
import seedu.scheduler.commons.exceptions.ScheduleException;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;
import seedu.scheduler.ui.RefreshListener;
import seedu.scheduler.ui.TabListener;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Interviewee> PREDICATE_SHOW_ALL_INTERVIEWEES = unused -> true;
    Predicate<Interviewer> PREDICATE_SHOW_ALL_INTERVIEWERS = unused -> true;

    // ================================== App Status ======================================

    /**
     * Sets the status whether the schedule command has been executed by the user. See @code{isScheduled} for more
     * details on when to set this status.
     */
    void setScheduled(boolean scheduled);

    /**
     * Returns true if the scheduling command is executed by the user. Returns false if otherwise or the user import
     * interviewer's availability again after the scheduling command is executed.
     * @return
     */
    boolean isScheduled();

    // ================================== IntervieweeList and InterviewerList ======================================

    List<Schedule> getEmptyScheduleList();

    /**
     * Replaces the list of IntervieweeList data with the data in {@code interviewees}
     */
    void setIntervieweeList(List<Interviewee> interviewees);

    /**
     * Replaces the list of Interviewers data with the data in {@code interviewers}
     */
    void setInterviewerList(List<Interviewer> interviewers);

    /**
     * Sets the user prefs' interviewee list file path.
     */
    void setIntervieweeListFilePath(Path intervieweeListFilePath);

    /**
     * Sets the user pref's interviewer list file path.
     */
    void setInterviewerListFilePath(Path interviewerListFilePath);

    /**
     * Returns the user pref's interviewee list file path.
     */
    Path getIntervieweeListFilePath();

    /**
     * Returns the user pref's interviewer list file path.
     */
    Path getInterviewerListFilePath();

    /**
     * Returns the modifiable internal list of {@code Interviewee}.
     */
    ReadAndWriteList<Interviewee> getMutableIntervieweeList();

    /**
     * Returns the modifiable internal list of {@code Interviewer}.
     */
    ReadAndWriteList<Interviewer> getMutableInterviewerList();

    /**
     * Returns an unmodifiable list view of {@code Interviewee} backed by the internal {@code IntervieweeList}.
     *
     * @see Model#updateFilteredIntervieweeList(Predicate)
     */
    ObservableList<Interviewee> getFilteredIntervieweeList();

    /**
     * Returns an unmodifiable list view of {@code Interviewer} backed by the internal {@code InterviewerList}.
     *
     * @see Model#updateFilteredInterviewerList(Predicate)
     */
    ObservableList<Interviewer> getFilteredInterviewerList();

    /**
     * Returns an unmodifiable list view of all {@code Interviewee}s backed by the internal {@code IntervieweeList}.
     */
    ObservableList<Interviewee> getUnfilteredIntervieweeList();

    /**
     * Returns an unmodifiable list view of all {@code Interviewer}s backed by the internal {@code InterviewerList}.
     */
    ObservableList<Interviewer> getUnfilteredInterviewerList();

    /**
     * Restricts the {@code ObservableList} of interviewee to display only what returns true on Predicate while
     * leaving the original {@code IntervieweeList} unmodified.
     */
    void updateFilteredIntervieweeList(Predicate<Interviewee> predicate);

    /**
     * Restricts the {@code ObservableList} of interviewer to display only what returns true on Predicate while
     * leaving the original {@code InterviewerList} unmodified.
     */
    void updateFilteredInterviewerList(Predicate<Interviewer> predicate);

    /**
     * Adds an interviewer to the model's {@code InterviewerList}. Must be unique.
     */
    void addInterviewer(Interviewer interviewer);

    /**
     * Adds an interviewee to the model's {@code IntervieweeList}. Must be unique.
     */
    void addInterviewee(Interviewee interviewee);

    /**
     * Returns true if an interviewee with the same identity exists in the interviewee list.
     */
    boolean hasInterviewee(Interviewee interviewee);

    /**
     * Returns true if an interviewee with the same Name exists in the interviewee list.
     */
    boolean hasInterviewee(Name name);

    /**
     * Returns true if an interviewer with the same identity exists in the interviewer list.
     */
    boolean hasInterviewer(Interviewer interviewer);

    /**
     * Returns true if an interviewer with the same Name exists in the interviewer list.
     */
    boolean hasInterviewer(Name name);

    /**
     * Returns an Interviewee given a name. The Interviewee must exist in the database, or an exception is thrown.
     */
    Interviewee getInterviewee(String name) throws NoSuchElementException;

    /**
     * Returns an Interviewer given a name. The Interviewer must exist in the database, or an exception is thrown.
     */
    Interviewer getInterviewer(String name) throws NoSuchElementException;

    /**
     * Deletes the interviewee from the {@code IntervieweeList}. An exception is thrown if {@code target} is not found.
     */
    void deleteInterviewee(Interviewee target) throws PersonNotFoundException;

    /**
     * Deletes the interviewer from the {@code InterviewerList}. An exception is thrown if {@code target} is not found.
     */
    void deleteInterviewer(Interviewer target) throws PersonNotFoundException;

    /**
     * Edits the interviewee with the {@code editedTarget}. An exception is thrown if {@code target} is not found.
     */
    void setInterviewee(Interviewee target, Interviewee editedTarget) throws PersonNotFoundException;

    /**
     * Edits the interviewer with the {@code editedTarget}. An exception is thrown if {@code target} is not found.
     */
    void setInterviewer(Interviewer target, Interviewer editedTarget) throws PersonNotFoundException;

    // =========================================== Mass Email =================================================

    /**
     * Emails the given Interviewee.
     * The Interviewee must exist in the database.
     */
    void emailInterviewee(Interviewee interviewee) throws IOException;

    // ================================== Refresh Listener ======================================

    /**
     * Add a refresh listener to listen to changes of Schedule data.
     */
    void addRefreshListener(RefreshListener listener);

    /**
     * Add a tabListener to change tab when command is given.
     */
    void addTabListener(TabListener tabListener);

    // ================================== Tab Change ============================================
    void intervieweeTabChange();

    void interviewerTabChange();

    void scheduleTabChange();

    // ============================================ Schedule ===================================================

    /**
     * Generates a list of empty schedules from the current list of interviewers.
     * @throws ParseException
     */
    void setEmptyScheduleList() throws ParseException;

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    void setSchedulesList(List<Schedule> schedulesList);

    /**
     * Returns the interview slot allocated to the interviewee with the {@code intervieweeName}.
     * @return
     */
    Slot getAllocatedSlot(String intervieweeName);

    /** Returns a list of observable list of the schedules **/
    List<ObservableList<ObservableList<String>>> getObservableLists();

    /** Returns the schedulesList **/
    List<Schedule> getSchedulesList();

    /** Returns a list of lists of column titles, each list of column titles belong to a Schedule table*/
    List<List<String>> getTitlesLists();

    /**
     * Resets the relevant data before running a schedule command.
     * 1. Removes the allocated time slot of all interviewees
     * 2. Removes the allocated time slots of all interviewers
     * 3. Resets the schedule list such that it only reflects the interviewers' availability and the scheduled result
     * is cleared.
     */
    void resetScheduledResult();

    /**
     * Updates the schedules after schedule command is executed by placing the interviewees into their allocated slot.
     **/
    void updateSchedulesAfterScheduling() throws ScheduleException;

    // ============================================ User Prefs ===================================================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
