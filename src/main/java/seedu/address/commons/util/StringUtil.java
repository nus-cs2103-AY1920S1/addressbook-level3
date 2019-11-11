package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
     * Returns a string that is a substring of {@code s}. The returned substring begins at index 0 and
     * extends to the first occurrence of the {@code delimiter} if present and if not, to the end of this string.
     *
     * @param s         The original string to operate on.
     * @param delimiter The delimiter that denotes where the returned substring should end.
     * @return The substring up till the delimiter (if found) or else the original string.
     */
    public static String substringBefore(final String s, final String delimiter) {
        requireAllNonNull(s, delimiter);

        final int delimiterIndex = s.indexOf(delimiter);
        if (delimiterIndex == -1) {
            return s;
        }

        return s.substring(0, delimiterIndex);
    }

    /**
     * Checks if {@code s} is null or is an empty String.
     * @param s string to check.
     * @return true if {@code s} is null or is an empty String.
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    /**
     * This method is used to remove the Nus Prefix in the validLocationName
     * @param locationName
     * @return
     */
    public static String removeNusPrefix(String locationName) {
        if (locationName.contains("NUS_")) {
            return locationName.split("NUS_")[1];
        } else {
            return locationName;
        }
    }
}
