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
            + "Parameters: BUDGET_AMT, BUDGET_DURATION\n"
            + "Restriction: " + RemainingBudget.MESSAGE_CONSTRAINTS + "\n"
            + DaysToExpire.MESSAGE_CONSTRAINTS + "\n"
            + "Example: " + COMMAND_WORD + " 100 30 or " + COMMAND_WORD + " 150.50 10";

    public static final String MESSAGE_SET_REMAININGBUDGET_SUCCESS = "New Budget: %1$s";
    public static final String MESSAGE_SET_DAYSTOEXPIRE_SUCCESS = "Number of days left: %2$s";
    public static final String MESSAGE_SET_BUDGET_SUCCESS = MESSAGE_SET_REMAININGBUDGET_SUCCESS
            + "\n"
            + MESSAGE_SET_DAYSTOEXPIRE_SUCCESS;

    private final RemainingBudget newRemainingBudget;
    private final DaysToExpire newDaysToExpire;

    public BudgetCommand(Wallet newWallet) {
        this.newRemainingBudget = newWallet.getRemainingBudget();
        this.newDaysToExpire = newWallet.getDaysToExpire();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (newRemainingBudget.getRemainingBudget() >= 1000000) {
            throw new CommandException(RemainingBudget.FLOATING_POINT_CONSTRAINTS);
        }
        if (newDaysToExpire.getDaysToExpire() > 365) {
            throw new CommandException(DaysToExpire.INTEGER_CONSTRAINTS);
        }
        model.setRemainingBudget(newRemainingBudget);
        model.setDaysToExpire(newDaysToExpire);

        return new CommandResult(String.format(MESSAGE_SET_BUDGET_SUCCESS,
                newRemainingBudget.toString(), newDaysToExpire.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && newRemainingBudget.equals(((BudgetCommand) other).newRemainingBudget) // state check
                && newDaysToExpire.equals(((BudgetCommand) other).newDaysToExpire)); // state check
    }
}
