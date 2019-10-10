package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidParticipation(String)}
 */
public class Participation {


    public static final String MESSAGE_CONSTRAINTS =
            "Participation should only contain numbers";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param participation A valid phone number.
     */
    public Participation(String participation) {
        requireNonNull(participation);
        checkArgument(isValidParticipation(participation), MESSAGE_CONSTRAINTS);
        value = participation;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidParticipation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Participation // instanceof handles nulls
                && value.equals(((Participation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
