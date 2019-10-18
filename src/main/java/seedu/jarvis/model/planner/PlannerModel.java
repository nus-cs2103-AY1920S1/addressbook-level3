package seedu.jarvis.model.planner;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * The API of the PlannerModel component
 */
public interface PlannerModel {

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
     * Checks if this planner is equal to another planner
     * @param other the other planner to be compared against
     * @return true if the planners are equal, false if they are not
     */
    boolean isEqual(Planner other);

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
     * Retrieves the size of the planner, i.e. the number of tasks in the planner
     * @return the size of the planner
     */
    int size();
}
