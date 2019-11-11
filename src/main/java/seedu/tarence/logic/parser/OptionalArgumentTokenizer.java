package seedu.tarence.logic.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Tokenizes arguments in the same way as {@code ArgumentTokenizer}.
 * Additionally, looks out for optional arguments without prefixes, such as matriculation number, NUSNET ID, and email.
 * These are detected via regex pattern matching.<br>
 */
public class OptionalArgumentTokenizer extends ArgumentTokenizer {

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps prefixes to their
     * respective argument values. Only the given prefixes will be recognized in the arguments string.
     * Additionally, detects and maps matriculation number, NUSNET ID, and email fields, even if they come without
     * prefix tags.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to tokenize the arguments string with
     * @return           ArgumentMultimap object that maps prefixes to their arguments
     */
    public static ArgumentMultimap tokenize(String argsString, OptionalArgument[] optionalArgs, Prefix... prefixes) {

        // Step 1: Detect and extract optional arguments not specified by a prefix
        ArgumentMultimap argMultimap = extractOptionalArguments(argsString, optionalArgs);
        Prefix[] optionalPrefixes = Arrays.stream(optionalArgs).map(a -> a.getPrefix()).toArray(Prefix[]::new);
        for (Prefix p : optionalPrefixes) {
            if (argMultimap.getValue(p).isPresent()) {
                argsString = argsString.replace(" " + argMultimap.getValue(p).get(), "");
            }
        }

        // Step 2: Extract required arguments specified by prefix
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, prefixes);
        ArgumentMultimap requiredArgMultimap = extractArguments(argsString, positions);
        for (Prefix p : prefixes) {
            if (requiredArgMultimap.getValue(p).isPresent()) {
                argMultimap.put(p, requiredArgMultimap.getValue(p).get());
            }
        }
        Prefix preamblePrefix = new Prefix("");
        if (requiredArgMultimap.getValue(preamblePrefix).isPresent()) {
            argMultimap.put(preamblePrefix, requiredArgMultimap.getValue(preamblePrefix).get());
        }

        return argMultimap;
    }

    /**
     * Extracts optional arguments defined at the start of the class, if present, from the given string.
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @return           ArgumentMultimap object that maps prefixes to their arguments.
     */
    public static ArgumentMultimap extractOptionalArguments(String argsString, OptionalArgument[] optionalArgs) {
        ArgumentMultimap argMultimap = new ArgumentMultimap();

        for (OptionalArgument optionalArg : optionalArgs) {
            Matcher m = optionalArg.pattern.matcher(argsString);
            if (m.find()) {
                argMultimap.put(optionalArg.prefix, m.group());
            }
        }

        return argMultimap;

    }

}
