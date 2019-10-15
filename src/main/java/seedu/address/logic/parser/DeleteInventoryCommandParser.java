package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.InvIndex;
import seedu.address.logic.commands.DeleteInventoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteInventoryCommandParser implements Parser<DeleteInventoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteInventoryCommand
     * and returns a DeleteInventoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteInventoryCommand parse(String args) throws ParseException {
        try {
            InvIndex index = ParserUtil.parseInvIndex(args);
            return new DeleteInventoryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInventoryCommand.MESSAGE_USAGE), pe);
        }
    }
}
