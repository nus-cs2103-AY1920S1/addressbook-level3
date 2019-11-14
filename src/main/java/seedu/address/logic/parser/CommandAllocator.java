package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a CommandAllocator that is able to call the appropriate Parser
 * depending on the user input and returns a {@code Command} of type {@code T}.
 */
public interface CommandAllocator<T extends Command> {

    /**
     * Parses {@code userInput} to determine which specific parser to call for the specific
     * user command and accordingly returns the corresponding command.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T allocate(String userInput) throws ParseException;
}
