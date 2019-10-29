package seedu.address.model.util;

import seedu.address.model.finance.Spending;
import seedu.address.model.person.Person;
import seedu.address.model.project.Task;

import java.util.Comparator;

/**
 * Container class to store the current and different types of sorting orders.
 */

public class SortingOrder {

    private static Comparator<Task> currentSortingOrderForTask = Comparator.comparing(task -> task.getTime().getDate());
    private static Comparator<String> currentSortingOrderForMember = Comparator.comparing(String::toString);
    private static Comparator<Person> currentSortingOrderForPerson = Comparator.comparing(person -> person.getName().fullName);
    private static Comparator<Spending> currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getDate());

    public static void setCurrentTaskSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForTask = (task1, task2) -> task1.getDescription().description
                .compareToIgnoreCase(task2.getDescription().description);
    }

    public static void setCurrentTaskSortingOrderByDate() {
        currentSortingOrderForTask = Comparator.comparing(task -> task.getTime().getDate());
    }

    public static void setCurrentTaskSortingOrderByDone() {
        currentSortingOrderForTask = (task1, task2) -> Boolean.compare(task1.isDone(), task2.isDone());
    }

    public static void setCurrentTaskSortingOrderByDoneThenDate() {
        currentSortingOrderForTask = (task1, task2) -> {
            if (task1.isDone() == task2.isDone()) {
                return task1.getTime().getDate().compareTo(task2.getTime().getDate());
            } else {
                return Boolean.compare(task1.isDone(), task2.isDone());
            }
        };
    }

    public static void setCurrentSpendingSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForSpending = (spending1, spending2) -> spending1.getDescription().compareToIgnoreCase(spending2.getDescription());
    }

    public static void setCurrentSpendingSortingOrderByDate() {
        currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getDate());
    }

    public static void setCurrentSpendingSortingOrderByExpense() {
        currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getSpending());
    }


    public static Comparator<Task> getCurrentSortingOrderForTask() {
        return currentSortingOrderForTask;
    }

    public static Comparator<String> getCurrentSortingOrderForMember() {
        return currentSortingOrderForMember;
    }

    public static Comparator<Person> getCurrentSortingOrderForPerson() {
        return currentSortingOrderForPerson;
    }

    public static Comparator<Spending> getCurrentSortingOrderForSpending() {
        return currentSortingOrderForSpending;
    }
}
