package seedu.jarvis.model.planner;

import java.util.ArrayList;

/**
 * Represents a list of tasks in the planner
 */
public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Retrieves all the tasks in the planner
     * @return a list of tasks in the planner
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the task list
     * @param t the task to be added to the planner
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Determines whether there is a duplicate task in the planner
     * @param t the task in question
     * @return true if Task t already exists in the planner, and false if it does not
     */
    public Boolean hasTask(Task t) {
        return tasks.contains(t);
    }
}
