package cs.f10.t1.nursetraverse.model.appointment.exceptions;

/**
 * Signals that the operation will result in duplicate Patients (Patients are considered duplicates
 * if they have the same identity).
 */
public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
