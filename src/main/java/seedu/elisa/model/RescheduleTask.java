package seedu.elisa.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.application.Platform;
import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.LogicManager;

/**
 * Task to reschedule an event.
 */
public class RescheduleTask extends TimerTask {
    private static LinkedList<RescheduleTask> allTasks = new LinkedList<>();
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private ItemModel model;
    private Item item;
    private Event event;
    private AutoReschedulePeriod period;

    /**
     * Creates a RescheduleTask to reschedule this item automatically, in intervals of the given period.
     * Requirements: Item must have an Event.
     * @param item who's event is to be rescheduled
     * @param period interval of rescheduling
     * @param model which consist of this item
     */
    public RescheduleTask(Item item, AutoReschedulePeriod period, ItemModel model) {
        this.item = item;
        this.event = item.getEvent().get();
        this.period = period;
        this.model = model;
    }

    /**
     * Adds this {@code task} to the list of allTasks.
     * @param task to be added
     */
    public static void addToAllTasks(RescheduleTask task) {
        allTasks.add(task);
    }

    /**
     * Removes the RescheduleTask of this event from the list of allTasks.
     * @param event to be removed
     */
    public static void removeFromAllTasks(Event event) {
        for (RescheduleTask task : RescheduleTask.allTasks) {
            if (task.getEvent().equals(event)) {
                task.cancel();
                RescheduleTask.allTasks.remove(task);
            }
        }
    }

    public LocalDateTime getStartTime() {
        return event.getStartDateTime();
    }

    public long getLongPeriod() {
        return period.getPeriod();
    }

    public Event getEvent() {
        return this.event;
    }

    /**
     * Executes this task.
     * Update this event's startDateTime with the new startDateTime, given the period of reccurence.
     * Update the model with the new item.
     * Refreshes the view on the Ui to reflect the new startDateTime of this event.
     */
    public void run() {
        logger.info("----------[INFO] " + "Reschedule runs again. Old event: " + event.toString());
        Item oldItem = item;
        long period = event.getPeriod().getPeriod();
        LocalDateTime newStart = LocalDateTime.now().plusNanos(Duration.ofMillis(period).toNanos());
        Event newEvent = event.changeStartDateTime(newStart);
        Item newItem = item.changeEvent(newEvent);;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                model.replaceItem(oldItem, newItem);
                model.repopulateLists();
                model.setVisualizeList(model.getVisualList()); // to refresh the view
            }
        });

        this.item = newItem;
        this.event = newEvent;

        logger.info("-----------[INFO] " + "End of run. New event: " + newEvent.toString());
    }
}
