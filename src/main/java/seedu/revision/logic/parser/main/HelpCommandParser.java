package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.revision.logic.commands.main.HelpCommand;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public static final String MESSAGE_ADDITIONAL_COMMAND_BEHIND = "Unexpected trailing command!";

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {

        if (!args.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_ADDITIONAL_COMMAND_BEHIND));
        }
        return new HelpCommand();
    }
}
