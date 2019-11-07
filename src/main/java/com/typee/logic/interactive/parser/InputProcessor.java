package com.typee.logic.interactive.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.typee.commons.core.Messages;
import com.typee.logic.parser.exceptions.ParseException;

public class InputProcessor {

    private static final String REGEX_PATTERN_COMMAND_WORD = "[a-zA-Z]+";
    private static final String REGEX_PATTERN_PREFIX = "[a-z]/";

    public Prefix[] extractPrefixes(String commandText) {
        Pattern pattern = Pattern.compile("[a-zA-z]/");
        Matcher matcher = pattern.matcher(commandText);
        List<Prefix> prefixes = getMatches(matcher);

        // Convert to an array to allow the values to be processed by varargs.
        return prefixes.toArray(Prefix[]::new);
    }

    private List<Prefix> getMatches(Matcher matcher) {
        List<Prefix> prefixes = new ArrayList<>();
        while (matcher.find()) {
            prefixes.add(new Prefix(matcher.group()));
        }
        return prefixes;
    }

    public String getCommandWord(String commandText) throws ParseException {
        String trimmedCommandText = commandText.trim();
        List<String> commandWords = getAllCommandWords(trimmedCommandText);

        // If there is no unique command word, throw an exception.
        if (commandWords.size() != 1) {
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        return commandWords.get(0);
    }

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
