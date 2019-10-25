package seedu.address.model;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;

import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.model.item.EventList;

/**
 * Manages all the events that are to be rescheduled automatically at each of their given interval period.
 * Uses a Timer to keep track of when to update the Event's startDateTime.
 */
public class AutoRescheduleManager {
    private static AutoRescheduleManager manager;
    private static Timer timer;

    private AutoRescheduleManager() {}

    /**
     * The only way to get an AutoRescheduleManager object. There should only be one AutoRescheduleManager at any time.
     * @return the only instance of AutoRescheduleManager
     */
    public static AutoRescheduleManager getInstance() {
        if (manager == null) {
            manager = new AutoRescheduleManager();
            timer = new Timer();
        }
        return manager;
    }

    /**
     * Initialise this AutoRescheduleManager with all the events that can be rescheduled
     * Update event times to the latest upcoming one, given their period.
     * @param eventList of all events in the storage
     * @param model containing the events
     */
    public static void initStorageEvents(EventList eventList, ItemModel model) {
        for (Item item : eventList) {
            if (item.hasEvent()) {
                Event event = item.getEvent().get();
                if (event.hasAutoReschedule()) {
                    if (event.getStartDateTime().compareTo(LocalDateTime.now()) > 0) {
                        // event date is after now
                        // add event to Timer thread, add(newEvent)
                        RescheduleTask task = new RescheduleTask(item, event.getPeriod(), model);
                        getInstance().add(task);
                    } else {
                        // event date is before now, but is reschedulable
                        // modify the event date to the most upcoming one (Use modulo and add remainder)
                        // add(newEvent)
                        LocalDateTime updatedDateTime = getUpdatedDateTime(event);
                        Event updatedEvent = event.changeStartDateTime(updatedDateTime);

                        // update the old event time to the new one in the itemModel
                        Item oldItem = item;
                        Item newItem = item.changeEvent(updatedEvent);
                        RescheduleTask task = new RescheduleTask(newItem, updatedEvent.getPeriod(), model);
                        model.replaceItem(oldItem, newItem);

                        getInstance().add(task);
                    }

                }
            }
        }
    }

    /**
     * Update the old event start time to the closest upcoming start time, using the event's auto-reschedule period
     * @param event whose start date we want to modify
     * @return LocalDateTime representation of the modified time
     */
    private static LocalDateTime getUpdatedDateTime(Event event) {
        // Use modulo to get the remaining time till the next reschedule time. Add that remaining time to the time now.
        long period = event.getPeriod().getPeriod();
        LocalDateTime startDateTime = event.getStartDateTime();
        long millisDifference = Duration.between(startDateTime, LocalDateTime.now()).toMillis(); // positive difference;
        BigInteger periodBi = new BigInteger("" + period);
        BigInteger millisDifferenceBi = new BigInteger("" + millisDifference);

        long millisRemainder = millisDifferenceBi.mod(periodBi).longValue();
        long tillNextStart = period - millisRemainder;
        LocalDateTime updatedDateTime = LocalDateTime.now().plusNanos(Duration.ofMillis(tillNextStart).toNanos());

        return updatedDateTime;
    }

    /**
     * Adds a RescheduleTask that is to be rescheduled periodically, to this AutoRescheduleManager.
     * @param task RescheduleTask to be carried out after reaching an event's startDateTime
     */
    public void add(RescheduleTask task) {
        try {
            Duration delay = Duration.between(LocalDateTime.now(), task.getStartTime());
            timer.scheduleAtFixedRate(task, delay.toMillis(), task.getLongPeriod());
            System.out.println("Should only add once");
        } catch (Exception e) {
            System.out.println("Fail to add rescheduleTask: " + e.getMessage());
        }
    }

    /**
     * Shutdown the AutoRescheduleManager
     */
    public void shutdown() {
        timer.cancel();
    }

}
