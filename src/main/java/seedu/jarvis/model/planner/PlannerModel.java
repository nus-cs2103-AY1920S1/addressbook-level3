package seedu.jarvis.model.planner;

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
    Boolean hasTask(Task t);

    /**
     * Retrieves the planner object
     * @return the planner object
     */
    Planner getPlanner();

}
