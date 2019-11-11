package seedu.address.model.task;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Driver;

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

    public void setTask(Task taskToEdit, Task editedTask) {
        tasks.setTask(taskToEdit, editedTask);
    }

    public boolean hasTask(Task task) {
        return tasks.hasTask(task);
    }

    public boolean hasTask(int taskId) {
        return tasks.hasTask(taskId);
    }

    public void setTaskList(TaskList taskList) {
        tasks.setTaskList(taskList.getList());
    }

    public static List<Task> getSortedList(List<Task> tasks, Comparator<Task> comparator) {
        return TaskList.getSortedList(tasks, comparator);
    }

    public static List<Task> getFilteredList(List<Task> tasks, Predicate<Task> predicate) {
        return TaskList.getFilteredList(tasks, predicate);
    }

    public static List<Task> getAssignedTasksOnDate(List<Task> tasks, LocalDate dateOfDelivery) {
        Predicate<Task> assignedTaskOnDatePredicate = task -> task.getDate().equals(dateOfDelivery)
                && !task.getStatus().equals(TaskStatus.INCOMPLETE);
        List<Task> assignedTaskOnDateList = getFilteredList(tasks, assignedTaskOnDatePredicate);

        return assignedTaskOnDateList;
    }

    /**
     * Gets assigned tasks sorted by eventTime.
     * NOTE: need to filter tasks list so that there is only assigned tasks with EventTime.
     */
    public static List<Task> getTasksSortedByEventTime(List<Task> tasks) {
        Comparator<Task> ascendingEventTimeComparator = Comparator.comparing(t -> {
            //uses filtered assigned tasks, so eventTime must be present
            assert t.getEventTime().isPresent();
            return t.getEventTime().get();
        });

        List<Task> sortedList = getSortedList(tasks, ascendingEventTimeComparator);

        return sortedList;
    }

    public static List<Task> getNotCompletedTasks(List<Task> tasks, LocalDate date) {
        Predicate<Task> notCompletedTasksOnDatePredicate = task -> task.getDate().equals(date)
                && !task.getStatus().equals(TaskStatus.COMPLETED);

        return getFilteredList(tasks, notCompletedTasksOnDatePredicate);
    }

    public static List<Driver> getDriversFromTasks(List<Task> assignedTasks) {
        return TaskList.getDriversFromTasks(assignedTasks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskManager otherObject = (TaskManager) o;
        return getList().equals(otherObject.getList());
    }
}
