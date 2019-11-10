package seedu.guilttrip.model.reminders.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.reminders.GeneralReminder;

/**
 * Used to keep track of specific Income/ Expense or Wishes.
 */
public class EntryHandler implements PropertyChangeListener {
    private GeneralReminder.Status status;
    private Entry monitoredEntry;
    private GeneralReminder generalReminder;

    private LocalDate currDate;
//===== Update Status and Notify GeneralReminder =====//
    @SuppressWarnings("checkstyle:CommentsIndentation")
    private void update() {

        generalReminder.setStatus(status.toString());
    }
//===== Update Local Date =====//
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("currDate")) {
            update();
        }
    }
}
