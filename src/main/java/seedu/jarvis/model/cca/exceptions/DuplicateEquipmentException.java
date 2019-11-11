package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the operation would result in duplicate equipments.
 */
public class DuplicateEquipmentException extends RuntimeException {
    public DuplicateEquipmentException() {
        super("Operation would result in duplicate equipments");
    }
}
