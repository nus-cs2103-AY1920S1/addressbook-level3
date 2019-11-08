package seedu.sugarmummy.model.calendar;

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
import seedu.sugarmummy.model.time.DateTime;
import seedu.sugarmummy.model.time.Today;

/**
 * A class representing a scheduler.
 */
public class Scheduler {
    private static int threads = 5;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(threads);
    private LocalDateTime currentDeadline;
    private LocalDateTime currentStartingDateTime;
    private Queue<ScheduledFuture> scheduledFutures;
    private Today today;
    private final LocalDateTime appStartingDateTime;
    private boolean isFirstScheduling;

    public Scheduler() {
        appStartingDateTime = LocalDateTime.now().withSecond(0).withNano(0).minusMinutes(1);
        currentDeadline = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
        currentStartingDateTime = appStartingDateTime.plusMinutes(1);
        scheduledFutures = new LinkedList<>();
        today = new Today();
        isFirstScheduling = true;
    }

    public LocalDateTime getAppStartingDateTime() {
        return appStartingDateTime;
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
        for (ScheduledFuture scheduledFuture : scheduledFutures) {
            if (!scheduledFuture.isDone()) {
                scheduledFuture.cancel(true);
            }
        }
    }

    /**
     * Returns a Today Object representing the date of today.
     */
    public Today getToday() {
        return today;
    }

    /**
     * Returns a LocalDate Object representing the date of today.
     */
    public LocalDate getTodayDate() {
        return today.getDate();
    }

    /**
     * Schedules reminders according to the current model.
     */
    public void schedule(Calendar calendar) {
        Map<LocalTime, List<Reminder>> dateTimeMap = getDateTimeMapToReminders(calendar.getCalendarEntryList());
        if (isFirstScheduling) {
            isFirstScheduling = false;
        } else {
            cancelAll();
        }
        currentStartingDateTime = LocalDateTime.now();
        for (Map.Entry<LocalTime, List<Reminder>> entry : dateTimeMap.entrySet()) {
            scheduledFutures.add(scheduler.schedule(new ReminderAdder(entry.getValue(), calendar),
                    getDuration(entry.getKey()), TimeUnit.MILLISECONDS));
        }
        scheduler.schedule(new Initializer(calendar),
                getDuration(currentDeadline.plusMinutes(1)), TimeUnit.MILLISECONDS);
    }

    /**
     * Returns a Map mapping from DateTime to a list of reminders at the certain date time;
     */
    private TreeMap<LocalTime, List<Reminder>> getDateTimeMapToReminders(
            ObservableList<CalendarEntry> calendarEntries) {
        if (!isFirstScheduling) {
            currentStartingDateTime = LocalDateTime.now().withSecond(0).withNano(0);
        }

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
        private Calendar calendar;

        public ReminderAdder(List<Reminder> reminders, Calendar calendar) {
            this.reminders = reminders;
            this.calendar = calendar;
        }

        @Override
        public void run() {
            calendar.addPastReminders(reminders);
        }
    }

    /**
     * A class implementing Runnable interface for initializing the scheduler.
     */
    private class Initializer implements Runnable {
        private Calendar calendar;

        public Initializer(Calendar calendar) {
            this.calendar = calendar;
        }

        @Override
        public void run() {
            currentDeadline = currentDeadline.plusDays(1);
            schedule(calendar);
            today.refresh();
        }
    }
}
