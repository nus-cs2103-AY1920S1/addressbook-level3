package seedu.address.model.util;

import seedu.address.model.finance.Spending;
import seedu.address.model.person.Person;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Task;

import java.util.Comparator;

/**
 * Container class to store the current and different types of sorting orders.
 */

public class SortingOrder {

    private static Comparator<Task> currentSortingOrderForTask = Comparator.comparing(task -> task.getTime().getDate());
    private static Comparator<String> currentSortingOrderForMember = (member1, member2) -> member1.compareToIgnoreCase(member2);
    private static Comparator<Person> currentSortingOrderForPerson = Comparator.comparing(person -> person.getName().fullName);
    private static Comparator<Spending> currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getTime().getDate());
    private static Comparator<Meeting> currentSortingOrderForMeeting = Comparator.comparing(meeting -> meeting.getTime().getDate());
    private static int taskCurrentIndex = 2;
    private static int spendingCurrentIndex = 2;
    private static int meetingCurrentIndex = 2;

    public static void setCurrentTaskSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForTask = (task1, task2) -> task1.getDescription().description
                .compareToIgnoreCase(task2.getDescription().description);
        taskCurrentIndex = 1;
    }

    public static void setCurrentTaskSortingOrderByDate() {
        currentSortingOrderForTask = Comparator.comparing(task -> task.getTime().getDate());
        taskCurrentIndex = 2;
    }

    public static void setCurrentTaskSortingOrderByDone() {
        currentSortingOrderForTask = (task1, task2) -> Boolean.compare(task1.isDone(), task2.isDone());
        taskCurrentIndex = 3;
    }

    public static void setCurrentTaskSortingOrderByDoneThenDate() {
        currentSortingOrderForTask = (task1, task2) -> {
            if (task1.isDone() == task2.isDone()) {
                return task1.getTime().getDate().compareTo(task2.getTime().getDate());
            } else {
                return Boolean.compare(task1.isDone(), task2.isDone());
            }
        };
        taskCurrentIndex = 4;
    }

    public static void setCurrentSpendingSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForSpending = (spending1, spending2) -> spending1.getDescription().compareToIgnoreCase(spending2.getDescription());
        spendingCurrentIndex = 1;
    }

    public static void setCurrentSpendingSortingOrderByDate() {
        currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getTime().getDate());
        spendingCurrentIndex = 2;
    }

    public static void setCurrentSpendingSortingOrderByExpense() {
        currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getTime().getDate());
        spendingCurrentIndex = 5;
    }

    public static void setCurrentMeetingSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForMeeting = (meeting1, meeting2) -> meeting1.getDescription().description.compareToIgnoreCase(meeting2.getDescription().description);
        meetingCurrentIndex = 1;
    }

    public static void setCurrentMeetingSortingOrderByDate() {
        currentSortingOrderForMeeting = Comparator.comparing(meeting -> meeting.getTime().getDate());
        meetingCurrentIndex = 2;
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

    public static Comparator<Meeting> getCurrentSortingOrderForMeeting() {
        return currentSortingOrderForMeeting;
    }

    public static int getTaskCurrentIndex() {
        return taskCurrentIndex;
    }

    public static int getSpendingCurrentIndex() {
        return spendingCurrentIndex;
    }

    public static int getMeetingCurrentIndex() {
        return meetingCurrentIndex;
    }
}
