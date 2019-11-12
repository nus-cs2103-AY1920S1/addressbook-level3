package seedu.moolah.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.AppUtil.checkArgument;

import java.util.List;

import seedu.moolah.model.general.Price;

/**
 * Represents a TrendStatistic's mode in the MooLah.
 * Guarantees: immutable; is valid as declared in {@link #isValidMode(String)}
 */
public class Mode {
    public static final String MESSAGE_CONSTRAINTS =
            "Mode should only be one of the following: category OR budget";
    private static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private static List<String> validModes = List.of("CATEGORY", "BUDGET");
    private final String modeName;

    /**
     * Constructs a {@code Mode}.
     *
     * @param modeName A valid mode.
     */
    public Mode(String modeName) {
        requireNonNull(modeName);
        checkArgument(isValidMode(modeName), MESSAGE_CONSTRAINTS);
        this.modeName = modeName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid mode.
     */
    public static boolean isValidMode(String test) {
        return test.matches(VALIDATION_REGEX) && validModes.contains(test.toUpperCase());
    }

    public boolean isBudgetMode() {
        return modeName.equals("budget".toUpperCase());
    }

    @Override
    public String toString() {
        return modeName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && modeName.equals(((Mode) other).modeName)); // state check
    }

    @Override
    public int hashCode() {
        return modeName.hashCode();
    }




}









