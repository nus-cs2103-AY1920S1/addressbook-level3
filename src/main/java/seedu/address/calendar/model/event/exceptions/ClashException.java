package seedu.address.calendar.model.event.exceptions;

import java.util.List;

public class ClashException extends RuntimeException {
    private List<String> collisions;

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
                + "with the operation? Please type yes or no.", collisionsStr);
        return exceptionMsg;
    }
}
