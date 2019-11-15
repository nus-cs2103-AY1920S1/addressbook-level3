package seedu.guilttrip.model.reminders.messages;

/**
 * Basic message displayed in notification panel when reminder is activated.
 */
public class Notification {
    private String text;
    public Notification (String text) {
        this.text = text;
    };
    public String toString() {
        return text;
    }
}
