package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.model.wallet.exceptions.BudgetAmountOutOfBoundsException;
import seedu.savenus.model.wallet.exceptions.BudgetDurationOutOfBoundsException;

//@@author raikonen
/**
 * Command to add a {@code Wallet} for the user.
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set User's remaining budget\n"
            + "Parameters: BUDGET_AMT, BUDGET_DURATION\n"
            + "Restriction: " + RemainingBudget.MESSAGE_CONSTRAINTS + "\n"
            + DaysToExpire.MESSAGE_CONSTRAINTS + "\n"
            + "Example: " + COMMAND_WORD + " 100 30 or " + COMMAND_WORD + " 150.50 10";

    public static final String MESSAGE_SET_REMAININGBUDGET_SUCCESS = "New budget: %1$s";
    public static final String MESSAGE_SET_DAYSTOEXPIRE_SUCCESS = "Number of days left: %2$s";
    public static final String MESSAGE_SET_BUDGET_SUCCESS = MESSAGE_SET_REMAININGBUDGET_SUCCESS
            + "\n"
            + MESSAGE_SET_DAYSTOEXPIRE_SUCCESS;

    private final Wallet newWallet;

    public BudgetCommand(Wallet newWallet) {
        this.newWallet = newWallet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.setWallet(newWallet);
        } catch (BudgetAmountOutOfBoundsException | BudgetDurationOutOfBoundsException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(newWallet.getNumberOfDaysToExpire() == 0
                ? String.format(MESSAGE_SET_REMAININGBUDGET_SUCCESS,
                newWallet.getRemainingBudget().toString())
                : String.format(MESSAGE_SET_BUDGET_SUCCESS,
                newWallet.getRemainingBudget().toString(), newWallet.getDaysToExpire().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && newWallet.equals(((BudgetCommand) other).newWallet)); // state check
    }
}
