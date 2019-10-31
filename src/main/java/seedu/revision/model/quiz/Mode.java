package seedu.revision.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;

/**
 * Represents the mode of a quiz in the Revision Tool.
 * Guarantees: immutable; is valid as declared in {@link #isValidMode(String)}
 */
public class Mode {

    public static final String MESSAGE_CONSTRAINTS = "Mode can only be normal / arcade / custom";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "(?i)\\bnormal\\b|\\barcade\\b|^\\bcustom\\b";

    public final String value;
    protected int time;
    protected Predicate<Answerable> combinedPredicate;

    /**
     * Constructs a {@code Mode}.
     *
     * @param value A valid mode.
     */
    public Mode(String value) {
        requireNonNull(value);
        checkArgument(isValidMode(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid question.
     */
    public static boolean isValidMode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int getTime() {
        return time;
    }

    public int getLevelTwoTime() {
        return time;
    }

    public int getLevelThreeTime() {
        return time;
    }

    public Predicate<Answerable> getCombinedPredicate() {
        return combinedPredicate;
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mode // instanceof handles nulls
                && value.equals(((Mode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
