package seedu.address.model.calendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.DateTime;
import seedu.address.model.Model;

/**
 * A class representing a scheduler.
 */
public class Scheduler {
    private static int threads = 10;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(threads);
    private LocalDateTime currentDeadline;
    private LocalDateTime currentStartingDateTime;
    private Queue<ScheduledFuture> scheduledFutures;

    public Scheduler() {
        currentDeadline = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
        currentStartingDateTime = LocalDateTime.now();
        scheduledFutures = new LinkedList<>();
    }

    /**
     * Stops all upcoming reminders and the scheduler.
     */
    public void stopAll() {
        cancelAll();
        scheduler.shutdownNow();
    }

    /**
     * Cancels all upcoming reminders.
     */
    private void cancelAll() {
        for (ScheduledFuture scheduledFuture: scheduledFutures) {
            if (!scheduledFuture.isDone()) {
                scheduledFuture.cancel(true);
            }
        }
    }

    /**
     * Schedules reminders according to the current model.
     */
    public void schedule(Model model) {
        Map<LocalTime, List<Reminder>> dateTimeMap = getDateTimeMapToReminders(model.getFilteredCalendarEntryList());

        cancelAll();
        for (Map.Entry<LocalTime, List<Reminder>> entry: dateTimeMap.entrySet()) {
            scheduledFutures.add(scheduler.schedule(new ReminderAdder(entry.getValue(), model),
                    getDuration(entry.getKey()), TimeUnit.MILLISECONDS));
        }
        scheduler.schedule(new Initializer(model),
                getDuration(currentDeadline.plusMinutes(1)), TimeUnit.MILLISECONDS);
    }

    /**
     * Returns a Map mapping from DateTime to a list of reminders at the certain date time;
     */
    private TreeMap<LocalTime, List<Reminder>> getDateTimeMapToReminders(
            ObservableList<CalendarEntry> calendarEntries) {
        currentStartingDateTime = LocalDateTime.now();

        return calendarEntries
                .stream()
                .filter(calendarEntry -> calendarEntry instanceof Reminder)
                .map(calendarEntry -> (Reminder) calendarEntry)
                .filter(reminder -> reminder
                        .isBetween(new DateTime(currentStartingDateTime), new DateTime(currentDeadline)))
                .collect(Collectors.groupingBy(Reminder::getTime, TreeMap::new, Collectors.toList()));
    }

    /**
     * Returns the duration between the current starting time and the target time.
     */
    private long getDuration(LocalTime targetTime) {
        return Duration.between(currentStartingDateTime.toLocalTime(), targetTime).toMillis();
    }

    /**
     * Returns the duration between the current starting time and the target time.
     */
    private long getDuration(LocalDateTime targetDateTime) {
        return Duration.between(currentStartingDateTime, targetDateTime).toMillis();
    }

    /**
     * A class implementing Runnable interface for adding reminders.
     */
    private class ReminderAdder implements Runnable {
        private List<Reminder> reminders;
        private Model model;

        public ReminderAdder(List<Reminder> reminders, Model model) {
            this.reminders = reminders;
            this.model = model;
        }

        @Override
        public void run() {
            for (Reminder reminder: reminders) {
                model.addPastReminder(reminder);
            }
        }
    }

    /**
     * A class implementing Runnable interface for initializing the scheduler.
     */
    private class Initializer implements Runnable {
        private Model model;

        public Initializer(Model model) {
            this.model = model;
        }

        @Override
        public void run() {
            currentDeadline = currentDeadline.plusDays(1);
            schedule(model);
        }
    }
}
