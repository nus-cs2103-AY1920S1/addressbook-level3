package seedu.deliverymans.logic.parser.restaurant;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.deliverymans.logic.commands.restaurant.AddRatingCommand;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.restaurant.Rating;

/**
 * Parses input arguments and creates a new AddRatingCommand object
 */
public class AddRatingCommandParser implements Parser<AddRatingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddRatingCommand
     * and returns an AddRatingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRatingCommand parse(String args) throws ParseException {
        try {
            Rating rating = ParserUtil.parseRating(args);
            return new AddRatingCommand(rating);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRatingCommand.MESSAGE_USAGE), pe);
        }
    }
}
