package seedu.deliverymans.logic.parser.restaurant;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.restaurant.DeleteFoodCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFoodCommand object
 */
public class DeleteFoodCommandParser implements Parser<DeleteFoodCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFoodCommand
     * and returns a DeleteFoodCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFoodCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFoodCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFoodCommand.MESSAGE_USAGE), pe);
        }
    }
}
