package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenize arguments string according to its patterns.
 */
public class ArgumentTokenizer {

    /**
     * Tokenize an arguments string and returns an {@code ArgumentMultimap} object that maps patterns to their
     * respective argument values. Only the given patterns will be recognized in the arguments string.
     *
     * @param argsString Arguments string
     * @param patterns   Patterns to tokenize the arguments string with
     * @return ArgumentMultimap object that maps patterns to their arguments
     */
    public static ArgumentMultimap tokenize(String argsString, Pattern... patterns) {
        ArgumentMultimap argMultimap = new ArgumentMultimap();

        for (Pattern p : patterns) {
            Matcher m = p.matcher(argsString);
            while (m.find()) {
                argMultimap.put(p, m.group());
            }
        }
        return argMultimap;
    }
}
