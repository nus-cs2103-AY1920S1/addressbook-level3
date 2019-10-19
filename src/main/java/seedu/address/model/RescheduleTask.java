package seedu.address.model;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.logic.LogicManager;

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
        logger.info("Reschedule runs again. Old event: " + event.toString());
        Item oldItem = item;
        Event newEvent = event.changeStartDateTime(LocalDateTime.now());
        Item newItem = item.changeEvent(newEvent);
        model.replaceItem(oldItem, newItem);
        model.updateLists();

        this.item = newItem;
        this.event = newEvent;

        logger.info("End of run. New event: " + newEvent.toString());
    }
}
