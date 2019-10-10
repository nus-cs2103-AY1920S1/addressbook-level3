package seedu.address.logic.parser.gui;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SpecificHelpCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SpecificHelpCommand object
 */
public class SpecificHelpCommandParser implements Parser<SpecificHelpCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * SpecificHelpCommand and returns an SpecificHelpCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SpecificHelpCommand parse(String args) throws ParseException {
        String commandName = args.trim();
        if (commandName.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpecificHelpCommand.MESSAGE_USAGE));
        }
        return new SpecificHelpCommand(commandName);
    }

}
