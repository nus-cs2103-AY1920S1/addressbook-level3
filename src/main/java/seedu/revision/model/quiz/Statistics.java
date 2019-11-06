package seedu.revision.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents the statistics of a quiz completed in the Revision Tool.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatistics (String)}
 */
public class Statistics {

    public static final String MESSAGE_CONSTRAINTS = "Statistics are made up of total scores and scores of "
            + "difficulty levels 1, 2, 3.";

    /*
     * The first character of the statistics must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * 1 to 2 digits before and after the decimal point.
     */
    public static final String VALIDATION_REGEX = "(?i)\\d+\\/\\d+";

    private int score;
    private int total;
    private double result = (double) score / total;
    private int score1;
    private int total1;
    private int score2;
    private int total2;
    private int score3;
    private int total3;

    public Statistics(int score, int total, int score1, int total1, int score2, int total2, int score3, int total3) {
        requireNonNull(score);
        requireNonNull(total);
        requireNonNull(score1);
        requireNonNull(total1);
        requireNonNull(score2);
        requireNonNull(total2);
        requireNonNull(score3);
        requireNonNull(total3);
        this.score = score;
        this.total = total;
        this.score1 = score1;
        this.total1 = total1;
        this.score2 = score2;
        this.total2 = total2;
        this.score3 = score3;
        this.total3 = total3;
    }

    public Statistics(String score) {
        requireNonNull(score);
        checkArgument(isValidStatistics(score), MESSAGE_CONSTRAINTS);
        String[] splitStr = score.split(",", 4);
        String[] splitScore = splitStr[0].split("/", 2);
        String[] splitScore1 = splitStr[1].split("/", 2);
        String[] splitScore2 = splitStr[2].split("/", 2);
        String[] splitScore3 = splitStr[3].split("/", 2);
        this.score = Integer.parseInt(splitScore[0]);
        this.total = Integer.parseInt(splitScore[1]);
        this.score1 = Integer.parseInt(splitScore1[0]);
        this.total1 = Integer.parseInt(splitScore1[1]);
        this.score2 = Integer.parseInt(splitScore2[0]);
        this.total2 = Integer.parseInt(splitScore2[1]);
        this.score3 = Integer.parseInt(splitScore3[0]);
        this.total3 = Integer.parseInt(splitScore3[1]);
    }

    public double getResult() {
        return result * 100;
    }

    /**
     * Returns true if a given string is a valid statistics.
     */
    public static boolean isValidStatistics(String test) {
        String[] splitStr = test.split(",", 4);
        return splitStr[0].matches(VALIDATION_REGEX) && splitStr[1].matches(VALIDATION_REGEX)
                && splitStr[2].matches(VALIDATION_REGEX) && splitStr[3].matches(VALIDATION_REGEX);
    }


    /**
     * Converting the statistics into a string to be stored later.
     * @return a fixed string format.
     */
    public String toString() {
        return String.format(score + "/" + total + "," + score1 + "/" + total1 + ","
        + score2 + "/" + total2 + "," + score3 + "/" + total3);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && score == (((Statistics) other).score)
                && total == (((Statistics) other).total)
                && score1 == (((Statistics) other).score1)
                && total1 == (((Statistics) other).total1)
                && score2 == (((Statistics) other).score2)
                && total2 == (((Statistics) other).total2)
                && score3 == (((Statistics) other).score3)
                && total3 == (((Statistics) other).total3)); // state check
    }

    @Override
    public int hashCode() {
        return String.format(score + "/" + total + "," + score1 + "/" + total1 + ","
                + score2 + "/" + total2 + "," + score3 + "/" + total3).hashCode();
    }

}
