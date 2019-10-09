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
        setTaskList(savedTasks);
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

    public void updateTask(Task taskToUpdate) {
        tasks.updateTask(taskToUpdate);
    }
}
