package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Slot;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.RefreshListener;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Interviewee> PREDICATE_SHOW_ALL_INTERVIEWEES = unused -> true;
    Predicate<Interviewer> PREDICATE_SHOW_ALL_INTERVIEWERS = unused -> true;

    // ==================================IntervieweeList and InterviewerList ======================================

    void setEmptyScheduleList() throws ParseException;

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
     * Returns true if an interviewer with the same identity exists in the interviewer list.
     */
    boolean hasInterviewer(Interviewer interviewer);

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

    // ============================================ Schedule ===================================================

    /**
     * Adds an interviewer to one of the schedules if the interviewer's availability fall within those schedules
     * and returns true. Otherwise, the method will not addEntity the interviewer and return false.
     */
    void addInterviewerToSchedule(Interviewer interviewer);
    /**
     * Returns the date of the schedule in which the interviewer exists in, otherwise return empty string.
     */
    String scheduleHasInterviewer(Interviewer interviewer);

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    void setSchedulesList(List<Schedule> schedulesList);

    /**
     * Returns the interview slot assigned to the interviewee with the {@code intervieweeName}.
     */
    List<Slot> getInterviewSlots(String intervieweeName);

    /**
     * Returns a list of observable list of the schedules.
     */
    List<ObservableList<ObservableList<String>>> getObservableLists();

    /** Returns the schedulesList **/
    List<Schedule> getSchedulesList();

    /** Returns a list of lists of column titles, each list of column titles belong to a Schedule table*/
    List<List<String>> getTitlesLists();

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
     * Clears the allocated slot of all interviewees.
     */
    void clearAllAllocatedSlot();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
