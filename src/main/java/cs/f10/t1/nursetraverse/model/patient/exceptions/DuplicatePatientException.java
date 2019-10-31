package cs.f10.t1.nursetraverse.model.patient.exceptions;

/**
 * Signals that the operation will result in duplicate Patients (Patients are considered duplicates if
 * they have the same identity).
 */
public class DuplicatePatientException extends RuntimeException {
    public DuplicatePatientException() {
        super("Operation would result in duplicate patients");
    }
}
