package seedu.address.logic.finance.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;

import java.util.stream.Stream;

import seedu.address.logic.finance.commands.SpendCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.logentry.Amount;
import seedu.address.model.finance.logentry.Description;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.TransactionDate;

/**
 * Parses input arguments and creates a new SpendCommand object
 */
public class SpendCommandParser implements Parser<SpendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SpendCommand
     * and returns an SpendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SpendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DAY, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DAY, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpendCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        TransactionDate tDate = ParserUtil.parseTransactionDate(argMultimap.getValue(PREFIX_DAY).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());

        LogEntry logEntry = new LogEntry(amount, tDate, description);

        return new SpendCommand(logEntry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
