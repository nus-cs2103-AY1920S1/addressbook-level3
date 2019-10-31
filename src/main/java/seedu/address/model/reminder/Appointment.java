package seedu.address.model.reminder;

/**
 * Represents a Appointment object containing title and number of days left
 */
public class Appointment {
    private String description;
    private int days;

    public Appointment(String description, int days) {
        this.description = description;
        this.days = days;
    }

    public String getDescription() {
        String desc = description;
        return desc;
    }

    public String getDaysString() {
        return Integer.toString(days);
    }

    @Override
    public String toString() {
        return description;
    }
}
