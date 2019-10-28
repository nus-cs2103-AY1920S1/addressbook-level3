package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddBudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ExpenseList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Name;

/**
 * Parses input arguments and creates a new AddBudgetCommand object
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBudgetCommand
     * and returns an AddBudgetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_CURRENCY, PREFIX_DATE, PREFIX_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_END_DATE)
                    || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Currency currency = ParserUtil.parseCurrency(argMultimap);
        Date startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        Budget budget = new Budget(name, amount, amount, currency, startDate, endDate, new ExpenseList());

        return new AddBudgetCommand(budget);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
