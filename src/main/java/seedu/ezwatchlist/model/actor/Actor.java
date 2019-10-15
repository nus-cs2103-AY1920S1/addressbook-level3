package seedu.ezwatchlist.model.actor;

import seedu.ezwatchlist.commons.util.AppUtil;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.commons.util.AppUtil.checkArgument;

/**
 * Represents an Actor in a show in the watchlist.
 * Guarantees: immutable; name is valid as declared in {@link #isValidActorName(String)}
 */
public class Actor {
    public static final String MESSAGE_CONSTRAINTS =
            "Actor names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the actor name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String actorName;
    //Maybe add a character name for actors?
    public String characterName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Actor(String name) {
        requireNonNull(name);
        AppUtil.checkArgument(isValidActorName(name), MESSAGE_CONSTRAINTS);
        actorName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidActorName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return actorName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Actor // instanceof handles nulls
                && actorName.equals(((Actor) other).actorName)); // state check
    }

    @Override
    public int hashCode() {
        return actorName.hashCode();
    }

}
