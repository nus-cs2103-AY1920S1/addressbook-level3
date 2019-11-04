package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.logic.parser.Option.isValidOption;

/**
 * Simple utility class for tokenize-ing an input String to check for presence
 * of options. Unlike ArgumentTokenizer, which takes any characters after the given prefix
 * as arguments, OptionsTokenizer requires that each option be preceded by a dash, and must
 * be strictly space delimited (or occurs at the end of the String).
 */
public class OptionsTokenizer {

    // private constructor to prevent instantiation
    private OptionsTokenizer() {}

    /**
     * Tokenizes the supplied {@code arg} and returns an {@code OptionsSet}
     * object representing all the parsed options contained in {@code args}
     * @param args argument to be parsed
     * @return {@code OptionsSet} object containing all parsed options
     */
    public static OptionsSet tokenize(String args) {
        requireNonNull(args);
        String[] splitAndTrimmedStr = getTokenizedBySpaceAndTrimmed(args);
        return constructOptionsSet(splitAndTrimmedStr);
    }

    /**
     * Splits a string delimited by blank-space.
     * All leading and trailing spaces will be trimmed.
     * @param args String to be split
     * @return Array of split strings, all trimmed.
     */
    private static String[] getTokenizedBySpaceAndTrimmed(String args) {
        requireNonNull(args);
        String[] splitStr = args.split(" ");
        for (int i = 0; i < splitStr.length; i++) {
            splitStr[i] = splitStr[i].trim();
        }
        return splitStr;
    }

    /**
     * Constructs an {@code OptionsSet} from {@code args}, an array
     * of Strings split by blank space.
     * @param args input string tokenized by blank space (space, newlines, etc.)
     * @return {@code OptionsSet} object containing all parsed {@Option} objects.
     */
    private static OptionsSet constructOptionsSet(String[] args) {
        requireNonNull(args);
        OptionsSet optionsSet = new OptionsSet();
        for (String arg : args) {
            if (isValidOption(arg)) {
                optionsSet.add(new Option(arg.substring(1)));
            }
        }
        return optionsSet;
    }
}
