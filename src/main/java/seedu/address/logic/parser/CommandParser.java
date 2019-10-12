package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a CommandParser that is able to parse user input into a {@code Command} of type {@code T}.
 *
 * All CommandParsers must have a no-argument constructor.
 */
public interface CommandParser<T extends Command> {
    /**
     * Gets the command name.
     *
     * @return The command name
     */
    String name();

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
