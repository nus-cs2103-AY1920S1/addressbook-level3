package seedu.flashcard.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.AppUtil.checkArgument;

/**
 * Represents a choice in the flashcard list.
 * Guarantees: immutable; choice is valid as declared in {@link #isValidChoice(String)}
 */
public class Choice {
    public static final String MESSAGE_CONSTRAINTS = "Choice can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String choice;

    public Choice(String choice) {
        requireNonNull(choice);
        checkArgument(isValidChoice(choice));
        this.choice = choice;
    }

    /**
     * Returns true if a given string is a valid choice.
     */
    public static boolean isValidChoice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Choice
                && choice.equals(((Choice) other).choice));
    }

    @Override
    public int hashCode() {
        return choice.hashCode();
    }

    @Override
    public String toString() {
        return '[' + choice + ']';
    }

}
