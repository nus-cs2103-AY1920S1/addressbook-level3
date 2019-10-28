package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;

/**
 * The API of the Model component.
 */
public interface Model {

    /* TODO: REMOVE THE FOLLOWING LINES AFTER THEIR USAGE IS REMOVED */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    ObservableList<Person> getFilteredPersonList();

    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean hasPerson(Person person);

    void deletePerson(Person person);

    void addPerson(Person person);

    Person getPerson(String name) throws NoSuchElementException;

    void setPerson(Person target, Person editedPerson);

    /* TODO: REMOVE ABOVE LINES */

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

    /**
     * Returns the user prefs' file path to the list of Interviewees.
     */
    Path getIntervieweeListFilePath();

    /**
     * Sets the user prefs' file path to the list of Interviewees.
     */
    void setIntervieweeListFilePath(Path intervieweeListFilePath);

    /**
     * Returns the user prefs' file path to the list of Interviewers.
     */
    Path getInterviewerListFilePath();

    /**
     * Sets the user prefs' file path to the list of Interviewers.
     */
    void setInterviewerListFilePath(Path interviewerListFilePath);

    /**
     * Emails the given Interviewee.
     * The Interviewee must exist in the database.
     */
    void emailInterviewee(Interviewee interviewee) throws IOException;

    //=========== SchedulesList ==============================================================================

    /**
     * Replaces schedule data with the data in {@code schedule}.
     */
    void setSchedulesList(List<Schedule> schedulesList);

    /** Returns the schedulesList **/
    List<Schedule> getSchedulesList();

    /**
     * Returns a list of observable list of the schedules.
     */
    List<ObservableList<ObservableList<String>>> getObservableLists();

    /** Returns a list of lists of column titles, each list of column titles belong to a Schedule table*/
    List<List<String>> getTitlesLists();

    /**
     * Returns the interview slot assigned to the interviewee with the {@code intervieweeName}.
     */
    List<Slot> getInterviewSlots(String intervieweeName);

    /**
     * Returns the date of the schedule in which the interviewer exists in, otherwise return empty string.
     */
    String getInterviewerSchedule(Interviewer interviewer);

    /**
     * Adds an interviewer to one of the schedules if the interviewer's availability fall within those
     * schedules and returns true. Otherwise, the method will not add the interviewer and return false.
     */
    void addInterviewerSchedule(Interviewer interviewer);

    //=========== IntervieweeList & InterviewerList ====================================================================

    /**
     * Replaces the list of IntervieweeList data with the data in {@code interviewees}
     */
    void setIntervieweeList(List<Interviewee> interviewees);

    /**
     * Replaces the list of Interviewers data with the data in {@code interviewers}
     */
    void setInterviewerList(List<Interviewer> interviewers);

    /**
     * Returns a list of IntervieweeList.
     */
    List<Interviewee> getInterviewees();

    /**
     * Returns a list of Interviewers.
     */
    List<Interviewer> getInterviewers();

    /**
     * Returns an Interviewee given the name.
     * The Interviewee must exist in the database.
     * @param name The name of the Interviewee
     * @throws NoSuchElementException If the Interviewee does not exist in the database.
     */
    Interviewee getInterviewee(String name) throws NoSuchElementException;

    /**
     * Replaces the given Interviewee {@code target} with {@code editedInterviewee}
     * {@code target} must exist in the database.
     * The Interviewee identity of {@code editedInterviewee} must not be the same as another existing
     * Interviewee in the database.
     */
    void setInterviewee(Interviewee target, Interviewee editedInterviewee);

    /**
     * Returns an Interviewer given the name.
     * The Interviewer must exist in the database.
     * @param name The name of the Interviewer
     * @throws NoSuchElementException if the Interviewer does not exist in the database.
     */
    Interviewer getInterviewer(String name) throws NoSuchElementException;

    /**
     * Replaces the given Interviewer {@code target} with {@code editedInterviewer}
     * {@code target} must exist in the database.
     * The Interviewer identity of {@code editedInterviewer} must not be the same as another existing
     * Interviewer in the database.
     */
    void setInterviewer(Interviewer target, Interviewer editedInterviewer);

    /**
     * Returns true if an Interviewee with the same identity as {@code interviewee} exists in the database.
     */
    boolean hasInterviewee(Interviewee interviewee);

    /**
     * Returns true if an Interviewer with the same identity as {@code interviewer} exists in the database.
     */
    boolean hasInterviewer(Interviewer interviewer);

    /**
     * Deletes the given Interviewee.
     * The Interviewee must exist in the database.
     */
    void deleteInterviewee(Interviewee target);

    /**
     * Deletes the given Interviewer.
     * The Interviewer must exist in the database.
     */
    void deleteInterviewer(Interviewer target);

    /**
     * Adds the given Interviewee.
     * {@code interviewee} must not already exist in the database.
     */
    void addInterviewee(Interviewee interviewee);

    /**
     * Adds the given Interviewer.
     * {@code interviewer} must not already exist in the database.
     */
    void addInterviewer(Interviewer interviewer);

    /**
     * Returns the IntervieweeList.
     */
    ReadOnlyIntervieweeList getIntervieweeList();

    /**
     * Returns the InterviewerList.
     */
    ReadOnlyInterviewerList getInterviewerList();

    /**
     * Returns an unmodifiable view of the Interviewee list.
     */
    ObservableList<Interviewee> getObservableIntervieweeList();

    /**
     * Returns an unmodifiable view of the Interviewer list.
     */
    ObservableList<Interviewer> getObservableInterviewerList();

}
