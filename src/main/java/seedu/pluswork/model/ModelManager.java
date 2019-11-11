package seedu.pluswork.model;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.pluswork.commons.core.GuiSettings;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.InvTasMapping;
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
 * Represents the in-memory model of +Work data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProjectDashboard projectDashboard;
    private final UserPrefs userPrefs;
    private final UserSettings userSettings;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredTasksNotStarted;
    private final FilteredList<Task> filteredTasksDoing;
    private final FilteredList<Task> filteredTasksDone;
    private final FilteredList<Task> filteredTasksByDeadline;
    private final FilteredList<Member> filteredMembers;
    private final FilteredList<Mapping> filteredMappings;
    private final FilteredList<TasMemMapping> filteredTasMemMappings;
    private final FilteredList<InvMemMapping> filteredInvMemMappings;
    private final FilteredList<Inventory> filteredInventories;
    private final FilteredList<CalendarWrapper> filteredCalendars;
    private final FilteredList<Meeting> filteredMeetings;
    private Statistics stats;
    private final Stack<ReadOnlyProjectDashboard> previousSaveState = new Stack<>();
    private final Stack<ReadOnlyProjectDashboard> redoSaveState = new Stack<>();

    /**
     * Initialises a ModelManager with the given projectDashboard, userPrefs and userSettings.
     */
    public ModelManager(ReadOnlyProjectDashboard projectDashboard, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyUserSettings userSettings) {
        super();
        requireAllNonNull(projectDashboard, userPrefs, userSettings);

        logger.fine("Initializing with address book: " + projectDashboard + " and user prefs " + userPrefs);

        this.projectDashboard = new ProjectDashboard(projectDashboard);
        this.userPrefs = new UserPrefs(userPrefs);
        this.userSettings = new UserSettings(userSettings);

        filteredTasks = new FilteredList<>(this.projectDashboard.getTaskList());
        filteredTasksNotStarted = new FilteredList<>(this.projectDashboard.getTasksNotStarted());
        filteredTasksDoing = new FilteredList<>(this.projectDashboard.getTasksDoing());
        filteredTasksDone = new FilteredList<>(this.projectDashboard.getTasksDone());
        filteredTasksByDeadline = new FilteredList<>(this.projectDashboard.getTasksByDeadline());
        filteredMembers = new FilteredList<>(this.projectDashboard.getMemberList());
        filteredInventories = new FilteredList<>(this.projectDashboard.getInventoryList());
        filteredMappings = new FilteredList<>(this.projectDashboard.getMappingList());
        filteredTasMemMappings = new FilteredList<>(this.projectDashboard.getTasMemMappingList());
        filteredInvMemMappings = new FilteredList<>(this.projectDashboard.getInvMemMappingList());
        filteredCalendars = new FilteredList<>(this.projectDashboard.getCalendarList());
        filteredMeetings = new FilteredList<>(this.projectDashboard.getMeetingList());
        stats = new Statistics(filteredMembers, filteredTasks, filteredTasMemMappings, filteredInvMemMappings);
        stats.doCalculations();

    }

    public ModelManager() {
        this(new ProjectDashboard(), new UserPrefs(), new UserSettings());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getProjectDashboardFilePath() {
        return userPrefs.getProjectDashboardFilePath();
    }

    @Override
    public void setProjectDashboardFilePath(Path projectDashboardFilePath) {
        requireNonNull(projectDashboardFilePath);
        userPrefs.setProjectDashboardFilePath(projectDashboardFilePath);
    }

    //=========== ProjectDashboard ================================================================================

    @Override
    public void setProjectDashboard(ReadOnlyProjectDashboard projectDashboard) {
        this.projectDashboard.resetData(projectDashboard);
    }

    @Override
    public ReadOnlyProjectDashboard getProjectDashboard() {
        return projectDashboard;
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return projectDashboard.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        requireNonNull(target);
        saveDashboardState();
        projectDashboard.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        requireNonNull(task);
        saveDashboardState();
        projectDashboard.addTask(task);
        updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        saveDashboardState();
        projectDashboard.setTask(target, editedTask);
        updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public ObservableList<Task> getFilteredTaskListNotStarted() {
        projectDashboard.splitTasksBasedOnStatus();
        return filteredTasksNotStarted;
    }

    @Override
    public ObservableList<Task> getFilteredTaskListDoing() {
        return filteredTasksDoing;
    }

    @Override
    public ObservableList<Task> getFilteredTaskListDone() {
        return filteredTasksDone;
    }


    @Override
    public ObservableList<Task> getFilteredTaskListByDeadline() {
        projectDashboard.splitTasksByDeadline();
        return filteredTasksByDeadline;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTasksList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTasksList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    public int getTasksLength() {
        return filteredTasks.size();
    }


    //=========== Inventory  =============================================================

    @Override
    public void addInventory(Inventory inventory) {
        saveDashboardState();
        projectDashboard.addInventory(inventory);
        updateFilteredInventoriesList(PREDICATE_SHOW_ALL_INVENTORIES);
    }

    @Override
    public boolean hasInventory(Inventory inventory) {
        requireNonNull(inventory);
        return projectDashboard.hasInventory(inventory);
    }

    @Override
    public void deleteInventory(Inventory target) {
        saveDashboardState();
        projectDashboard.removeInventory(target);
    }

    @Override
    public void setInventory(Inventory target, Inventory editedInventory) {
        requireAllNonNull(target, editedInventory);

        saveDashboardState();
        projectDashboard.setInventory(target, editedInventory);
    }

    @Override
    public ObservableList<Inventory> getFilteredInventoriesList() {
        return filteredInventories;
    }

    @Override
    public void updateFilteredInventoriesList(Predicate<Inventory> predicate) {
        requireNonNull(predicate);
        filteredInventories.setPredicate(predicate);
    }

    //=========== ProjectDashboard (member) ===============================================

    @Override
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return projectDashboard.hasMember(member);
    }

    @Override
    public boolean hasMemberId(MemberId memId) {
        requireNonNull(memId);
        return projectDashboard.hasMemId(memId);
    }

    @Override
    public void deleteMember(Member target) {
        requireNonNull(target);
        saveDashboardState();
        projectDashboard.removeMember(target);
    }

    @Override
    public void addMember(Member member) {
        requireNonNull(member);
        saveDashboardState();
        projectDashboard.addMember(member);
        updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public void setMember(Member target, Member editedMember) {
        requireAllNonNull(target, editedMember);

        saveDashboardState();
        projectDashboard.setMember(target, editedMember);
        updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public int getMembersLength() {
        return filteredMembers.size();
    }

    //=========== Filtered Member List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    public ObservableList<Member> getFilteredMembersList() {
        return filteredMembers;
    }

    @Override
    public void updateFilteredMembersList(Predicate<Member> predicate) {
        requireNonNull(predicate);
        filteredMembers.setPredicate(predicate);
    }

    //=========== ProjectDashboard (mapping) ===============================================

    @Override
    public void addMapping(Mapping mapping) {
        requireNonNull(mapping);
        saveDashboardState();
        projectDashboard.addMapping(mapping);
        updateFilteredMappingsList(PREDICATE_SHOW_ALL_MAPPINGS);
    }

    @Override
    public void deleteMapping(Mapping mapping) {
        requireNonNull(mapping);
        saveDashboardState();
        projectDashboard.removeMapping(mapping);
    }

    @Override
    public boolean hasMapping(Mapping mapping) {
        requireNonNull(mapping);
        return projectDashboard.hasMapping(mapping);
    }

    //=========== Filtered Mapping List Accessors =============================================================

    @Override
    public ObservableList<Mapping> getFilteredMappingsList() {
        return filteredMappings;
    }

    @Override
    public void updateFilteredMappingsList(Predicate<Mapping> predicate) {
        requireNonNull(predicate);
        filteredMappings.setPredicate(predicate);
    }

    @Override
    public ObservableList<TasMemMapping> getFilteredTasMemMappingsList() {
        return filteredTasMemMappings;
    }

    @Override
    public ObservableList<InvMemMapping> getFilteredInvMemMappingsList() {
        return filteredInvMemMappings;
    }


    // ========= Statistics =================================================================================
    @Override
    public Statistics getStatistics() {
        return this.stats;
    }

    @Override
    public void setStatistics(Statistics newStats) {
        saveDashboardState();
        this.stats = newStats;
    }

    // ========= Calendar Commands ===========================================================================

    @Override
    public void addCalendar(CalendarWrapper calendar) {
        requireNonNull(calendar);
        saveDashboardState();
        projectDashboard.addCalendar(calendar);
        filteredCalendars.setPredicate(PREDICATE_SHOW_ALL_CALENDARS);
    }

    @Override
    public boolean hasCalendar(CalendarWrapper calendar) {
        requireNonNull(calendar);
        return projectDashboard.hasCalendar(calendar);
    }

    @Override
    public void deleteCalendar(CalendarWrapper calendar) {
        requireNonNull(calendar);
        saveDashboardState();
        projectDashboard.deleteCalendar(calendar);
    }

    @Override
    public ObservableList<CalendarWrapper> getFilteredCalendarList() {
        return filteredCalendars;
    }

    // ========= Meeting Commands ===========================================================================

    @Override
    public void addMeeting(Meeting meeting) {
        requireNonNull(meeting);
        saveDashboardState();
        projectDashboard.addMeeting(meeting);
        filteredMeetings.setPredicate(PREDICATE_SHOW_ALL_MEETINGS);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        requireNonNull(meeting);
        saveDashboardState();
        projectDashboard.deleteMeeting(meeting);
    }

    @Override
    public boolean hasMeeting(Meeting meeting) {
        requireNonNull(meeting);
        return projectDashboard.hasMeeting(meeting);
    }

    @Override
    public ObservableList<Meeting> getFilteredMeetingList() {
        return filteredMeetings;
    }

    @Override
    public void updateFilteredMeetingsList(Predicate<Meeting> predicate) {
        filteredMeetings.setPredicate(predicate);
    }

    @Override
    public void findMeetingTime(LocalDateTime startDate, LocalDateTime endDate, Duration meetingDuration) {
        projectDashboard.findMeetingTime(startDate, endDate, meetingDuration);
    }

    @Override
    public MeetingQuery getMeetingQuery() {
        return projectDashboard.getMeetingQuery();
    }


    // ========= General Commands ===========================================================================

    public ObservableList<ObservableList<InvMemMapping>> getInvMemPDFList() {
        return projectDashboard.getInvMemPDFList();
    }

    public ObservableList<ObservableList<InvTasMapping>> getInvTasPDFList() {
        return projectDashboard.getInvTasPDFList();
    }

    public ArrayList<Integer> getInvMemLonelyList() {
        return projectDashboard.getInvMemLonelyList();
    }

    public ArrayList<Integer> getInvTasLonelyList() {
        return projectDashboard.getInvTasLonelyList();
    }

    // ========= General Commands ===========================================================================

    @Override
    public void undo() {
        ReadOnlyProjectDashboard previousDashboard = previousSaveState.pop();
        redoSaveState.push(new ProjectDashboard(projectDashboard));
        projectDashboard.resetData(previousDashboard);
        updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
    }

    public boolean canUndo() {
        return !previousSaveState.empty();
    }

    @Override
    public void redo() {
        saveDashboardState();
        ReadOnlyProjectDashboard redoableDashboard = redoSaveState.pop();
        projectDashboard.resetData(redoableDashboard);
        updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public boolean canRedo() {
        return !redoSaveState.empty();
    }

    public void saveDashboardState() {
        previousSaveState.push(new ProjectDashboard(projectDashboard));
    }

    // ========= User Settings =================================================================================
    @Override
    public UserSettings getUserSettings() {
        return userSettings;
    }

    @Override
    public Path getUserSettingsFilePath() {
        return userSettings.getUserSettingsFilePath();
    }

    // TODO for testing purposes
    public void setUserSettingsFilePath(Path newPath) {
        requireAllNonNull(newPath);
        userSettings.setUserSettingsFilePath(newPath);
    }

    @Override
    public Theme getCurrentTheme() {
        return userSettings.getTheme();
    }

    @Override
    public void setCurrentTheme(Theme newTheme) {
        userSettings.setTheme(newTheme);
    }

    @Override
    public ClockFormat getCurrentClockFormat() {
        return userSettings.getClockFormat();
    }

    @Override
    public void setClockFormat(ClockFormat newClockFormat) {
        userSettings.setClockFormat(newClockFormat);
    }

    // ====================== Util methods ======================================================================

    @Override
    public void updateData() {
        updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    // ============================================================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return projectDashboard.equals(other.projectDashboard)
                && userPrefs.equals(other.userPrefs)
                && userSettings.equals(other.userSettings)
                && filteredTasks.equals(other.filteredTasks)
                && filteredTasksNotStarted.equals(other.filteredTasksNotStarted)
                && filteredTasksDoing.equals(other.filteredTasksDoing)
                && filteredTasksDone.equals(other.filteredTasksDone)
                && filteredTasksByDeadline.equals(other.filteredTasksByDeadline)
                && filteredMembers.equals(other.filteredMembers)
                && filteredMappings.equals(other.filteredMappings)
                && filteredTasMemMappings.equals(other.filteredTasMemMappings)
                && filteredInvMemMappings.equals(other.filteredInvMemMappings)
                && filteredInventories.equals(other.filteredInventories)
                && filteredCalendars.equals(other.filteredCalendars)
                && filteredMeetings.equals(other.filteredMeetings)
                && stats.equals(other.stats);
    }
}
