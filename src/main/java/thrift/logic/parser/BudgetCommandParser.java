package thrift.logic.parser;

import java.util.Calendar;

import thrift.commons.core.Messages;
import thrift.logic.commands.BudgetCommand;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetValue;

/**
 * Parses input arguments and creates a new BudgetCommand object.
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand and returns an BudgetCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public BudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_VALUE, CliSyntax.PREFIX_DATE);

        if (argMultimap.getSingleValue(CliSyntax.PREFIX_VALUE).isEmpty()
                || argMultimap.getSingleValue(CliSyntax.PREFIX_DATE).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    BudgetCommand.MESSAGE_USAGE));
        }

        Calendar monthYear = ParserUtil.parseDate(argMultimap.getSingleValue(CliSyntax.PREFIX_DATE).get());
        BudgetValue value = ParserUtil.parseBudgetValue(argMultimap.getSingleValue(CliSyntax.PREFIX_VALUE).get());
        Budget budget = new Budget(monthYear, value);
        return new BudgetCommand(budget);
    }
}
