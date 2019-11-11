package seedu.address.logic.finance.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DUR;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_END;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_MONTH;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_START;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;

import java.util.Date;

import seedu.address.logic.finance.commands.BudgetCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.budget.Budget;

/**
 * Parses input arguments and creates a new BudgetCommand object
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand
     * and returns an BudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_START, PREFIX_END,
                        PREFIX_DUR, PREFIX_MONTH, PREFIX_TRANSACTION_METHOD,
                        PREFIX_CATEGORY, PREFIX_PLACE);

        if (!isValidPrefixCombi(argMultimap)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Date startDate;
        Date endDate;
        if (isPrefixPresent(argMultimap, PREFIX_START)) {
            startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START).get());
            if (isPrefixPresent(argMultimap, PREFIX_END)) {
                // <end>
                endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END).get());
            } else {
                // <dur>
                long duration = ParserUtil.parseDur(argMultimap.getValue(PREFIX_DUR).get());
                endDate = new Date(startDate.getTime() + duration);
            }
        } else {
            // <month>
            startDate = ParserUtil.parseMonthStart(argMultimap.getValue(PREFIX_MONTH).get());
            endDate = ParserUtil.parseMonthEnd(argMultimap.getValue(PREFIX_MONTH).get());
        }
        if (endDate.before(startDate)) {
            throw new ParseException("End date should not be before start date.");
        }

        String budgetType;
        String budgetTypeValue;
        if (isPrefixPresent(argMultimap, PREFIX_TRANSACTION_METHOD)) {
            budgetType = "met";
            budgetTypeValue = ParserUtil
                    .parseTransactionMethod(
                            argMultimap.getValue(PREFIX_TRANSACTION_METHOD)
                                    .get()).value;
        } else if (isPrefixPresent(argMultimap, PREFIX_CATEGORY)) {
            budgetType = "cat";
            budgetTypeValue = ParserUtil
                    .parseCategory(
                            argMultimap.getValue(PREFIX_CATEGORY)
                                    .get()).catName;
        } else if (isPrefixPresent(argMultimap, PREFIX_PLACE)) {
            budgetType = "place";
            budgetTypeValue = ParserUtil
                    .parsePlace(argMultimap.getValue(PREFIX_PLACE)
                            .get()).value;
        } else {
            budgetType = "all";
            budgetTypeValue = null;
        }

        Budget budget = new Budget(amount, startDate, endDate, budgetType, budgetTypeValue);

        return new BudgetCommand(budget);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isValidPrefixCombi(ArgumentMultimap argumentMultimap) {
        boolean isStartPresent = isPrefixPresent(argumentMultimap, PREFIX_START);
        boolean isEndPresent = isPrefixPresent(argumentMultimap, PREFIX_END);
        boolean isDurPresent = isPrefixPresent(argumentMultimap, PREFIX_DUR);
        boolean isMonthPresent = isPrefixPresent(argumentMultimap, PREFIX_MONTH);

        if (!isPrefixPresent(argumentMultimap, PREFIX_AMOUNT)) {
            return false;
        }
        if (!isStartPresent && !isMonthPresent) {
            return false;
        }
        // If <start> is present (paired with either <end> or <dur> but not both)
        if (isStartPresent) {
            if ((!isEndPresent && !isDurPresent)
                    || (isEndPresent && isDurPresent)) {
                return false;
            }
        }
        // If <month> is present (<start>, <end>, <dur> not allowed)
        if (isMonthPresent && (isEndPresent || isDurPresent)) {
            return false;
        }
        return true;
    }

    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix p) {
        return argumentMultimap.getValue(p).isPresent();
    }

}
