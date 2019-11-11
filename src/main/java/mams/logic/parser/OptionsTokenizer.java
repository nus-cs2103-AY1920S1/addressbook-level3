package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.commons.util.CollectionUtil.requireAllNonNull;
import static mams.logic.parser.Option.isValidOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple utility class to tokenize an input String, and subsequently check for presence
 * of {@code Option} objects. Unlike ArgumentTokenizer, which takes any characters after the given prefix
 * as arguments, OptionsTokenizer requires that each option be preceded by a dash, and must
 * be strictly space delimited (or occurs at the end of the String). In other words, this should be used for
 * simpler command formats that do not require taking in of values, and only need to check for the presence of flags
 * (represented as {@code Option}), as it provides a much cleaner and readable API in such contexts,
 * without the need for obfuscating calls to {@code Optional} for fetching of values
 * just to check for the presence of an option. Since it is optimized for a simpler, space-delimited format,
 * it also provides specialized functions to fetch unrecognized space-delimited arguments.
 */
public class OptionsTokenizer {

    // private constructor to prevent instantiation
    private OptionsTokenizer() {}

    /**
     * Tokenizes the supplied {@code args} and returns an {@code OptionsSet}
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
     * Parses through the supplied {@code args}, split them by whitespace, and
     * returns all arguments that are not in recognizedArguments. Note that trailing and leading
     * whitespace in {@code recognizedArguments} will be ignored during comparison.
     * @param args String to be parsed
     * @param recognizedArguments array or variable length parameters of recognized arguments
     * @return List of all white-space delimited arguments in {@code args} not matching
     * those in {@code recognizedArguments}
     */
    public static List<String> getUnrecognizedArguments(String args, String... recognizedArguments) {
        requireAllNonNull(args, recognizedArguments);
        Set<String> recognizedSet = Stream.of(recognizedArguments).map(String::trim)
                .collect(Collectors.toSet());
        List<String> unrecognizedList = new ArrayList<>();
        String[] tokenizedArgs = getTokenizedBySpaceAndTrimmed(args);
        for (String arg: tokenizedArgs) {
            if (!arg.isBlank() && !recognizedSet.contains(arg)) {
                unrecognizedList.add(arg);
            }
        }
        return unrecognizedList;
    }

    /**
     * Returns all unrecognized parameters. This is a convenient wrapper around
     * {@link #getUnrecognizedArguments(String, String...)} which takes in an {@code Option}
     * array and converts it internally to a String array for comparison.
     * @param args String to be parsed
     * @param recognizedOptions array or variable length parameters of recognized arguments
     * @return List of all white-space delimited arguments in {@code args} not matching
     * those in {@code recognizedArguments}
     */
    public static List<String> getUnrecognizedArguments(String args, Option... recognizedOptions) {
        requireAllNonNull(args, recognizedOptions);
        String[] recognizedStrings = Arrays.stream(recognizedOptions).map(Option::toString)
                .toArray(String[]::new);
        return getUnrecognizedArguments(args, recognizedStrings);
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
