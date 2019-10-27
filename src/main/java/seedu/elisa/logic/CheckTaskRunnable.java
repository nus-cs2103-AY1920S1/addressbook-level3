package seedu.elisa.logic;

import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.Reminder;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.ItemModelManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CheckTaskRunnable implements Runnable {
    private final Logger logger = LogsCenter.getLogger(CheckTaskRunnable.class);
    ItemModel model;
    ArrayList<Item> futureReminders;
    ArrayList<Item> activeReminders;
    Reminder reminder;
    Item item;

    public CheckTaskRunnable(ItemModel model) {
        this.model = model;
        futureReminders = model.getFutureRemindersList();

        while(futureReminders.size() > 0 && futureReminders.get(0).getReminder().get().getDateTime().isBefore(LocalDateTime.now())) {
            futureReminders.remove(0);
        }

        activeReminders = new ArrayList<Item>(0);
        reminder = null;
    }

    public void run() {
        logger.info("----------------[LOGIC MANAGER]["
                + "Checking for pending reminders" + "]");

        if (futureReminders.size() > 0) {
            reminder = futureReminders.get(0).getReminder().get();
            while (reminder != null && reminder.getDateTime().isBefore(LocalDateTime.now())) {
                logger.info("----------------[LOGIC MANAGER]["
                        + "Transferring reminder from futureReminders to activeReminders" + "]");

                item = futureReminders.remove(0);
                activeReminders.add(item);
                if (futureReminders.size() > 0) {
                    reminder = futureReminders.get(0).getReminder().get();
                } else {
                    reminder = null;
                }
            }

            model.getActiveReminderListProperty().addReminders(activeReminders);
        }
    }

}
