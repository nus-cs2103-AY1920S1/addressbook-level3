package seedu.deliverymans.logic.parser.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.deliverymans.logic.commands.universal.AssignOrderCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Name;

/**
 * Parses input arguments and creates a new AssignOrderCommand object
 */
public class AssignOrderCommandParser implements Parser<AssignOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignOrderCommand
     * and returns a AssignOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()
                    && !ParserUtil.hasRepeatedPrefix(args, PREFIX_NAME)) {
                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
                return new AssignOrderCommand(name);
            } else {
                throw new ParseException("");
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignOrderCommand.MESSAGE_USAGE), pe);
        }

        /*
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AssignOrderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignOrderCommand.MESSAGE_USAGE), pe);
        }
         */
    }
}
