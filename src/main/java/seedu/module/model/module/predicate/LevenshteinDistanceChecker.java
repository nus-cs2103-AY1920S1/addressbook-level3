package seedu.module.model.module.predicate;

import java.util.Arrays;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Compares two strings and checks if they are similar enough to be considered a spelling mistake.
 */
public class LevenshteinDistanceChecker {

    private int tolerance;
    private final LevenshteinDistance ld = LevenshteinDistance.getDefaultInstance();

    public LevenshteinDistanceChecker(int tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * Compares the contents of a list of string and checks if the list contains string with Levenshtein distance
     * within the acceptable tolerance range when compared to the second string.
     *
     * @param other The string being compared to.
     * @param current The string used for comparison to other.
     * @return Boolean result.
     */
    public boolean fuzzyContains(String other, String current) {

        String[] partitionedList = other.split(" ");

        return Arrays.asList(partitionedList)
                .stream().anyMatch(x -> checkWithinLevenshteinDistance(x, current));
    }

    /**
     * Compares two strings and checks if the Levenshtein distance between the two string is within acceptable range.
     *
     * @param other The string being compared to.
     * @param current The string used for comparison to other.
     * @return Boolean result.
     */
    private boolean checkWithinLevenshteinDistance(String other, String current) {
        if (current.length() <= tolerance) {
            return other.equals(current);
        }

        // removes punctuations from the end of a string.
        other = other.replaceAll("\\s*\\p{Punct}+\\s*$", "");
        Integer distance = ld.apply(other, current);
        if (distance <= tolerance) {
            return true;
        }
        return false;
    }

}
