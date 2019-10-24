package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import java.util.stream.Stream;

import seedu.jarvis.logic.commands.finance.SetMonthlyLimitCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.financetracker.MonthlyLimit;

/**
 * Parses input arguments and creates a new SetMonthlyLimitCommand object
 */
public class SetMonthlyLimitCommandParser implements Parser<SetMonthlyLimitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of SetMonthlyLimitCommand
     * and returns an SetMonthlyLimitCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetMonthlyLimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONEY);
        if (!arePrefixesPresent(argMultimap, PREFIX_MONEY)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetMonthlyLimitCommand.MESSAGE_USAGE));
        }

        MonthlyLimit limitAmount = FinanceParserUtil.parseMonthlyLimit(argMultimap.getValue(PREFIX_MONEY).get());

        return new SetMonthlyLimitCommand(limitAmount);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
