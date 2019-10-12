package seedu.jarvis.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

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
     * Checks if the given {@code StringBuilder} can accommodate its current length, a new
     * token and an extra newline and space, beneath the given limit.
     *
     * @param sb {@code StringBuilder} to check
     * @param token {@code String} to check
     * @param limit length limit of the {@code String}
     * @return true if the result {@code String} can accommodate the limit
     */
    private static boolean canAppend(StringBuilder sb, String token, int limit) {
        int tokenLen = token.length();
        int sbLen = sb.length();
        int newLineLen = "\n".length();
        int spaceLen = " ".length();
        return tokenLen + sbLen + newLineLen + spaceLen <= limit;
    }

    /**
     * Appends the given token to the given {@code StringBuilder}. This method
     * only appends just the token if the {@code StringBuilder} is empty, the token with
     * a preceding a space otherwise.
     */
    private static void appendToken(StringBuilder sb, String token) {
        if (sb.length() == 0) {
            sb.append(token);
        } else {
            sb.append(" " + token);
        }
    }

    /**
     * Appends the given token to the given {@code StringBuilder} if the length of the
     * given {@code StringBuilder} is above the {@code limit}. Appends preceding and
     * subsequent newline if the token is above the limit, appends a newline otherwise.
     *
     * @return true if the given token is above the limit.
     */
    private static boolean appendTokenAboveLimit(StringBuilder sb, String token, int limit) {
        if (token.length() >= limit) {
            sb.append("\n").append(token).append("\n");
            return true;
        } else {
            sb.append("\n");
            return false;
        }
    }

    /**
     * Returns a {@code String} containing the input string, but delimited by {@code \n}
     * after every 80 characters. Words (a {@code CharSequence} delimited by spaces)
     * are not cut halfway and will insert newlines only between spaces.
     *
     * If a word is longer than 80 characters, the word will be put on its own line.
     *
     * This method only guarantees lines shorter than 80 characters if the above scenario
     * does not occur.
     *
     * @param s input string
     * @return an 80 character limited {@code String} per line
     */
    public static String asLimitedCharactersPerLine(String s, int limit) {
        if (s.length() == 0) {
            return "";
        }
        String[] tokens = s.split("\\s+");
        StringBuilder lines = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tokens.length;) {
            if (canAppend(builder, tokens[i], limit)) {
                appendToken(builder, tokens[i]);
                i++;
            } else {
                if (appendTokenAboveLimit(builder, tokens[i], limit)) {
                    i++;
                }
                lines.append(builder.toString());
                builder = new StringBuilder();
            }
        }
        lines.append(builder.toString()); // get last line
        return lines.toString();
    }
}
