package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_BUDGET_AMOUNT;

import seedu.moneygowhere.logic.commands.BudgetCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.budget.Budget;

/**
 * parses input arguments and creates a new BudgetCommand object.
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand
     * and returns a BudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        try {
            Budget budget = ParserUtil.parseBudget(args);
            return new BudgetCommand(budget);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_BUDGET_AMOUNT);
        }

    }
}
