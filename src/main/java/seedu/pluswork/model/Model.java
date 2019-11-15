package seedu.pluswork.model;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.Mapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.statistics.Statistics;
import seedu.pluswork.model.task.Task;

//Remove after changing to ObservableList for Calendar

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Inventory> PREDICATE_SHOW_ALL_INVENTORIES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Mapping> PREDICATE_SHOW_ALL_MAPPINGS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<CalendarWrapper> PREDICATE_SHOW_ALL_CALENDARS = unused -> true;

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
     * Returns the user prefs' project dashboard file path.
     */
    Path getProjectDashboardFilePath();

    /**
     * Sets the user prefs' project dashboard file path.
     */
    void setProjectDashboardFilePath(Path projectDashboardFilePath);

    /**
     * Replaces address book data with the data in {@code projectDashboard}.
     */
    void setProjectDashboard(ReadOnlyProjectDashboard projectDashboard);

    /**
     * Returns the ProjectDashboard
     */
    ReadOnlyProjectDashboard getProjectDashboard();

    /// Task

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given task.
     * The task must exist in the address book.
     */
    void deleteTask(Task target);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the address book.
     */
    void addTask(Task task);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the address book.
     */
    void setTask(Task target, Task editedTask);

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskListByDeadline();

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskListNotStarted();

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskListDoing();

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskListDone();

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTasksList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTasksList(Predicate<Task> predicate);

    /// Member

    /**
     * Returns true if a member with the same identity as {@code member} exists in the address book.
     */
    boolean hasMember(Member member);

    /**
     * Returns true if a member with the same identity as {@code memberId} exists in the dashboard.
     */
    boolean hasMemberId(MemberId memId);

    /**
     * Deletes the given member.
     * The member must exist in the address book.
     */
    void deleteMember(Member member);

    /**
     * Adds the given member.
     * {@code member} must not already exist in the address book.
     */
    void addMember(Member member);

    /**
     * Replaces the given task {@code target} with {@code editedMember}.
     * {@code target} must exist in the address book.
     * The member identity of {@code editedMember} must not be the same as another existing task in the address book.
     */
    void setMember(Member target, Member editedMember);

    /**
     * returns length of filteredMembers
     */
    int getMembersLength();

    /**
     * Returns an unmodifiable view of the filtered member list
     */
    ObservableList<Member> getFilteredMembersList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMembersList(Predicate<Member> predicate);

    /**
     * returns length of filteredList
     */
    int getTasksLength();

    /// Inventory

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Inventory> getFilteredInventoriesList();

    /**
     * Updates the filter of the filtered inventories list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInventoriesList(Predicate<Inventory> predicate);


    /**
     * Adds the given inventory.
     * {@code inventory} must not already exist in the address book.
     */
    void addInventory(Inventory inventory);

    /**
     * Returns true if a inventory with the same identity as {@code inventory} exists in the address book.
     */
    boolean hasInventory(Inventory inventory);

    /**
     * Deletes the given inventory.
     * The inventory must exist in the address book.
     */
    void deleteInventory(Inventory target);

    // Mapping

    /**
     * Replaces the given task {@code target} with {@code editedInventory}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedInventory} must not be the same as another existing inventory in the dashboard.
     */
    void setInventory(Inventory target, Inventory editedInventory);

    void addMapping(Mapping mapping);

    void deleteMapping(Mapping mapping);

    boolean hasMapping(Mapping mapping);

    ObservableList<Mapping> getFilteredMappingsList();

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    boolean hasMeeting(Meeting meeting);

    void findMeetingTime(LocalDateTime startDate, LocalDateTime endDate, Duration duration);

    ObservableList<Meeting> getFilteredMeetingList();

    void updateFilteredMeetingsList(Predicate<Meeting> predicate);

    Statistics getStatistics();

    void setStatistics(Statistics newStats);

    void undo();

    void redo();

    boolean canUndo();

    boolean canRedo();

    ObservableList<TasMemMapping> getFilteredTasMemMappingsList();

    ObservableList<InvMemMapping> getFilteredInvMemMappingsList();

    void updateFilteredMappingsList(Predicate<Mapping> predicate);

    void addCalendar(CalendarWrapper calendar);

    void deleteCalendar(CalendarWrapper calendar);

    boolean hasCalendar(CalendarWrapper calendar);

    ObservableList<CalendarWrapper> getFilteredCalendarList();

    // Settings

    /**
     * Returns the user settings of +Work.
     */
    UserSettings getUserSettings();

    /**
     * Returns the user settings file path.
     */
    Path getUserSettingsFilePath();

    /**
     * Returns the possible meeting times generated by the user query.
     *
     * @return possible meeting times
     */
    MeetingQuery getMeetingQuery();

    /**
     * Returns the current theme of +Work.
     *
     * @return the current theme
     */
    Theme getCurrentTheme();

    /**
     * Replaces the theme of +Work with {@code newTheme}.
     *
     * @param newTheme the theme to replace with
     */
    void setCurrentTheme(Theme newTheme);

    /**
     * Returns the current clock format of +Work.
     *
     * @return the current clock format
     */
    ClockFormat getCurrentClockFormat();

    /**
     * Replaces the clock format of +Work with {@code newClockFormat}.
     *
     * @param newClockFormat the format to replace with
     */
    void setClockFormat(ClockFormat newClockFormat);

    /**
     * Update the predicates to show all data after a {@code FindTaskCommand} is executed.
     */
    void updateData();
}
