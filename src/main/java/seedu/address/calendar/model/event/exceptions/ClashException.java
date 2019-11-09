package seedu.address.calendar.model.event.exceptions;

import java.util.List;

/**
 * Represents an error when adding an engagement (trip or commitment) to the calendar due to possible conflicts.
 */
public class ClashException extends RuntimeException {
    private List<String> collisions;

    /**
     * Creates an instance {@code ClashException}.
     * @param collisions The event(s) that are in conflict with that which is about to be added.
     */
    public ClashException(List<String> collisions) {
        super("Operation would result in clashes in schedule");
        this.collisions = collisions;
    }

    @Override
    public String getMessage() {
        String collisionsStr = collisions.stream()
                .reduce("", (prev, curr) -> {
                    String accum = prev + curr + "\n";
                    return accum;
                })
                .trim();
        String exceptionMsg = String.format(super.getMessage() + " with:\n%s\n\nWould you still like to continue "
                + "with the operation? Please type 'yes' or 'no'. Or if you would like to proceed with other commands, "
                + "just continue with them. ", collisionsStr);
        return exceptionMsg;
    }
}
