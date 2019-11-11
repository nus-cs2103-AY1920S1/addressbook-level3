package seedu.moolah.model.event;

import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A reminder of an upcoming task that the user had inputted.
 */
public class Reminder {
    private Event event;
    private long daysLeft;

    public Reminder(Event event, long daysLeft) {
        requireAllNonNull(event);
        this.event = event;
        this.daysLeft = daysLeft;
    }

    /**
     * Formats the reminder into a readable form for the user.
     *
     * @return The reminder message.
     */
    @Override
    public String toString() {
        return String.format(
                "You have %d days left before this event: %s!",
                daysLeft, event.getDescription());
    }

}
