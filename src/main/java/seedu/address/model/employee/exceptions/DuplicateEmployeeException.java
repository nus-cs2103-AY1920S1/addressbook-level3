package seedu.address.model.employee.exceptions;

/**
 * Signals that the operation will result in duplicate Employees
 * (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateEmployeeException extends RuntimeException {
    public DuplicateEmployeeException() {
        super("Operation would result in duplicate employees");
    }
}
