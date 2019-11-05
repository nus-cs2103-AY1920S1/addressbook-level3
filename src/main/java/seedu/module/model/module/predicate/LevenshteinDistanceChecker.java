package seedu.module.model.module.predicate;

import java.util.ArrayList;

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
     * Compares two strings and checks the first string contains substring with Levenshtein distance
     * within the acceptable tolerance range when compared to the second string.
     *
     * @param other The string being compared to.
     * @param current The string used for comparison to other.
     * @return Boolean result.
     */
    public boolean fuzzyContains(String other, String current) {

        ArrayList<String> partitionedList = partitionString(other, current);

        for (String partitionedString : partitionedList) {
            Integer distance = ld.apply(partitionedString, current);
            if (distance <= tolerance) {
                return true;
            }
        }
        Integer distance = ld.apply(other, current);
        return distance <= tolerance;
    }

    /**
     * Partition the first string into an ArrayList of strings, each with the same number of words as the second string.
     *
     * @param other The string being compared to.
     * @param current The string used for comparison to other.
     * @return ArrayList of strings.
     */
    public ArrayList<String> partitionString(String other, String current) {
        ArrayList<String> partitionedString = new ArrayList<>();
        String[] otherWords = other.split(" ");
        int takeCount = current.split(" ").length;

        assert takeCount >= 1 : "partitionString received empty input string";

        for (int i = 0; i < otherWords.length; i++) {
            StringBuilder curr = new StringBuilder()
                    .append(otherWords[i].replaceAll("\\s*\\p{Punct}+\\s*$", ""));
            for (int j = 1; i + j < otherWords.length && j < takeCount; j++) {
                curr.append(" ").append(otherWords[i + j].replaceAll("\\s*\\p{Punct}+\\s*$", ""));
            }
            partitionedString.add(curr.toString());
        }

        return partitionedString;

    }
}
