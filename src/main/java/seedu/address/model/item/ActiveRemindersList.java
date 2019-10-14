package seedu.address.model.item;

import javafx.beans.property.ListPropertyBase;
import seedu.address.commons.core.item.Item;

import java.awt.*;
import java.util.Collection;

public class ActiveRemindersList extends ListPropertyBase<Item> {

    public ActiveRemindersList (ReminderList reminderList) {
        super(reminderList);
    }

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public synchronized Item popReminder() {
        if(!isEmpty()) {
            return remove(0);
        } else {
            //Should have this throw an exception
            return null;
        }
    }

    public synchronized void addReminders(Collection<Item> reminders) {
        System.out.println("Starting to add.");
        for (Item item:reminders) {
            add(0, item);
        }
        System.out.println("Finished adding.");
        return;
    }
}
