package tagline.logic.parser;

import java.util.List;
import java.util.Optional;

/**
 * A class containing utility static functions for parser classes when handling prompts.
 */
public class ParserPromptHandlerUtil {
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
}
