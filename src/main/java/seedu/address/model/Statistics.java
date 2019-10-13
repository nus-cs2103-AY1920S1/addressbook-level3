package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a statistics object.
 */
public class Statistics {

    public final double correct;
    public final double incorrect;
    public final List<Double> statistics;

    public Statistics() {
        this.correct = 0;
        this.incorrect = 0;
        this.statistics = new ArrayList<>();
    }

    public Statistics(double correct, double incorrect) {
        requireNonNull(correct);
        requireNonNull(incorrect);
        this.correct = correct;
        this.incorrect = incorrect;
        this.statistics = new ArrayList<>();
        statistics.add(correct);
        statistics.add(incorrect);
    }

    public boolean hasStatistics() {
        return !(correct == 0 && incorrect == 0);
    }

    public List<Double> getStatistics() {
        return statistics;
    }

    @Override
    public String toString() {
        return "Correct: " + correct + "\nIncorrect: " + incorrect;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && correct == ((Statistics) other).correct
                && incorrect == ((Statistics) other).incorrect); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(correct, incorrect);
    }
}
