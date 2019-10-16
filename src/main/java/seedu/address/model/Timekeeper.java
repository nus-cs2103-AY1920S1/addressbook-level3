package seedu.address.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.expense.Event;
import seedu.address.model.expense.Reminder;

/**
 * Handles all comparisons between system time and the time fields of Expenses, Events and Budgets.
 */
public class Timekeeper {
    public static final LocalDate SYSTEM_DATE = LocalDate.now();
    public static final long LOWER_THRESHOLD = 0;
    public static final long UPPER_THRESHOLD = 7;
    private Model model;
    private ObservableList<Event> events;
    private List<Reminder> reminders = new ArrayList<>();

    public Timekeeper(Model model) {
        this.model = model;
        events = model.getFilteredEventList();
        filterOutdatedEvents();
        getReminders();
    }

    /**
     * Removes events that are already past their due date from the list of events in Moolah.
     */
    private void filterOutdatedEvents() {
        List<Event> toBeRemoved = new ArrayList<>();
        for (Event event : events) {
            LocalDate timestamp = event.getTimestamp().timestamp;
            long daysLeft = SYSTEM_DATE.until(timestamp, ChronoUnit.DAYS);
            if (isOutdated(daysLeft)) {
                toBeRemoved.add(event);
            }
        }

        for (Event outdatedEvent : toBeRemoved) {
            model.deleteEvent(outdatedEvent);
        }
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
     * Given an Event, create an Optional Reminder which will contain a Reminder
     * if the Event is upcoming.
     *
     * @param event The Event which a Reminder may be made from.
     * @return An Optional Reminder which may contain a Reminder of the Event.
     */
    private static Optional<Reminder> createReminderIfValid(Event event) {
        LocalDate timestamp = event.getTimestamp().timestamp;

        long daysLeft = SYSTEM_DATE.until(timestamp, ChronoUnit.DAYS);

        return (isUrgent(daysLeft)) ? Optional.of(new Reminder(event, daysLeft)) : Optional.empty();
    }

    private static boolean isUrgent(long daysLeft) {
        return daysLeft < UPPER_THRESHOLD;
    }

    private static boolean isOutdated(long daysLeft) {
        return daysLeft < LOWER_THRESHOLD;
    }
}
