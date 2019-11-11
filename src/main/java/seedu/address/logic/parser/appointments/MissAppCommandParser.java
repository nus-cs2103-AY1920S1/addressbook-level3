//@@author woon17
package seedu.address.logic.parser.appointments;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.appointments.MissAppCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new MissAppCommand object
 */
public class MissAppCommandParser implements Parser<MissAppCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MissAppCommand
     * and returns a MissAppCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MissAppCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MissAppCommand.MESSAGE_USAGE));
        }
        return new MissAppCommand();
    }
}
