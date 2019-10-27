package seedu.address.model.util;

import seedu.address.model.project.Task;

import java.util.Comparator;

/**
 * Container class to store the current and different types of sorting orders.
 */

public class SortingOrder {

    private static Comparator<Task> currentSortingOrderForTask = Comparator.comparing(task -> task.getTime().getDate());

    public static void setCurrentSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForTask = Comparator.comparing(task -> task.getDescription().description);
    }

    public static void setCurrentSortingOrderByDate() {
        currentSortingOrderForTask = Comparator.comparing(task -> task.getTime().getDate());
    }

    public static void setCurrentSortingOrderByDone() {
        currentSortingOrderForTask = (task1, task2) -> Boolean.compare(task1.isDone(), task2.isDone());
    }

    public static void setCurrentSortingOrderByDoneThenDate() {
        currentSortingOrderForTask = (task1, task2) -> {
            if (task1.isDone() == task2.isDone()) {
                return task1.getTime().getDate().compareTo(task2.getTime().getDate());
            } else {
                return Boolean.compare(task1.isDone(), task2.isDone());
            }
        };
    }

    public static Comparator<Task> getCurrentSortingOrderForTask() {
        return currentSortingOrderForTask;
    }
}
