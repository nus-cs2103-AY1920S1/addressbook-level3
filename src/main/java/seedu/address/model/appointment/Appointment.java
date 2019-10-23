package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.datetime.DateTime;
import seedu.address.model.datetime.RecurringDateTime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

/**
 * Represents an Appointment in the application
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    // Identity fields
    private final DateTime startDateTime;
    private final DateTime endDateTime;
    private final RecurringDateTime frequency;

    // Data fields
    private final Person patient;
    private final String description;
    // private final List<Medicine> medicinePackingList;

    public Appointment(DateTime startDateTime, DateTime endDateTime, RecurringDateTime frequency,
                       Person patient, String description) {
        requireAllNonNull(startDateTime, endDateTime, frequency, patient, description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.frequency = frequency;
        this.patient = patient;
        this.description = description;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public RecurringDateTime getFrequency() {
        return frequency;
    }

    public Person getPatient() {
        return patient;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return patient.getAddress();
    }

    /**
     * Returns true if both appointments of the same date and time have at least one other identity field that is the
     * same.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameTimeSlot(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getStartDateTime().equals(getStartDateTime())
                && (otherAppointment.getEndDateTime().equals(getEndDateTime())
                    || otherAppointment.getFrequency().equals(getFrequency()));
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two appointments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;

        return otherAppointment.getStartDateTime().equals(getStartDateTime())
                && otherAppointment.getEndDateTime().equals(getEndDateTime())
                && otherAppointment.getFrequency().equals(getFrequency())
                && otherAppointment.getPatient().equals(getPatient())
                && otherAppointment.getDescription().equals(getDescription())
                && otherAppointment.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startDateTime, endDateTime, frequency, patient, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Start Date and Time: ")
                .append(getStartDateTime())
                .append(" End Date and Time ")
                .append(getEndDateTime())
                .append(" Frequency: ")
                .append(getFrequency())
                .append(" Patient: ")
                .append(getPatient())
                .append(" Location: ")
                .append(getAddress())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
