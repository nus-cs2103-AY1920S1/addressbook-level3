package thrift.model.clone;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Objects;

import thrift.logic.commands.exceptions.CommandException;

/**
 * Represents how many times and with what frequency (how much time apart) a {@code Transaction} should be cloned.
 * Guarantees: immutable, frequency not null, numOccurences valid according to frequency.
 */
public class Occurrence {
    public static final String OCCURRENCE_CONSTRAINTS = "Occurrence must be in the format \"FREQUENCY:"
            + "NUMBER_OF_OCCURRENCES\"."
            + "\n- Valid FREQUENCY values are: \"daily\", \"weekly\", \"monthly\", \"yearly\""
            + "\n- Valid NUMBER_OF_OCCURRENCES ranges are: 1 - 5 with \"yearly\", 1 - 12 with other frequencies";
    private static final String[] validFrequencies = {"daily", "weekly", "monthly", "yearly"};

    private final String frequency;
    private final int numOccurrences;

    /**
     * Constructs an {@code Occurrence}.
     *
     * @param frequency How often the {@code Transaction}'s clones occur.
     * @param numOccurrences How many times the {@code Transaction}'s clones occur with frequency of {@code frequency}.
     */
    public Occurrence(String frequency, int numOccurrences) {
        requireNonNull(frequency);
        this.frequency = frequency;
        this.numOccurrences = numOccurrences;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getNumOccurrences() {
        return numOccurrences;
    }

    /**
     * Determines whether a {@code frequency String} is a valid {@code frequency} according to
     * {@link Occurrence#validFrequencies}.
     *
     * @param inputFrequency The frequency to be checked for validity.
     * @return true if {@code inputFrequency} is valid, false if invalid.
     */
    public static boolean isValidFrequency(String inputFrequency) {
        for (String vf : validFrequencies) {
            if (vf.equals(inputFrequency)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the {@code Calendar} date field corresponding to the {@code Occurrence}'s {@code frequency}.
     *
     * @return {@code int} value of Calendar field corresponding to Occurrence's frequency.
     * @throws CommandException if {@code frequency} is invalid.
     */
    public int getFrequencyCalendarField() throws CommandException {
        switch (frequency) {
        case "daily":
            return Calendar.DATE;
        case "weekly":
            return Calendar.WEEK_OF_YEAR;
        case "monthly":
            return Calendar.MONTH;
        case "yearly":
            return Calendar.YEAR;
        default:
            throw new CommandException(OCCURRENCE_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Occurrence // instanceof handles nulls
                && frequency.equals(((Occurrence) other).frequency)
                && numOccurrences == ((Occurrence) other).numOccurrences); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(frequency, numOccurrences);
    }

}
