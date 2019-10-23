package seedu.address.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Reminder;
import seedu.address.model.expense.Timestamp;

/**
 * Handles all comparisons between system time and the time fields of Expenses, Events and Budgets.
 */
public class Timekeeper {
    public static final Timestamp SYSTEM_DATE = new Timestamp(LocalDate.now());
    // public static final Timestamp SYSTEM_DATE = Timestamp.createTimestampIfValid("11-11").get();
    public static final long LOWER_THRESHOLD = 0;
    public static final long UPPER_THRESHOLD = 7;
    private Model model;
    private ObservableList<Event> events;
    private List<Reminder> reminders = new ArrayList<>();
    private ObservableList<Budget> budgets;

    public Timekeeper(Model model) {
        this.model = model;
        events = model.getFilteredEventList();
        budgets = model.getAddressBook().getBudgetList();
        getReminders();
    }

    /**
     * Removes events with timestamps on this current day or before this current day. Returns these transpired events.
     *
     * @return A list of transpired events.
     */
    public List<Event> getTranspiredEvents() {
        List<Event> eventsToNotify = new ArrayList<>();
        List<Event> eventsToBeRemoved = new ArrayList<>();

        for (Event event : events) {
            Timestamp timestamp = event.getTimestamp();
            if (hasTranspired(timestamp)) {
                eventsToNotify.add(event);
                eventsToBeRemoved.add(event);
            }
        }

        for (Event event : eventsToBeRemoved) {
            model.deleteEvent(event);
        }

        return eventsToNotify;
    }

    /**
     * Creates Reminders of upcoming Events.
     */
    public void getReminders() {
        for (Event event : events) {
            Optional<Reminder> potentialReminder = createReminderIfValid(event);
            if (potentialReminder.isPresent()) {
                reminders.add(potentialReminder.get());
            }
        }
    }

    /**
     * Formats the list of reminders into a readable format.
     *
     * @return The String of reminders.
     */
    public String displayReminders() {
        StringBuilder remindersMessage = new StringBuilder("These are your upcoming events:");
        for (Reminder reminder: reminders) {
            remindersMessage.append("\n" + reminder.toString());
        }

        return remindersMessage.toString();
    }

    /**
     * Dummy.
     *
     */
    public void refreshBudgets() {
        for (Budget budget : budgets) {
            if (budget.expired(SYSTEM_DATE)) {
                budget.refresh(SYSTEM_DATE);
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

    /**
     * Calculates how many days outdated the given timestamp is.
     *
     * @param timestamp The given timestamp.
     * @return How many days outdated the given timestamp is. Can be negative.
     */
    public static long calculateDaysOutdated(Timestamp timestamp) {
        long daysOutdated = -SYSTEM_DATE.getTimestamp().until(timestamp.getTimestamp(), ChronoUnit.DAYS);
        return daysOutdated;
    }

    private static long calculateDaysRemaining(Timestamp timestamp) {
        long daysLeft = SYSTEM_DATE.getTimestamp().until(timestamp.getTimestamp(), ChronoUnit.DAYS);
        return daysLeft;
    }

    private static boolean isUrgent(Timestamp timestamp) {
        long daysLeft = calculateDaysRemaining(timestamp);
        return daysLeft < UPPER_THRESHOLD && !hasTranspired(timestamp);
    }

    private static boolean hasTranspired(Timestamp timestamp) {
        long daysOutdated = calculateDaysOutdated(timestamp);
        return daysOutdated >= LOWER_THRESHOLD;
    }

    public static boolean isFutureTimestamp(Timestamp timestamp) {
        return timestamp.isAfter(SYSTEM_DATE);
    }
}
