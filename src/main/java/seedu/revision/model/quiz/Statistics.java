package seedu.revision.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

import javafx.collections.ObservableList;
import seedu.revision.model.answerable.Answerable;

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
     * 1 or more digits before and after the decimal point.
     */
    public static final String VALIDATION_REGEX = "(?i)\\d+\\/\\d+";

    private int score;
    private int total;
    private double result;
    private int score1;
    private int total1;
    private double result1;
    private int score2;
    private int total2;
    private double result2;
    private int score3;
    private int total3;
    private double result3;

    public Statistics() {
        this.score = 0;
        this.total = 0;
        this.score1 = 0;
        this.total1 = 0;
        this.score2 = 0;
        this.total2 = 0;
        this.score3 = 0;
        this.total3 = 0;
        this.result = 0;
        this.result1 = 0;
        this.result2 = 0;
        this.result3 = 0;
    }

    public Statistics(String scores) {
        requireNonNull(scores);
        checkArgument(isValidStatistics(scores), MESSAGE_CONSTRAINTS);
        String[] splitStr = scores.split(",", 4);
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
        this.result = (double) score / total;
        this.result1 = (double) score1 / total1;
        this.result2 = (double) score2 / total2;
        this.result3 = (double) score3 / total3;
    }

    public double getResult() {
        return result * 100.0;
    }

    public double getResult1() {
        return result1 * 100.0;
    }

    public double getResult2() {
        return result2 * 100.0;
    }

    public double getResult3() {
        return result3 * 100.0;
    }

    public String getPriority() {
        double priority = Math.min(result1, Math.min(result2, result3));
        if (priority == result1) {
            return "Difficulty 1";
        } else if (priority == result2) {
            return "Difficulty 2";
        } else if (priority == result3) {
            return "Difficulty 3";
        } else {
            return "Cannot be determined";
        }
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

    /**
     * Updates the scores after every answer to a question.
     * @param currentAnswerable the current question.
     * @param quizList the entire list of questions during a quiz attempt.
     */
    public void updateStatistics(Answerable currentAnswerable, ObservableList<Answerable> quizList) {
        this.score++;
        this.total = quizList.size();
        switch (currentAnswerable.getDifficulty().difficulty) {
        case "1":
            score1++;
            this.total1 = quizList.filtered(a ->
                    a.getDifficulty().difficulty.equals(currentAnswerable.getDifficulty().difficulty)).size();
            break;
        case "2":
            score2++;
            this.total2 = quizList.filtered(a ->
                    a.getDifficulty().difficulty.equals(currentAnswerable.getDifficulty().difficulty)).size();
            break;
        case "3":
            score3++;
            this.total3 = quizList.filtered(a ->
                    a.getDifficulty().difficulty.equals(currentAnswerable.getDifficulty().difficulty)).size();
            break;
        default:
            assert false : currentAnswerable.getDifficulty().difficulty;
        }
        this.result = (double) score / total;
        this.result1 = (double) score1 / total1;
        this.result2 = (double) score2 / total2;
        this.result3 = (double) score3 / total3;

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
