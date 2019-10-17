package seedu.address.model.task;

import javafx.collections.ObservableList;

/**
 * Manages the task list.
 * It contains the minimal set of list operations.
 */
public class TaskManager {

    private TaskList tasks;

    public TaskManager() {
        tasks = new TaskList();
    }

    public TaskManager(TaskList savedTasks) {
        this();
        tasks = savedTasks;
    }

    public void setTaskList(TaskList savedTasks) {
        tasks.setTaskList(savedTasks.getList());
    }

    public ObservableList<Task> getList() {
        return tasks.getList();
    }

    //task list operations

    public void addTask(Task newTask) {
        tasks.addTask(newTask);
    }

    public void deleteTask(Task taskToRemove) {
        tasks.deleteTask(taskToRemove);
    }

    public Task getTask(int taskId) {
        return tasks.getTask(taskId);
    }

    public void setTask(Task taskToUpdate) {
        tasks.setTask(taskToUpdate);
    }

    public boolean hasTask(Task task) {
        return tasks.hasTask(task);
    }

    public boolean hasTask(int taskId) {
        return tasks.hasTask(taskId);
    }
}
