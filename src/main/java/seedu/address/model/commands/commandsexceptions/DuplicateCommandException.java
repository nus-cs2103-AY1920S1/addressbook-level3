package seedu.address.model.commands.commandsexceptions;

/**
 * Signals that the operation will result in duplicate Earnings
 * (Earnings are considered duplicates if they have the same
 * classId, date, amount and type).
 */
public class DuplicateCommandException extends RuntimeException {
    public DuplicateCommandException() {
        super("Operation would result in duplicate command");
    }
}
