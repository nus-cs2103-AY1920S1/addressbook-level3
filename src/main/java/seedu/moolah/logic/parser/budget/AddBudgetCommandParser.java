package seedu.moolah.logic.parser.budget;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Collections;
import java.util.List;

import seedu.moolah.logic.commands.budget.AddBudgetCommand;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

/**
 * Parses input arguments and creates a new AddBudgetCommand object.
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_START_DATE, PREFIX_PERIOD
    ));

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of());

    /**
     * Parses the given {@code String} of arguments in the context of the AddBudgetCommand
     * and returns an AddBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_START_DATE, PREFIX_PERIOD, PREFIX_PRICE);

        if (!argMultimap.arePrefixesPresent(PREFIX_DESCRIPTION, PREFIX_START_DATE, PREFIX_PERIOD, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
        }

        if (argMultimap.hasRepeatedPrefixes(PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_START_DATE, PREFIX_PERIOD)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Price amount = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Timestamp startDate = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_START_DATE).get()).toStartOfDay();
        BudgetPeriod period = ParserUtil.parsePeriod(argMultimap.getValue(PREFIX_PERIOD).get());

        Budget budget = new Budget(description, amount, startDate, period);

        return new AddBudgetCommand(budget);
    }

}

