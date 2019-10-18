package seedu.deliverymans.logic.parser.restaurant;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.restaurant.EditRestaurantCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new EditRestaurantCommand object
 */
public class EditRestaurantCommandParser implements Parser<EditRestaurantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRestaurantCommand
     * and returns a EditRestaurantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRestaurantCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EditRestaurantCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRestaurantCommand.MESSAGE_USAGE), pe);
        }
    }
}
