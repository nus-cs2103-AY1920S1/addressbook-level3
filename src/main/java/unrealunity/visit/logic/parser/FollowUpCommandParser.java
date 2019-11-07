package unrealunity.visit.logic.parser;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_DAYS;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DAYS;

import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.commons.exceptions.IllegalValueException;
import unrealunity.visit.logic.commands.FollowUpCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code FollowUpCommand} object
 */
public class FollowUpCommandParser implements Parser<FollowUpCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemindCommand}
     * and returns a {@code FollowUpCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FollowUpCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAYS);

        Index index;
        int days;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            days = Integer.parseInt(argMultimap.getValue(PREFIX_DAYS).orElse("7"));
            if (days < 0) {
                throw new NumberFormatException(String.format(MESSAGE_INVALID_DAYS));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FollowUpCommand.MESSAGE_USAGE), ive);
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_DAYS));
        }

        return new FollowUpCommand(index, days);
    }
}
