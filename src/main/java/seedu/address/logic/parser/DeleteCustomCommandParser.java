package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteCustomCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commands.CommandObject;

/**
 * Parses input arguments and creates a new DeleteCustomCommand object
 */
public class DeleteCustomCommandParser implements Parser<DeleteCustomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCustomCommand
     * and returns a DeleteCustomCommand object for execution.
     * @throws ParseException if the command doesn't exist.
     */
    public DeleteCustomCommand parse(String args) throws ParseException {
        try {
            CommandObject commandToDelete = ParserUtil.parseCommand(args);
            return new DeleteCustomCommand(commandToDelete);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(DeleteCustomCommand.MESSAGE_USAGE, args.trim()), pe);
        }
    }

}
