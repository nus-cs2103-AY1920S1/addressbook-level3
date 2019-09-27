package seedu.address.model.expense;

// import java.time.LocalDateTime;
// import java.time.temporal.ChronoUnit;
// import java.util.Optional;

/**
 * A reminder of an upcoming task that the user had inputted.
 */
public class Reminder {
    private Event event;
    private String totalTimeDifference;

    public Reminder(Event event, String totalTimeDifference) {
        this.event = event;
        this.totalTimeDifference = totalTimeDifference;
    }

    /**
     * Formats the reminder into a readable form for the user.
     *
     * @return The reminder message.
     */
    @Override
    public String toString() {
        return String.format(
                "You have %s left to complete the task: %s!",
                totalTimeDifference, event.getDescription());
    }

}
