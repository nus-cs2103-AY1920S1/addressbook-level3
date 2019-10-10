package seedu.savenus.logic.commands;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.wallet.Budget;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.Wallet;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a food identified using it's displayed index from the menu.
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set User's budget\n"
            + "Parameters: BUDGET_AMT, BUDGET_DURATION"
            + "(Budget amount should only contain numbers and have either 0 or 2 decimal places.)\n"
            + "(Budget duration should be a positive integer.)\n"
            + "Example: " + COMMAND_WORD + " 100 30 or " + COMMAND_WORD + " 150.50 10";

    public static final String MESSAGE_SET_BUDGET_SUCCESS = "New Budget: %1$s";
    public static final String MESSAGE_SET_DAYSTOEXPIRE_SUCCESS = "Number of days left: %1$s";

    private final Budget newBudget;
    private final DaysToExpire newDaysToExpire;

    public BudgetCommand(Wallet newWallet) {
        this.newBudget = newWallet.getBudget();
        this.newDaysToExpire = newWallet.getDaysToExpire();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setBudget(newBudget);
        model.setDaysToExpire(newDaysToExpire);

        return new CommandResult(String.format(MESSAGE_SET_BUDGET_SUCCESS, newBudget.toString())
                + "\n"
                + String.format(MESSAGE_SET_DAYSTOEXPIRE_SUCCESS, newDaysToExpire.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && newBudget.equals(((BudgetCommand) other).newBudget)); // state check
    }
}
