package seedu.elisa.logic.parser;

import seedu.elisa.logic.commands.Command;
import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String description, String userInput) throws ParseException;
}
