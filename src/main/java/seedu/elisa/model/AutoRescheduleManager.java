package seedu.elisa.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.logging.Logger;

import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.LogicManager;
import seedu.elisa.logic.parser.ParserUtil;
import seedu.elisa.model.item.EventList;

/**
 * Manages all the events that are to be rescheduled automatically at each of their given interval period.
 * Uses a Timer to keep track of when to update the Event's startDateTime.
 */
public class AutoRescheduleManager {
    private static AutoRescheduleManager manager;
    private static Timer timer;

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

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
     * Initialise this AutoRescheduleManager with all the events that can be rescheduled, in the given event list.
     * Update event times to the latest upcoming one, given their period.
     * @param eventList of all events in the storage
     * @param model containing the events
     */
    public static void initStorageEvents(EventList eventList, ItemModel model) {
        for (Item item : eventList) {
            if (item.hasEvent()) {
                updateEvent(item, model);
            }
        }
    }

    /**
     * Updates the start time of the event and creates a RescheduleTask based on the updated event.
     * If the current start time is already over, update to the next upcoming time.
     * Else, start time remains the same.
     * @param item whose event is to be updated
     * @param model where the item is stored in
     */
    public static void updateEvent(Item item, ItemModel model) {
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

    /**
     * Update the old event start time to the closest upcoming start time, using the event's auto-reschedule period
     * @param event whose start date we want to modify
     * @return LocalDateTime representation of the modified time
     */
    private static LocalDateTime getUpdatedDateTime(Event event) {
        // Use modulo to get the remaining time till the next reschedule time. Add that remaining time to the time now.
        long period = event.getPeriod().getPeriod();
        LocalDateTime startDateTime = event.getStartDateTime();

        LocalDateTime updatedDateTime = ParserUtil.getUpdatedDateTime(startDateTime, period);
        return updatedDateTime;
    }

    /**
     * Adds a RescheduleTask that is to be rescheduled periodically, to this AutoRescheduleManager.
     * Requirements: task's start time is after current time. If not, use {@code updateEvent()} to add.
     * @param task RescheduleTask to be carried out after reaching an event's startDateTime
     */
    public void add(RescheduleTask task) {
        try {
            Duration delay = Duration.between(LocalDateTime.now(), task.getStartTime());
            if (delay.getSeconds() < 0) {
                LocalDateTime updatedTime = getUpdatedDateTime(task.getEvent());
                //delay = Duration.between(LocalDateTime.now(), updatedTime);
            }

            RescheduleTask.addToAllTasks(task);
            timer.scheduleAtFixedRate(task, delay.toMillis(), task.getLongPeriod());
        } catch (Exception e) {
            logger.warning("----------------[Failed to schedule Event][" + task.getEvent() + "]" + e.getMessage());
        }
    }

    /**
     * Shutdown the AutoRescheduleManager
     */
    public void shutdown() {
        timer.cancel();
    }

}
