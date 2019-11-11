package seedu.deliverymans.logic.parser.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.deliverymans.logic.commands.universal.DeleteOrderCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Name;

/**
 * Parses input arguments and creates a new DeleteOrderCommand object
 */
public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteOrderCommand
     * and returns a DeleteOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()
                    && !ParserUtil.hasRepeatedPrefix(args, PREFIX_NAME)) {
                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
                return new DeleteOrderCommand(name);
            } else {
                throw new ParseException("");
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteOrderCommand.MESSAGE_USAGE), pe);
        }

        /*
        try {
            Name name = ParserUtil.parseName(args);
            return new DeleteOrderCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE), pe);
        }
         */
    }
}
