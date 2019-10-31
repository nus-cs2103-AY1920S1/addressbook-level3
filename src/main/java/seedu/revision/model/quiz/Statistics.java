package seedu.revision.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents the statistics of a quiz completed in the Revision Tool.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatistics (String)}
 */
public class Statistics {

    public static final String MESSAGE_CONSTRAINTS = "Statistics are made up of 2 items, score and total";

    /*
     * The first character of the statistics must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * 1 to 2 digits before and after the decimal point.
     */
    public static final String VALIDATION_REGEX = "(?i)\\d+\\/\\d+";

    private int total;
    private int score;
    private double result = score / total;

    public Statistics(int score, int total) {
        requireNonNull(score);
        requireNonNull(total);
        this.total = total;
        this.score = score;
    }

    public Statistics(String score) {
        requireNonNull(score);
        checkArgument(isValidStatistics(score), MESSAGE_CONSTRAINTS);
        String[] splitStr = score.split("/", 2);
        this.total = Integer.parseInt(splitStr[1]);
        this.score = Integer.parseInt(splitStr[0]);
    }

    public double getResult() {
        return result;
    }

    /**
     * Returns true if a given string is a valid statistics.
     */
    public static boolean isValidStatistics(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    public String toString() {
        return String.format(score + "/" + total);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && score == (((Statistics) other).score)
                && total == (((Statistics) other).total)); // state check
    }

    @Override
    public int hashCode() {
        return String.format(score + "/" + total).hashCode();
    }

}
