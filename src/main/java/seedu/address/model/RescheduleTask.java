package seedu.address.model;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.logic.LogicManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.logging.Logger;

public class RescheduleTask extends TimerTask {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    public ItemModel model;

    Item item;
    Event event;
    AutoReschedulePeriod period;

    public RescheduleTask(Item item, AutoReschedulePeriod period, ItemModel model) {
        this.item = item;
        this.event = item.getEvent().get();
        this.period = period;
        this.model = model;
    }

    public LocalDateTime getStartTime() {
        return event.getStartDateTime();
    }

    public long getLongPeriod() {
        return period.getPeriod();
    }

    public void run() {
        logger.info( "----------[INFO] " + "Reschedule runs again. Old event: " + event.toString());
        Item oldItem = item;
        long period = event.getPeriod().getPeriod();
        LocalDateTime newStart = LocalDateTime.now().plusNanos(Duration.ofMillis(period).toNanos());
        Event newEvent = event.changeStartDateTime(newStart);
        Item newItem = item.changeEvent(newEvent);
        model.replaceItem(oldItem, newItem);
        model.updateLists();
        model.setVisualizeList(model.getVisualList()); // to refresh the view

        this.item = newItem;
        this.event = newEvent;

        logger.info("-----------[INFO] " + "End of run. New event: " + newEvent.toString());
    }
}
