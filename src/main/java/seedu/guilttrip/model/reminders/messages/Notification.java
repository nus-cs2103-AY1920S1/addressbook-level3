package seedu.guilttrip.model.reminders.messages;

public class Notification {
    String text;
    public Notification (String text){
        this.text = text;
    };
    public String toString() {
        return text;
    }
}
