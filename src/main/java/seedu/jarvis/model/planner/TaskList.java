package seedu.jarvis.model.planner;

import java.util.ArrayList;

import seedu.jarvis.model.planner.tasks.Task;

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
    public boolean hasTask(Task t) {
        for (Task task : tasks) {
            if (task.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if one TaskList is equal to the other TaskList
     * @param other the TaskList to be compared to
     * @return true if both TaskLists are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskList)) {
            return false;
        }

        ArrayList<Task> otherTasks = ((TaskList) other).getTasks();
        if (otherTasks.size() != tasks.size()) {
            return false;
        }

        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).equals(otherTasks.get(i))) {
                return false;
            }
        }

        return true;
    }

}
