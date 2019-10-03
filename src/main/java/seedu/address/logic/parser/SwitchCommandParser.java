package seedu.address.logic.parser;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * (to be added)
 */
public class SwitchCommandParser implements Parser<ClearCommand.SwitchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchCommand
     * and returns a SwitchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ClearCommand.SwitchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.SwitchCommand.MESSAGE_USAGE));
        }

        return new ClearCommand.SwitchCommand(trimmedArgs);
    }
}
