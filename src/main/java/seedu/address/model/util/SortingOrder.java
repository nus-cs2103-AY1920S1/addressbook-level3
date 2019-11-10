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
        setTaskCurrentIndex(1);
    }

    public static void setCurrentTaskSortingOrderByDate() {
        currentSortingOrderForTask = Comparator.comparing(task -> task.getTime().getDate());
        setTaskCurrentIndex(2);
    }

    public static void setCurrentTaskSortingOrderByDone() {
        currentSortingOrderForTask = (task1, task2) -> Boolean.compare(task1.isDone(), task2.isDone());
        setTaskCurrentIndex(3);
    }

    public static void setCurrentTaskSortingOrderByDoneThenDate() {
        currentSortingOrderForTask = (task1, task2) -> {
            if (task1.isDone() == task2.isDone()) {
                return task1.getTime().getDate().compareTo(task2.getTime().getDate());
            } else {
                return Boolean.compare(task1.isDone(), task2.isDone());
            }
        };
        setTaskCurrentIndex(5);
    }

    public static void setCurrentSpendingSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForSpending = (spending1, spending2) -> spending1.getDescription().compareToIgnoreCase(spending2.getDescription());
        setSpendingCurrentIndex(1);
    }

    public static void setCurrentSpendingSortingOrderByDate() {
        currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getTime().getDate());
        setSpendingCurrentIndex(2);
    }

    public static void setCurrentSpendingSortingOrderByExpense() {
        currentSortingOrderForSpending = Comparator.comparing(spending -> spending.getTime().getDate());
        setSpendingCurrentIndex(5);
    }

    public static void setCurrentMeetingSortingOrderByAlphabeticalOrder() {
        currentSortingOrderForMeeting = (meeting1, meeting2) -> meeting1.getDescription().description.compareToIgnoreCase(meeting2.getDescription().description);
        setMeetingCurrentIndex(1);
    }

    public static void setCurrentMeetingSortingOrderByDate() {
        currentSortingOrderForMeeting = Comparator.comparing(meeting -> meeting.getTime().getDate());
        setMeetingCurrentIndex(2);
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

    private static void setTaskCurrentIndex(int index) {
        assert index <= 4 && index >= 1 : "incorrect sorting order";
        taskCurrentIndex = index;
    }

    private static void setSpendingCurrentIndex(int index) {
        assert index == 1 || index == 2 || index == 5 : "incorrect sorting order";
        spendingCurrentIndex = index;
    }

    private static void setMeetingCurrentIndex(int index) {
        assert index == 1 || index == 2 : "incorrect sorting order";
        meetingCurrentIndex = index;
    }
}
