package seedu.revision.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents the statistics of a quiz completed in the Revision Tool.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatistics (String)}
 */
public class Statistics {

    public static final String MESSAGE_CONSTRAINTS = "Statistics should only be in double";

    /*
     * The first character of the statistics must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * 1 to 2 digits before and after the decimal point.
     */
    public static final String VALIDATION_REGEX = "(?i)\\d{1,2}\\.\\d{1,2}";

    private double totalScore;

    public Statistics(double totalScore) {
        requireNonNull(totalScore);
        this.totalScore = totalScore;
    }

    public Statistics(String totalScore) {
        requireNonNull(totalScore);
        checkArgument(isValidStatistics(totalScore), MESSAGE_CONSTRAINTS);
        this.totalScore = Double.parseDouble(totalScore);
    }

    /**
     * Returns true if a given string is a valid statistics.
     */
    public static boolean isValidStatistics(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String toString() {
        return Double.toString(totalScore);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && totalScore == (((Statistics) other).totalScore)); // state check
    }

    @Override
    public int hashCode() {
        return Double.toString(totalScore).hashCode();
    }

}
