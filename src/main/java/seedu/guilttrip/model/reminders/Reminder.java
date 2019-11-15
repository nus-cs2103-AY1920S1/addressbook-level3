package seedu.guilttrip.model.reminders;

import seedu.guilttrip.commons.util.ListenerSupport;
import seedu.guilttrip.commons.util.ObservableSupport;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.ui.reminder.ReminderListEntry;

/**
 * All reminder types implements this interface.
 */
public interface Reminder extends ReminderListEntry {
    /**
     * Used by ReminderList to know when to update displayed reminder messages.
     * @return
     */
    abstract Status getStatus();
    abstract Notification genNotification();
    abstract Description getHeader();
    abstract void setHeader(Description header);
    abstract Message getMessage();
    abstract boolean willDisplayPopUp();
    abstract void togglePopUpDisplay(boolean willDisplayPopup);
    abstract void setMessage(Message message);
    public ObservableSupport getSupport();
    public void addPropertyChangeListener(ListenerSupport pcl);
    public void removePropertyChangeListener(ListenerSupport pcl);
    public String getUniqueId();
    public void reset();

    /**
     * Status of condition
     */
    public enum Status {
        unmet, met, exceeded;

        /**
         * Converts string to status enum.
         */
        public static Status parse (String status) {
            switch (status.toLowerCase()) {
            case "met":
                return met;
            case "exceeded":
                return exceeded;
            default:
                return unmet;
            }
        }
        @Override
        public String toString() {
            switch(this) {
            case met:
                return "met";
            case exceeded:
                return "exceeded";
            default:
                return "unmet";
            }
        }
    }
}
