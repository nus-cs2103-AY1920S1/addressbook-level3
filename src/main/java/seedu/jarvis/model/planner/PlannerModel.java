package seedu.jarvis.model.planner;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.planner.tasks.Task;


/**
 * The API of the PlannerModel component
 */
public interface PlannerModel {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;


    /**
     * Retrieves the tasks stored in the planner
     * @return a list of tasks stored in the planner
     */
    TaskList getTasks();

    /**
     * Adds a task to the planner
     * @param t Task to be added to the planner
     */
    void addTask(Task t);

    /**
     * Adds a {@code Task} at a given {@code Index}
     *
     * @param zeroBasedIndex Zero-based index to add {@code Task} to
     * @param task {@code Task} to be added
     */
    void addTask(int zeroBasedIndex, Task task);

    /**
     * Determines whether the planner contains the given task
     * @param t the task in question
     * @return true if the planner already contains the task, false if
     *         it does not.
     */
    boolean hasTask(Task t);

    /**
     * Retrieves the planner object
     * @return the planner object
     */
    Planner getPlanner();

    /**
     * Resets all commands in {@code executedCommands} and {@code inverselyExecutedCommands} to the commands in the
     * given {@code Planner}.
     *
     * @param planner {@code Planner} to take reference from.
     */
    void resetData(Planner planner);

    /**
     * Retrieves the task at the specified index
     *
     * @param index index of the task that is being retrieved
     * @return the task at the specified index
     */
    Task getTask(Index index);

    /**
     * Deletes the task at the specified index
     *
     * @param index index of the task to be deleted
     */
    void deleteTask(Index index);

    /**
     * Deletes the specified task from the planner
     * @param t the task to be deleted
     */
    void deleteTask(Task t);

    /**
     * Retrieves the size of the planner, i.e. the number of tasks in the planner
     * @return the size of the planner
     */
    int size();

    /**
     * Updates the {@code filteredTaskList} according to the given {@code Predicate}
     *
     * @param predicate {@code Predicate} to be applied to filter {@code filteredTaskList}
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list
     * of {@code Planner}
     */
    ObservableList<Task> getFilteredTaskList();

    //TODO jdocs
    ObservableList<Task> getUnfilteredTaskList();

    /**
     * Marks a {@code Task} at the specified {@code Index} as done
     * @param i {@code Index} of the {@code Task} to be marked as done
     */
    void markTaskAsDone(Index i);
}
