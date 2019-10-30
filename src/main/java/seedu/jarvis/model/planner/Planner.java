package seedu.jarvis.model.planner;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.planner.tasks.Task;


/**
 * Represents the planner feature in JARVIS
 */
public class Planner {
    private TaskList taskList;
    private FilteredList<Task> filteredTaskList;

    /**
     * Constructs an empty planner
     */
    public Planner() {
        this.taskList = new TaskList();
        filteredTaskList = new FilteredList<>(FXCollections.observableList(getTasks()),
                                                PlannerModel.PREDICATE_SHOW_ALL_TASKS);
    }

    /**
     * Constructs a Planner with reference from another Planner,
     * adding its commands to this Planner.
     *
     * @param planner {@code Planner} whose data this {@code Planner} is taking reference from.
     */
    public Planner(Planner planner) {
        this();
        resetData(planner);
    }

    /**
     * Retrieves all the tasks in the planner
     * @return a list of tasks stored in the planner
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Returns the task list {@code TaskList}.
     *
     * @return the task list.
     */
    public ArrayList<Task> getTasks() {
        return taskList.getTasks();
    }

    /**
     * Adds a task to the planner
     * @param t the task to be added
     */
    public void addTask(Task t) {
        taskList.add(t);
    }

    /**
     * Adds a {@code Task} at a given {@code Index}
     *
     * @param zeroBasedIndex Zero-based index to add {@code Task} to
     * @param task {@code Task} to be added
     */
    public void addTask(int zeroBasedIndex, Task task) {

        taskList.add(zeroBasedIndex, task);
    }

    /**
     * Determines whether the planner contains the given task
     * @param t the task in question
     * @return true if the planner already contains the task, false if
     *         it does not.
     */
    public boolean hasTask(Task t) {
        return taskList.hasTask(t);
    }

    /**
     * Resets all tasks to the tasks in the given {@code Planner}.
     *
     * @param planner {@code Planner} to take reference from.
     */
    public void resetData(Planner planner) {
        requireNonNull(planner);
        this.taskList = new TaskList(planner.getTaskList().getTasks());
    }

    /**
     * Checks if one planner is equal to the other
     * @param other the planner to be compared against
     * @return true if both planners are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if it is the same object.
        if (other == this) {
            return true;
        }

        // instanceof handles nulls.
        if (!(other instanceof Planner)) {
            return false;
        }

        //state check
        return taskList.equals(((Planner) other).getTaskList());
    }

    /**
     * Retrieves the task at the specified index
     *
     * @param index index of the task that is being retrieved
     * @return the task at the specified index
     */
    public Task getTask(Index index) {
        return taskList.getTask(index);
    }

    /**
     * Deletes the task at the specified index
     *
     * @param index index of the task to be deleted
     */
    public void deleteTask(Index index) {
        taskList.deleteTask(index);
    }

    /**
     * Deletes the specified task from the planner
     *
     * @param t the task to be deleted
     */
    public void deleteTask(Task t) {
        taskList.deleteTask(t);
    }

    /**
     * Retrieves the size of the planner
     * @return the size of the planner, i.e. the number of tasks in the planner
     */
    public int size() {
        return taskList.size();
    }

    /**
     * Updates the {@code filteredTaskList} according to the given {@code Predicate}
     *
     * @param predicate {@code Predicate} to be applied to filter {@code filteredTaskList}
     */
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTaskList = new FilteredList<>(FXCollections.observableList(getTasks()),
                PlannerModel.PREDICATE_SHOW_ALL_TASKS);
        filteredTaskList.setPredicate(predicate);

    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list
     * of {@code Planner}
     */
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTaskList;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code Planner}
     * @return a list of all the {@code Task} in the {@code Planner}
     */
    public ObservableList<Task> getUnfilteredTaskList() {
        return FXCollections.observableList(getTasks());
    }

    /**
     * Marks a {@code Task} at the specified {@code Index} as done
     * @param i {@code Index} of the {@code Task} to be marked as done
     */
    public void markTaskAsDone(Index i) {
        taskList.markTaskAsDone(i);
    }
}
