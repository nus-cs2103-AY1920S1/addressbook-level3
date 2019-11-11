package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.AppUtil.checkArgument;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

/**
 * Represents an Assignment.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assignment implements Comparable<Assignment> {

    public static final String MESSAGE_CONSTRAINTS_ASSIGNMENT_NAME =
            "Assignment name should not be blank and should contain only alphanumeric characters.";
    public static final String MESSAGE_CONSTRAINTS_MAX_SCORE =
            "Max score should be a non-negative integer.";
    public static final String MESSAGE_CONSTRAINTS_START_END_DATE =
            String.format("Dates should be of the format %s. "
            + "Start date should be earlier than end date.", Tutorial.DATE_FORMAT);
    public static final String MESSAGE_CONSTRAINTS_SCORE =
            "Score should be a non-negative integer lesser or equal to the max score.";

    /*
     * The first character of the assignment name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String assignName;
    private final Integer maxScore;
    private final Date startDate;
    private final Date endDate;

    /**
     * Every field must be present and not null.
     */
    public Assignment(String assignName, Integer maxScore, Date startDate, Date endDate) {
        requireAllNonNull(assignName, maxScore, startDate, endDate);
        checkArgument(isValidAssignName(assignName), MESSAGE_CONSTRAINTS_ASSIGNMENT_NAME);
        checkArgument(isValidMaxScore(maxScore), MESSAGE_CONSTRAINTS_MAX_SCORE);
        checkArgument(isValidStartEndDates(startDate, endDate), MESSAGE_CONSTRAINTS_START_END_DATE);
        this.assignName = assignName;
        this.maxScore = maxScore;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns true if a given string is a valid name, else false.
     */
    public static boolean isValidAssignName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given int is a valid max score, else false.
     */
    public static boolean isValidMaxScore(int maxScore) {
        return maxScore >= 0;
    }

    /**
     * Returns true if given start and end dates are valid, else false.
     */
    public static boolean isValidStartEndDates(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

    public String getAssignName() {
        return assignName;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("AssignmentName: ")
                .append(getAssignName())
                .append(" Max Score: ")
                .append(getMaxScore())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Assignment // instanceof handles nulls
                && assignName.equalsIgnoreCase(((Assignment) other).assignName))
                && maxScore == ((Assignment) other).maxScore
                && startDate.equals(((Assignment) other).startDate)
                && endDate.equals(((Assignment) other).endDate); // state check
    }

    /**
     * Compares first on the basis of end date, then start date and finally assignment name.
     */
    @Override
    public int compareTo(Assignment other) {
        return endDate.compareTo(other.endDate) != 0
                ? endDate.compareTo(other.endDate)
                : startDate.compareTo(other.startDate) != 0
                ? startDate.compareTo(other.startDate)
                : assignName.compareTo(other.assignName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignName, maxScore, startDate, endDate);
    }

    public String getAssignmentName() {
        return this.assignName;
    }

    public Date getAssignmentStartDate() {
        return this.startDate;
    }

    public Date getAssignmentEndDate() {
        return this.endDate;
    }

    public int getAssignmentMaxScore() {
        return this.maxScore;
    }



}
