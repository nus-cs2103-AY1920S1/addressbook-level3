package seedu.jarvis.model.planner;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.planner.predicates.TaskDesContainsKeywordsPredicate;
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
     * The task must not already exist in the planner
     * @param t the task to be added to the planner
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Adds a {@code Task} at a given {@code Index}
     *
     * @param zeroBasedIndex Zero-based index to add {@code Person} to
     * @param toAdd {@code Person} to be added.
     */
    public void add(int zeroBasedIndex, Task toAdd) {
        requireNonNull(toAdd);

        tasks.add(zeroBasedIndex, toAdd);
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

    /**
     * Retrieves a task from the taskList
     * @param index index of the task that is being retrieved
     * @return the task at that particular index
     */
    public Task getTask(Index index) {
        return tasks.get(index.getZeroBased());
    }

    /**
     * Retrieves the number of tasks in the taskList
     * @return the size of the taskList, i.e. the number of tasks in the taskList
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Removes the tasks at the specified index
     * @param index the index of that task that is being removed
     */
    public void deleteTask(Index index) {
        tasks.remove(index.getZeroBased());
    }

    /**
     * Deletes the specified task from the list of tasks
     * @param t the task to be deleted
     */
    public void deleteTask(Task t) {
        tasks.remove(t);
    }

    /**
     * Looks through all the tasks in the {@code TaskList} to find the tasks that
     * match the keywords in the predicate
     *
     * @param predicate contains a list of keywords
     * @return a {@code TaskList} of all the tasks in the planner that match
     * any of the given keywords
     */
    public TaskList find(TaskDesContainsKeywordsPredicate predicate) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task t : tasks) {
            if (predicate.test(t)) {
                filteredTasks.add(t);
            }
        }

        return new TaskList(filteredTasks);
    }

    /**
     * Marks a {@code Task} as done
     * @param i {@code Index} of the {@code Task} to be marked as done
     */
    public void markTaskAsDone(Index i) {
        Task t = tasks.get(i.getZeroBased());
        t.markAsDone();
    }

}
