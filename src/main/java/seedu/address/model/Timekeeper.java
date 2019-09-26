package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Reminder;
import seedu.address.model.expense.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Timekeeper {
    public static final LocalDate SYSTEM_DATE = LocalDate.now();
    public static final long THRESHOLD = 7;
    public ObservableList<Event> events;

    public Timekeeper(Model model) {
        events = model.getFilteredEventList();
    }

    public List<Reminder> getReminders() {
        List<Reminder> reminders = new ArrayList<>();
        for (Event event : events) {
            Optional<Reminder> potentialReminder = createReminderIfValid(event);
            if (potentialReminder.isPresent()) {
                reminders.add(potentialReminder.get());
            }
        }
        return reminders;
    }

    /**
     * Calculates the time left before the task has to be completed.
     *
     * @param timestamp The timestamp of the timed task.
     */
    private static Optional<Reminder> createReminderIfValid(Event event) {
        LocalDate timestamp = event.getTimestamp().timestamp;

        long daysLeft = SYSTEM_DATE.until(timestamp, ChronoUnit.DAYS);
        String totalTimeDifference = String.format("%d days", daysLeft);

        return (isUrgent(daysLeft)) ? Optional.of(new Reminder(event, totalTimeDifference)) : Optional.empty();
    }

    private static boolean isUrgent(long daysLeft) {
        return daysLeft < THRESHOLD;
    }
}
