package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;
import seedu.address.model.member.Member;
import seedu.address.model.mapping.Mapping;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_INVENTORIES = unused -> true;


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

    /** Returns the ProjectDashboard */
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

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskListByDeadline();

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskListNotStarted();

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskListDoing();

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskListDone();

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTasksList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTasksList(Predicate<Task> predicate);

    /// Member
    /**
     * Returns true if a member with the same identity as {@code member} exists in the address book.
     */
    boolean hasMember(Member member);

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

    /** Returns an unmodifiable view of the filtered member list */
    ObservableList<Member> getFilteredMembersList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMembersList(Predicate<Member> predicate);

    /**
     * returns length of filteredList
     */
    int getTasksLength();

    /// Inventory

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Inventory> getFilteredInventoriesList();

    /**
     * Updates the filter of the filtered inventories list to filter by the given {@code predicate}.
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

    void addMapping(Mapping mapping);

    void deleteMapping(Mapping mapping);

    boolean hasMapping(Mapping mapping);

    ObservableList<Mapping> getFilteredMappingsList();

    void updateFilteredMappingsList(Predicate<Mapping> predicate);

    void replaceExistingMappingsWithNewMember(Member oldMember, Member newMember);

    void replaceExistingMappingsWithNewTask(Task oldTask, Task newTask);

}
