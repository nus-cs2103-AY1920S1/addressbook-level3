package seedu.address.model.reminder;

/**
 * Represents a Reminder object containing title and number of days left
 */
public class ReminderStub {
    private String description;
    private int noOfDays;

    public ReminderStub(String description, int noOfDays) {
        this.description = description;
        this.noOfDays = noOfDays;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getDescription() {
        String desc = description;
        return desc;
    }

    public String getDaysString() {
        int noOfDays = this.noOfDays;
        return Integer.toString(noOfDays);
    }

    // may not be needed
    @Override
    public String toString() {
        return description;
    }
}
