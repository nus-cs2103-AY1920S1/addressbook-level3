package seedu.ichifund.logic.parser.budget;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.ichifund.logic.commands.budget.AddBudgetCommand;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.Prefix;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;

/**
 * Parses input arguments and creates a new AddBudgetCommand object
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBudgetCommand
     * and returns an AddBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_AMOUNT,
                        PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Month month = null;
        Year year = null;
        Category category = null;

        if (argMultimap.getValue(PREFIX_MONTH).isPresent() && argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
            year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        }

        Budget budget = new Budget(description, amount, month, year, category);

        return new AddBudgetCommand(budget);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
