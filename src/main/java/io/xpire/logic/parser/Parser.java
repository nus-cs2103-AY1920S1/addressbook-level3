package io.xpire.logic.parser;

import io.xpire.logic.commands.Command;
import io.xpire.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {
    String SEPARATOR = "\\|";
    String MULTIPLE_SEPARATOR = "\\|+$";

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format.
     */
    T parse(String userInput) throws ParseException;
}
