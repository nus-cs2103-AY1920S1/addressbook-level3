package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteVehicleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and returns a DeleteVehicleCommand for execution.
 */
public class DeleteVehicleCommandParser implements Parser<DeleteVehicleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteVehicleCommand
     * and returns a DeleteVehicleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteVehicleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteVehicleCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVehicleCommand.MESSAGE_USAGE), pe);
        }
    }
}
