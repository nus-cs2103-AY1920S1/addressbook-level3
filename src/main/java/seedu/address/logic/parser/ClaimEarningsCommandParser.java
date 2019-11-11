package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClaimEarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.earnings.Claim;

/**
 * Parses input arguments and creates a new ClaimEarningsCommand object
 */
public class ClaimEarningsCommandParser implements Parser<ClaimEarningsCommand> {

    /**
     * Parses the given {@code String} of arguments
     * in the context of the ClaimEarningsCommand
     * and returns an ClaimEarningsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClaimEarningsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_CLAIM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClaimEarningsCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_CLAIM)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClaimEarningsCommand.MESSAGE_USAGE));
        }

        Claim claim = ParserUtil.parseClaim(argMultimap.getValue(PREFIX_CLAIM).get());

        return new ClaimEarningsCommand(index, claim);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
