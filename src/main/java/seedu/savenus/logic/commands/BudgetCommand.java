package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;


/**
 * Command to add a {@code Wallet} for the user.
 * TODO Make Days to Expire optional
 * TODO Fix existing tests
 * TODO Add tests for the Budget Command
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set User's remaining budget\n"
            + "Parameters: BUDGET_AMT, BUDGET_DURATION"
            + "(Budget amount should only contain numbers and have either 0 or 2 decimal places.)\n"
            + "(Budget duration should be a positive integer.)\n"
            + "Example: " + COMMAND_WORD + " 100 30 or " + COMMAND_WORD + " 150.50 10";

    public static final String MESSAGE_SET_REMAININGBUDGET_SUCCESS = "New Budget: %1$s";
    public static final String MESSAGE_SET_DAYSTOEXPIRE_SUCCESS = "Number of days left: %1$s";

    private final RemainingBudget newRemainingBudget;
    private final DaysToExpire newDaysToExpire;

    public BudgetCommand(Wallet newWallet) {
        this.newRemainingBudget = newWallet.getRemainingBudget();
        this.newDaysToExpire = newWallet.getDaysToExpire();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setRemainingBudget(newRemainingBudget);
        model.setDaysToExpire(newDaysToExpire);

        return new CommandResult(String.format(MESSAGE_SET_REMAININGBUDGET_SUCCESS, newRemainingBudget.toString())
                + "\n"
                + String.format(MESSAGE_SET_DAYSTOEXPIRE_SUCCESS, newDaysToExpire.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && newRemainingBudget.equals(((BudgetCommand) other).newRemainingBudget) // state check
                && newDaysToExpire.equals(((BudgetCommand) other).newDaysToExpire)); // state check
    }
}
