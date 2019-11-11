package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;

/**
 * Sets the budget of the MoneyGoWhere list.
 */
public class BudgetCommand extends Command {
    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_SUCCESS = "Budget has been updated!\n"
            + "New budget is ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the monthly budget specified by the amount given by the user input.\n"
            + "Parameters: value (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 10000";

    private Budget budget;

    //Budget must be valid
    public BudgetCommand(Budget budget) {
        requireNonNull(budget);
        this.budget = budget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Currency currencyInUse = model.getCurrencyInUse();

        double budgetAmount = budget.getAmount();
        String budgetString = budget.toString();

        if (!model.getCurrencyInUse().name.equalsIgnoreCase("SGD")) {
            budget = new Budget(budgetAmount / currencyInUse.rate);
        }

        model.setBudgetAmount(budget);

        return new CommandResult(MESSAGE_SUCCESS + currencyInUse.symbol + budgetString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetCommand
            && budget.equals(((BudgetCommand) other).budget));
    }
}
