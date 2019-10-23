package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    /**
     * Returns true if the string similarity between {@code string1} and {@code string2}
     * is greater than {@code requiredSimilarityPercentage}
     * @throws NullPointerException if {@code string1} or {@code string2} is null.
     */
    public static boolean isSimilarWord(String string1, String string2, Double requiredSimilarityPercentage) {
        double stringSimilarity = calculateStringSimilarity(string1, string2);
        return stringSimilarity >= requiredSimilarityPercentage;
    }

    /**
     * Returns string similarity  between {@code string1} and {@code string2} as a double from 0.0 - 1.0
     * @throws NullPointerException if {@code string1} or {@code string2} is null.
     */
    public static double calculateStringSimilarity(String string1, String string2) {
        requireNonNull(string1);
        requireNonNull(string2);

        Integer longerStringLength = Math.max(string1.length(), string2.length());
        Integer levenshteinDistance = calculateLevenshteinDistance(string1, string2);
        Double similarityPercentage = ((longerStringLength - levenshteinDistance) / (double) longerStringLength);
        return similarityPercentage;
    }

    /**
     * Returns the cost of inserting / deleting / swapping a character. Used in {@code calculateLevenshteinDistance}
     */
    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
    /**
     * Returns the smallest value from a variable amount of int.
     * Returns Integer.MAXVALUE as default value
     */
    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
    /**
     * Returns the Levenshtein Distance (an algorithm to compare the difference between strings)
     * of {@code string1} and {@code string2}
     */
    private static int calculateLevenshteinDistance(String string1, String string2) {
        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        for (int i = 0; i <= string1.length(); i++) {
            for (int j = 0; j <= string2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(string1.charAt(i - 1), string2.charAt(j - 1)),
                            dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[string1.length()][string2.length()];
    }
}
