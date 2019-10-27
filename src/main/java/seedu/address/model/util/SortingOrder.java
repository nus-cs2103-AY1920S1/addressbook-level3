package seedu.address.model.util;

import seedu.address.model.project.Task;

import java.util.Comparator;

/**
 * Container class to store the current and different types of sorting orders.
 */

public class SortingOrder {

    public static Comparator<Task> CURRENT_SORTING_ORDER_FOR_TASK = Comparator.comparing(task -> task.getTime().getDate());

    public static void setCurrentSortingOrderByAlphabeticalOrder() {
        CURRENT_SORTING_ORDER_FOR_TASK = Comparator.comparing(task -> task.getDescription().description);
    }

    public static void setCurrentSortingOrderByDate() {
        CURRENT_SORTING_ORDER_FOR_TASK = Comparator.comparing(task -> task.getTime().getDate());
    }

    public static void setCurrentSortingOrderByDone() {
        CURRENT_SORTING_ORDER_FOR_TASK = (task1, task2) -> Boolean.compare(task1.isDone(), task2.isDone());
    }

    public static void setCurrentSortingOrderByDoneThenDate() {
        CURRENT_SORTING_ORDER_FOR_TASK = (task1, task2) -> {
            if (task1.isDone() == task2.isDone()) {
                return task1.getTime().getDate().compareTo(task2.getTime().getDate());
            } else {
                return Boolean.compare(task1.isDone(), task2.isDone());
            }
        };
    }
}
