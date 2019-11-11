package seedu.moolah.model;

import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.moolah.logic.Logic;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Reminder;
import seedu.moolah.model.expense.Timestamp;

/**
 * Handles all comparisons between system time and the time fields of Expenses, Events and Budgets.
 */
public class Timekeeper {
    public static final long UPPER_THRESHOLD = 7;
    private static Timestamp systemTime = new Timestamp(LocalDateTime.now());
    private Logic logic;
    private ObservableList<Event> events;
    private List<Reminder> reminders = new ArrayList<>();
    private ObservableList<Budget> budgets;

    public Timekeeper(Logic logic) {
        requireAllNonNull(logic);
        this.logic = logic;
        events = logic.getFilteredEventList();
        budgets = logic.getMooLah().getBudgetList();
    }

    /**
     * Checks the system's time and updates the app's system time field to it.
     */
    public void updateTime() {
        systemTime = new Timestamp(LocalDateTime.now());
        if (isAtDayBreak(systemTime)) {
            refreshBudgets();
        }
    }

    private boolean isAtDayBreak(Timestamp timeNow) {
        LocalDate dateNow = timeNow.getDate();
        LocalDate dateJustNow = timeNow.getDateJustNow();
        return !dateNow.equals(dateJustNow);
    }

    /**
     * Converts the Date object returned by Natty into a LocalDateTime object with 0 in both its
     * seconds and nanoseconds field.
     *
     * @param dateToConvert The Date object returned by Natty's parser.
     * @return The LocalDateTime object to be wrapped in a Timestamp.
     */
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .withSecond(0)
                .withNano(0);
    }

    /**
     * Removes events with timestamps on this current day or before this current day. Returns these transpired events.
     *
     * @return A list of transpired events.
     */
    public List<Event> getTranspiredEvents() {
        List<Event> eventsToNotify = new ArrayList<>();

        for (Event event : events) {
            Timestamp timestamp = event.getTimestamp();
            if (hasTranspired(timestamp) && logic.hasBudgetWithName(event.getBudgetName())) {
                eventsToNotify.add(event);
            }
        }

        return eventsToNotify;
    }

    /**
     * Creates then formats a list of reminders into a readable format.
     *
     * @return The String of reminders.
     */
    public String displayReminders() {
        for (Event event : events) {
            Optional<Reminder> potentialReminder = createReminderIfValid(event);
            if (potentialReminder.isPresent()) {
                reminders.add(potentialReminder.get());
            }
        }

        StringBuilder remindersMessage =
                new StringBuilder((reminders.isEmpty()
                        ? "You have no upcoming events!"
                        : "These are your upcoming events:"));
        for (Reminder reminder: reminders) {
            remindersMessage.append("\n" + reminder.toString());
        }

        return remindersMessage.toString();
    }

    /**
     * Refreshes all budgets (except default budget) in MooLah.
     */
    public void refreshBudgets() {
        for (Budget budget : budgets) {
            if (!budget.isDefaultBudget()) {
                budget.refresh();
            }
        }
    }

    /**
     * Given an Event, create an Optional Reminder which will contain a Reminder
     * if the Event is upcoming.
     *
     * @param event The Event which a Reminder may be made from.
     * @return An Optional Reminder which may contain a Reminder of the Event.
     */
    private static Optional<Reminder> createReminderIfValid(Event event) {
        Timestamp timestamp = event.getTimestamp();
        long daysLeft = calculateDaysRemaining(timestamp);
        return (isUrgent(timestamp)) ? Optional.of(new Reminder(event, daysLeft)) : Optional.empty();
    }

    //    /**
    //     * Calculates how many days outdated the given timestamp is.
    //     *
    //     * @param timestamp The given timestamp.
    //     * @return How many days outdated the given timestamp is. Can be negative.
    //     */
    //    public static String formatTimeOutdated(Timestamp timestamp) {
    //        LocalDateTime temp = systemTime.fullTimestamp;
    //
    //        long daysLeft = temp.until(timestamp.fullTimestamp, ChronoUnit.DAYS);
    //        temp = temp.plusDays(daysLeft);
    //
    //        long hoursLeft = temp.until(timestamp.fullTimestamp, ChronoUnit.HOURS);
    //        temp = temp.plusHours(hoursLeft);
    //
    //        long minutesLeft = temp.until(timestamp.fullTimestamp, ChronoUnit.MINUTES);
    //
    //        String timeRemaining = String.format("%d days, %d hours and %d minutes",
    //                Math.abs(daysLeft), Math.abs(hoursLeft), Math.abs(minutesLeft));
    //
    //        return timeRemaining;
    //    }

    private static long calculateDaysRemaining(Timestamp timestamp) {
        long daysLeft = systemTime.getFullTimestamp().until(timestamp.getFullTimestamp(), ChronoUnit.DAYS);
        return daysLeft;
    }

    private static boolean isUrgent(Timestamp timestamp) {
        long daysLeft = calculateDaysRemaining(timestamp);
        return daysLeft < UPPER_THRESHOLD && !hasTranspired(timestamp);
    }

    static boolean hasTranspired(Timestamp timestamp) {
        return timestamp.isBefore(systemTime);
    }

    public static boolean isFutureTimestamp(Timestamp timestamp) {
        return timestamp.isAfter(systemTime);
    }
}
