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
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and a full word match is not required.
     *   <br>examples:<pre>
     *       containsStringIgnoreCase("ABc def", "abc") == true
     *       containsStringIgnoreCase("ABc def", "DE") == true //Works despite not full word match
     *       containsWordIgnoreCase("ABc def", "EF") == false //Does not contain "EF"
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsStringIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();

        return preppedSentence.contains(preppedWord.toLowerCase());
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
     * Returns true if two strings are equal, or both strings are null.
     * @param a the first string to be compared.
     * @param b the second string to be compared.
     * @return whether two strings are equal, or both strings are null.
     */
    public static boolean equalOrBothNull(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else if (a != null && b != null) {
            return a.equals(b);
        } else {
            return false;
        }
    }

    /**
     * Returns the encrypted Password with asterixs.
     * @param password
     * @return encrypted Password
     */
    public static String passwordToAsterix(String password) {
        int len = password.length();
        return asterix(len);
    }

    /**
     * Returns the encrypted Card Number with asterixs.
     * @param cardNumber
     * @return encrypted card number
     */
    public static String cardNumberToAsterix(String cardNumber) {
        String asterisks = asterix(4) + "-" + asterix(4) + "-" + asterix(4) + "-";
        return asterisks + cardNumber.substring(15, 19);
    }

    /**
     * Returns the encrypted Cvc with asterixs.
     * @return encrypted cvc
     */
    public static String cvcToAsterix() {
        return asterix(3);
    }

    /**
     * Returns a string with the asterix of length length.
     * @param length
     * @return String with asterix of length length
     */
    public static String asterix(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("*");
        }
        return sb.toString();
    }
}
