package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents an interview timeslot an {@code Interviewee} is allocated.
 */
public class Slot {

    public static final String MESSAGE_CONSTRAINTS = "Placeholder";
    public static final String VALIDATION_REGEX = "Placeholder";
    // TODO: Abstract out to DateTime class
    // The plan is for users to input DD/MM/YY HH:MM for both start and end
    public final String start;
    public final String end;

    public Slot(String start, String end) {
        requireNonNull(start, end);
        // TODO: Argument checking
        // checkArgument(isValidSlot(start), MESSAGE_CONSTRAINTS);
        // checkArgument(isValidSlot(end), MESSAGE_CONSTRAINTS);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns true if the given timing is in valid format.
     */
    public static boolean isValidSlot(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Slot // instanceof handles nulls
                && start.equals(((Slot) other).start)
                && end.equals(((Slot) other).end)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

}
