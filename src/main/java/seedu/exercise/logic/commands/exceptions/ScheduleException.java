package seedu.exercise.logic.commands.exceptions;

/**
 * Represents a scheduling conflict between regimes during execution of a {@code Command}
 */
public class ScheduleException extends CommandException {

    /**
     * Constructs a new {@code ScheduleException} with the specified {@code message}
     * @param message
     */
    public ScheduleException(String message) {
        super(message);
    }
}
