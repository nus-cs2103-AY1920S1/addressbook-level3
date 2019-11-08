package com.typee.logic.interactive.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.typee.commons.core.Messages;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Processes input {@code Strings} to find prefixes and command words.
 */
public class InputProcessor {

    private static final String REGEX_PATTERN_COMMAND_WORD = "[a-zA-Z]+";
    private static final String REGEX_PATTERN_PREFIX = "[a-z]/";

    /**
     * Extracts and returns the list of prefixes in the user entered input.
     * Returns the prefixes in their order of appearance.
     *
     * @param commandText User entered input.
     * @return array of prefixes in their order of appearance.
     */
    public Prefix[] extractPrefixes(String commandText) {
        Pattern pattern = Pattern.compile("[a-zA-z]/");
        Matcher matcher = pattern.matcher(commandText);
        List<Prefix> prefixes = getMatches(matcher);

        // Convert to an array to allow the values to be processed by varargs.
        return prefixes.toArray(Prefix[]::new);
    }

    /**
     * Returns the list of prefixes matched by the input regular expression {@code Matcher}.
     *
     * @param matcher {@code Matcher} containing a text input and a regular expression {@code Prefix} format to match.
     * @return list of matched prefixes.
     */
    private List<Prefix> getMatches(Matcher matcher) {
        List<Prefix> prefixes = new ArrayList<>();
        while (matcher.find()) {
            prefixes.add(new Prefix(matcher.group()));
        }
        return prefixes;
    }

    /**
     * Returns the command word contained in a text input.
     *
     * @param commandText User entered input.
     * @return command word.
     * @throws ParseException If there is no unique command word in the text input.
     */
    public String getCommandWord(String commandText) throws ParseException {
        String trimmedCommandText = commandText.trim();
        List<String> commandWords = getAllCommandWords(trimmedCommandText);

        // If there is no unique command word, throw an exception.
        if (commandWords.size() != 1) {
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        return commandWords.get(0);
    }

    /**
     * Returns the list of command words present in a {@code String}.
     *
     * @param trimmedCommandText Trimmed text input.
     * @return list of command words.
     */
    private List<String> getAllCommandWords(String trimmedCommandText) {
        int boundary = findBoundary(trimmedCommandText);
        Pattern pattern = Pattern.compile(REGEX_PATTERN_COMMAND_WORD);
        Matcher matcher = pattern.matcher(trimmedCommandText.substring(0, boundary));
        List<String> commandWords = new ArrayList<>();
        while (matcher.find()) {
            commandWords.add(matcher.group());
        }
        return commandWords;
    }

    /**
     * Finds and returns the index of the first {@code Prefix} that appears in the input {@code String}.
     * Returns the length of the {@code String} if no {@code Prefix} is present.
     *
     * @param trimmedCommandText Trimmed text input.
     * @return index of the first {@code Prefix}.
     */
    private int findBoundary(String trimmedCommandText) {
        Pattern pattern = Pattern.compile(REGEX_PATTERN_PREFIX);
        Matcher matcher = pattern.matcher(trimmedCommandText);
        boolean prefixExists = matcher.find();
        if (prefixExists) {
            return matcher.start();
        }
        return trimmedCommandText.length();
    }

}
