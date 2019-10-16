package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.inventory.Inventory;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.task.Task;
import seedu.address.model.mapping.Mapping;

//import seedu.address.model.task.NameContainsKeywordsPredicate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProjectDashboard projectDashboard;
    private final UserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredTasksNotStarted;
    private final FilteredList<Task> filteredTasksDoing;
    private final FilteredList<Task> filteredTasksDone;
    private final FilteredList<Member> filteredMembers;
    private final FilteredList<Mapping> filteredMappings;
    private final FilteredList<Inventory> filteredInventories;


    /**
     * Initializes a ModelManager with the given projectDashboard and userPrefs.
     */
    public ModelManager(ReadOnlyProjectDashboard projectDashboard, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(projectDashboard, userPrefs);

        logger.fine("Initializing with address book: " + projectDashboard + " and user prefs " + userPrefs);

        this.projectDashboard = new ProjectDashboard(projectDashboard);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredTasks = new FilteredList<>(this.projectDashboard.getTaskList());
        filteredTasksNotStarted = new FilteredList<>(this.projectDashboard.getTasksNotStarted());
        filteredTasksDoing = new FilteredList<>(this.projectDashboard.getTasksDoing());
        filteredTasksDone = new FilteredList<>(this.projectDashboard.getTasksDone());
        filteredMembers = new FilteredList<>(this.projectDashboard.getMemberList());
        filteredInventories = new FilteredList<>(this.projectDashboard.getInventoryList());
        filteredMappings = new FilteredList<>(this.projectDashboard.getMappingList());
    }

    public ModelManager() {
        this(new ProjectDashboard(), new UserPrefs());
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
        filteredTasksNotStarted.setPredicate(predicate);
        filteredTasksDoing.setPredicate(predicate);
        filteredTasksDone.setPredicate(predicate);
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
    public void addMapping(Mapping mapping) {
        projectDashboard.addMapping(mapping);
    }

    @Override
    public void deleteMapping(Mapping mapping) {
        projectDashboard.removeMapping(mapping);
    }

    @Override
    public boolean hasMapping(Mapping mapping) {
        requireNonNull(mapping);
        return projectDashboard.hasMapping(mapping);
    }

    public void replaceExistingMappingsWithNewMember(Member oldMember, Member newMember) {
        for (int i = 0; i < filteredMappings.size(); i++) {
            if (filteredMappings.get(i).getMember().equals(oldMember)) {
                Task taskInvolved = filteredMappings.get(i).getTask();
                filteredMappings.remove(filteredMappings.get(i));
                filteredMappings.add(new Mapping(newMember, taskInvolved));
            }
        }
    }

    public void replaceExistingMappingsWithNewTask(Task oldTask, Task newTask) {
        for (int i = 0; i < filteredMappings.size(); i++) {
            if (filteredMappings.get(i).getTask().equals(oldTask)) {
                Member memberInvolved = filteredMappings.get(i).getMember();
                filteredMappings.remove(filteredMappings.get(i));
                filteredMappings.add(new Mapping(memberInvolved, newTask));
            }
        }
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
}
