package unrealunity.visit.model.appointment.exceptions;

/**
 * Signals that the operation will result in duplicate Appointments
 * (Appointments are considered duplicates if they have the same description and days).
 */
public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointment");
    }
}
