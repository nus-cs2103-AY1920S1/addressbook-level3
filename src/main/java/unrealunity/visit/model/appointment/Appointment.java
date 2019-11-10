package unrealunity.visit.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Appointment object with its description and days left.
 */
public class Appointment {

    /**
     * Enumeration for Appointment Type: Reminder or Follow-up
     */
    public static final class Type {
        public static final Integer REMINDER = 0;
        public static final Integer FOLLOWUP = 1;
    }

    /**
     * The description of the Appointment.
     */
    private String description;

    /**
     * How many days the Appointment has remaining.
     */
    private int days;

    /**
     * Creates a new Appointment object.
     *
     * @param description The description of the Appointment.
     * @param days How many days the Appointment has remaining.
     */
    public Appointment(String description, int days) {
        requireNonNull(description);
        requireNonNull(days);
        this.description = description;
        this.days = days;
    }

    /**
     * Gets the description of the Appointment.
     *
     * @return The description of the Appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the raw description of the Appointment, without the prefix.
     *
     * @return The raw description of the Appointment.
     */
    public String getDescriptionRaw() {
        return description.substring(4);
    }

    /**
     * Gets the type of the Appointment.
     *
     * @return [R] for Reminder, [F] for Follow-Up.
     */
    public String getType() {
        return description.substring(0, 3);
    }

    /**
     * Gets the number of days remaining for the Appointment.
     *
     * @return The number of days remaining for the Appointment.
     */
    public int getDays() {
        return days;
    }

    /**
     * Sets the number of days remaining for the Appointment.
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * Gets the number of days remaining for the Appointment in String.
     *
     * @return The number of days remaining for the Appointment.
     */
    public String getDaysString() {
        return Integer.toString(days);
    }

    /**
     * Comparator to check for same Appointment with other Appointment.
     *
     * @param otherAppt other Appointment object.
     * @return True if it is the same.
     */
    public boolean isSameAppointment(Appointment otherAppt) {
        if (otherAppt == this) {
            return true;
        }

        return otherAppt != null
                && otherAppt.getDescription().equals(getDescription())
                && otherAppt.getDaysString().equals(getDaysString());
    }

    /**
     * Comparator to check for same Appointment with same Key (description).
     *
     * @param description Description to check against.
     * @return True if it is the same.
     */
    public boolean isSameAppointment(String description) {
        return description != null
                && getDescriptionRaw().equals(description);
    }

    @Override
    public String toString() {
        return description;
    }
}
