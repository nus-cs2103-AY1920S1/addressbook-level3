package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AutoAddEarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.earnings.Count;

/**
 * Parses input arguments and creates a new AutoAddEarningsCommand object.
 */
public class AutoAddEarningsCommandParser implements Parser<AutoAddEarningsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AutoAddEarningsCommand
     * and returns an AutoAddEarningsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutoAddEarningsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AutoAddEarningsCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_COUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AutoAddEarningsCommand.MESSAGE_USAGE));
        }

        Count count = ParserUtil.parseCount(argMultimap.getValue(PREFIX_COUNT).get());

        return new AutoAddEarningsCommand(index, count);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
