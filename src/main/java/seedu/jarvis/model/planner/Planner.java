package seedu.jarvis.model.planner;

import static java.util.Objects.requireNonNull;

/**
 * Represents the planner feature in JARVIS
 */
public class Planner {
    protected TaskList taskList;

    /**
     * Constructs an empty planner
     */
    public Planner() {
        this.taskList = new TaskList();
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
    public TaskList getTasks() {
        return taskList;
    }

    /**
     * Adds a task to the planner
     * @param t the task to be added
     */
    public void addTask(Task t) {
        taskList.add(t);
    }

    /**
     * Determines whether the planner contains the given task
     * @param t the task in question
     * @return true if the planner already contains the task, false if
     *         it does not.
     */
    public Boolean hasTask(Task t) {
        return taskList.hasTask(t);
    }

    /**
     * Resets all commands in {@code executedCommands} and {@code inverselyExecutedCommands} to the commands in the
     * given {@code Planner}.
     *
     * @param planner {@code Planner} to take reference from.
     */
    public void resetData(Planner planner) {
        requireNonNull(planner);
        this.taskList = new TaskList(planner.getTasks().getTasks());
    }
}
