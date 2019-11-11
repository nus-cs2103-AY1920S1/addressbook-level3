package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_INVALID_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DriverRating;

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_RATING);

        //check all prefix are present
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_RATING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        int taskId = ParserUtil.parseId(argMultimap.getValue(PREFIX_TASK).get());
        int rating = ParserUtil.parseId(argMultimap.getValue(PREFIX_RATING).get());

        if (!DriverRating.isValid(rating)) {
            throw new ParseException(MESSAGE_INVALID_RATING);
        }
        return new DoneCommand(taskId, rating);
    }
}
