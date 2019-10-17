package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.execeptions.TaskNotFoundException;

/**
 * Represents a list of delivery tasks. The `TaskList` class supports a minimal set
 * of list operations. List sort and filter operations are provided.
 */
public class TaskList {

    private static int lastTaskId = 0;
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private final ObservableList<Task> tasksUnmodifiable = FXCollections.unmodifiableObservableList(tasks);

    public TaskList() {
    }

    public int getSize() {
        return tasks.size();
    }

    public Task getTask(int taskId) {
        Optional<Task> foundTask = tasks
                                    .stream()
                                    .filter(task -> task.getId() == taskId)
                                    .findFirst();
        if (foundTask.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return foundTask.get();
    }

    /**
     * Adds tasks into the task list.
     *
     * @param newTask task to be added.
     */
    public void addTask(Task newTask) {
        requireNonNull(newTask);
        tasks.add(newTask);

        lastTaskId++;
    }

    /**
     * Deletes the task from the task list.
     * @param taskToRemove task to be deleted.
     */
    public void deleteTask(Task taskToRemove) {
        requireNonNull(taskToRemove);
        if (!tasks.contains(taskToRemove)) {
            throw new TaskNotFoundException();
        }

        tasks.remove(taskToRemove);
    }

    /**
     * Updates the details of the task.
     *
     * @param updatedTask task to be updated.
     */
    public void updateTask(Task updatedTask) {
        requireNonNull(updatedTask);
        if (!tasks.contains(updatedTask)) {
            throw new TaskNotFoundException();
        }

        for (int i = 0; i < getSize(); i++) {
            Task task = getTask(i);
            if (task == updatedTask) {
                tasks.set(i, updatedTask);
                break;
            }
        }
    }

    public ObservableList<Task> getList() {
        return tasksUnmodifiable;
    }

    public List<Task> getSortedList(Comparator<Task> comparator) {
        return tasks
                    .stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
    }

    public List<Task> getFilteredList(Predicate<Task> predicate) {
        return tasks
                    .stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
    }

    public void setTaskList(List<Task> savedTasks) {
        tasks.setAll(savedTasks);
    }

    @Override
    public String toString() {
        StringBuilder strToPrint = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
            Task task = getTask(i);
            if (i == getSize() - 1) {
                //if this is the last task
                strToPrint.append(task);
            }

            strToPrint.append(task).append("\n");
        }

        return strToPrint.toString();
    }
}
