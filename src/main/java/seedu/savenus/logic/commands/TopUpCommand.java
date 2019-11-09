package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.exceptions.BudgetAmountOutOfBoundsException;

//@@author raikonen
/**
 * Command to top up a {@code Wallet} for the user.
 */
public class TopUpCommand extends Command {

    public static final String COMMAND_WORD = "topup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Top up User's remaining budget\n"
            + "Parameters: BUDGET_AMT\n"
            + "Restriction: " + RemainingBudget.MESSAGE_CONSTRAINTS + "\n"
            + "Example: " + COMMAND_WORD + " 100";

    public static final String MESSAGE_TOPUP_BUDGET_SUCCESS = "Top Up Successful! New budget: %1$s";

    private final BigDecimal topUpAmount;

    public TopUpCommand(BigDecimal topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        RemainingBudget newRemainingBudget = new RemainingBudget(model.getWallet().getRemainingBudget()
                .getRemainingBudgetAmount().add(topUpAmount).toString());
        try {
            model.getWallet().setRemainingBudget(newRemainingBudget);
        } catch (BudgetAmountOutOfBoundsException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_TOPUP_BUDGET_SUCCESS,
                newRemainingBudget.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopUpCommand // instanceof handles nulls
                && topUpAmount.equals(((TopUpCommand) other).topUpAmount)); // state check
    }
}
