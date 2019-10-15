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
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
//import seedu.address.model.task.NameContainsKeywordsPredicate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProjectDashboard projectDashboard;
    private final UserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Member> filteredMembers;

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
        filteredMembers = new FilteredList<>(this.projectDashboard.getMemberList());
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

    //=========== Filtered Task List Accessors =============================================================

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

}
