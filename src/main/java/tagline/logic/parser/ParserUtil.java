//@@author tanlk99
package tagline.logic.parser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A class containing utility static functions for parser classes.
 */
public class ParserUtil {
    /**
     * Parses an argument {@code String} and a {@code List} of {@code Prompt} objects, to produce
     * a {@code String} including the prompt responses.
     */
    public static String getArgsWithFilledPrompts(String args, List<Prompt> filledPrompts) {
        StringBuilder argsBuilder = new StringBuilder(args);
        for (Prompt prompt : filledPrompts) {
            if (prompt.getArgumentPrefix().isEmpty()) {
                argsBuilder.insert(0, " ").insert(0, prompt.getPromptResponse());
            } else {
                argsBuilder.append(" ").append(prompt.getArgumentPrefix())
                        .append(" ").append(prompt.getPromptResponse());
            }
        }

        return argsBuilder.toString();
    }

    /**
     * Gets the response corresponding to an argument prefix from a list of {@code Prompt} objects.
     * If no such prompt exists, returns an empty string.
     */
    public static String getPromptResponseFromPrefix(String prefix, List<Prompt> filledPrompts) {
        Optional<Prompt> promptWithPrefix = filledPrompts.stream()
                .filter(prompt -> prompt.getArgumentPrefix().equals(prefix))
                .findFirst();

        //If empty, no prompt was given
        if (!promptWithPrefix.isPresent()) {
            return "";
        }

        return promptWithPrefix.get().getPromptResponse();
    }

    //@@author
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean allPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
