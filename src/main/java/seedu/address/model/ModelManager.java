package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.mapping.Mapping;
import seedu.address.model.mapping.TasMemMapping;
import seedu.address.model.mapping.UniqueInvMemMappingList;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.settings.ClockFormat;
import seedu.address.model.settings.Theme;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.task.Task;

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
    private final FilteredList<Inventory> filteredInventories;
    private Statistics stats;


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
        stats = new Statistics(filteredMembers, filteredTasks, filteredMappings);
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
        projectDashboard.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        projectDashboard.addTask(task);
        updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        projectDashboard.setTask(target, editedTask);
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
        projectDashboard.removeInventory(target);
    }

    @Override
    public void setInventory(Inventory target, Inventory editedInventory) {
        requireAllNonNull(target, editedInventory);
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
                && filteredMembers.equals(other.filteredMembers);
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
        projectDashboard.removeMember(target);
    }

    @Override
    public void addMember(Member member) {
        projectDashboard.addMember(member);
        updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public void setMember(Member target, Member editedMember) {
        requireAllNonNull(target, editedMember);

        projectDashboard.setMember(target, editedMember);
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
    public void addMapping(InvMemMapping mapping) {
        projectDashboard.addMapping(mapping);
        updateFilteredMappingsList(PREDICATE_SHOW_ALL_MAPPINGS);
    }

    @Override
    public void addMapping(InvTasMapping mapping) {
        projectDashboard.addMapping(mapping);
        updateFilteredMappingsList(PREDICATE_SHOW_ALL_MAPPINGS);
    }

    @Override
    public void addMapping(TasMemMapping mapping) {
        projectDashboard.addMapping(mapping);
        updateFilteredMappingsList(PREDICATE_SHOW_ALL_MAPPINGS);
    }

    @Override
    public void deleteMapping(InvMemMapping mapping) {
        projectDashboard.removeMapping(mapping);
    }

    @Override
    public void deleteMapping(InvTasMapping mapping) {
        projectDashboard.removeMapping(mapping);
    }

    @Override
    public void deleteMapping(TasMemMapping mapping) {
        projectDashboard.removeMapping(mapping);
    }


    @Override
    public boolean hasMapping(InvTasMapping mapping) {
        requireNonNull(mapping);
        return projectDashboard.hasMapping(mapping);
    }

    @Override
    public boolean hasMapping(InvMemMapping mapping) {
        requireNonNull(mapping);
        return projectDashboard.hasMapping(mapping);
    }

    @Override
    public boolean hasMapping(TasMemMapping mapping) {
        requireNonNull(mapping);
        return projectDashboard.hasMapping(mapping);
    }

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

    // ========= Statistics =================================================================================
    @Override
    public Statistics getStatistics() {
        return this.stats;
    }

    @Override
    public void setStatistics(Statistics newStats) {
        this.stats = newStats;
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
}
