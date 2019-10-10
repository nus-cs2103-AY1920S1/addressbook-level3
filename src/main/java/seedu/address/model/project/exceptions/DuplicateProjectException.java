package seedu.address.model.project.exceptions;

public class DuplicateProjectException extends RuntimeException {
    public DuplicateProjectException() {
        super("Operation would result in duplicate projects");
    }
}
