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
        return description;
    }

    public String getDescriptionRaw() {
        return description.substring(4);
    }

    public String getType() {
        return description.substring(0, 3);
    }

    public int getDays() {
        return days;
    }

    public String getDaysString() {
        return Integer.toString(days);
    }

    /**
     * Check for same Appointment with other Appointment
     * @param otherAppt other Appointment object
     * @return True if it is the same
     */
    public boolean isSameAppointment(Appointment otherAppt) {
        if (otherAppt == this) {
            return true;
        }

        return otherAppt != null
                && otherAppt.getDescription().equals(getDescription())
                && otherAppt.getDaysString().equals(getDaysString());
    }

    @Override
    public String toString() {
        return description;
    }
}
