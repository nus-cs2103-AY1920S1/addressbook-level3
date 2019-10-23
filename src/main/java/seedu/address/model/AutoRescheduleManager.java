package seedu.address.model;

import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.model.item.EventList;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;

public class AutoRescheduleManager {
    public static AutoRescheduleManager manager;
    public static Timer timer;

    private AutoRescheduleManager() {}

    public static AutoRescheduleManager getInstance() {
        if (manager == null) {
            manager = new AutoRescheduleManager();
            timer = new Timer();
        }
        return manager;
    }

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
                        // modify the event date to the most upcoming one (For loop: event start date + period until time is after now)
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
        long period = event.getPeriod().getPeriod();
        LocalDateTime startDateTime = event.getStartDateTime();
        long millisDifference = Duration.between(startDateTime, LocalDateTime.now()).toMillis(); // positive difference;
        BigInteger periodBI = new BigInteger("" + period);
        BigInteger millisDifferenceBI = new BigInteger("" + millisDifference);

        long millisRemainder = millisDifferenceBI.mod(periodBI).longValue();
        LocalDateTime updatedDateTime = LocalDateTime.now().plusNanos(Duration.ofMillis(millisRemainder).toNanos());

        return updatedDateTime;
    }

    public void add(RescheduleTask task) {
        try {
            Duration delay = Duration.between(LocalDateTime.now(), task.getStartTime());
            timer.scheduleAtFixedRate(task, delay.toMillis(), task.getLongPeriod());
            System.out.println("Should only add once");
        } catch (Exception e) {
            System.out.println("Fail to add rescheduleTask: " + e.getMessage());
        }
    }

    public void shutdown() {
        timer.cancel();
    }

}
