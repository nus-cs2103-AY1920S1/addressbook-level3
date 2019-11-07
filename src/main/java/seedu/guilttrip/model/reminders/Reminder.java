package seedu.guilttrip.model.reminders;

import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.model.reminders.messages.Notification;

public interface Reminder {
    /**
     * Used by R3minderList to know when to update displayed reminder messages.
     * @return
     */
    abstract Status getStatus();
    abstract Notification genNotification();
    abstract Message getMessage();
    abstract void reset();
    abstract boolean willDisplayPopUp();
    abstract void togglePopUpDisplay(boolean willDisplayPopup);
    abstract void setMessage(Message message);

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
