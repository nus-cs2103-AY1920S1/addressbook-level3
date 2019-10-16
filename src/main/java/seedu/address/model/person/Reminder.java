package seedu.address.model.person;

/**
 * Basic reminder class with minimal functionality.
 */
public abstract class Reminder {
    private String message;
    private boolean isActivated;
    private int priority;


    public Reminder(String message) {
        this.message = message;
        isActivated = false;
    }

    public String getMessage() {
        return message;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getStatus() {
        return isActivated;
    }

    void setStatus(boolean bool) {
        isActivated = bool;
    }


    @Override
    public String toString() {
        return "Reminder: " + message;
    }

    abstract boolean isSameReminder(Reminder reminder);
}
