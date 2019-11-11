package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DUR;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_END;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_MONTH;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_START;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;

import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.budget.Budget;

/**
 * Adds an entry of expenditure to the finance log.
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in the finance log.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a budget. \n"
            + "Parameters Example 1: budget "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_START + "START_DATE "
            + PREFIX_END + "END_DATE "
            + "[" + PREFIX_TRANSACTION_METHOD + "TRANSACTION_METHOD]\n"
            + "Parameters Example 2: budget "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_START + "START_DATE"
            + PREFIX_DUR + "DURATION_IN_DAYS"
            + "[" + PREFIX_CATEGORY + "CATEGORY]\n"
            + "Parameters Example 3: budget"
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_MONTH + "MM-YYYY "
            + "[" + PREFIX_PLACE + "PLACE]\n"
            + "Example: budget"
            + PREFIX_AMOUNT + "300 "
            + PREFIX_START + "03-11-2019 "
            + PREFIX_END + "11-11-2019 "
            + PREFIX_TRANSACTION_METHOD + " cash\n";

    public static final String MESSAGE_SUCCESS = "New budget added: %1$s \n";

    private final Budget toAdd;

    /**
     * Creates an BudgetCommand to add the specified {@code Budget}
     */
    public BudgetCommand(Budget budget) {
        requireNonNull(budget);
        toAdd = budget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasBudget(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }
        model.addBudget(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && toAdd.equals(((BudgetCommand) other).toAdd));
    }
}
