package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.exceptions.InsufficientSavingsException;
import seedu.savenus.model.util.TimeStamp;
import seedu.savenus.model.wallet.exceptions.BudgetAmountOutOfBoundsException;

//@@author fatclarence
/**
 * Withdraw a sum of money from the user's savings account.
 */
public class WithdrawCommand extends Command {

    public static final String COMMAND_WORD = "withdraw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Withdraw from User's savings\n"
            + "Parameters: WITHDRAWAL_AMOUNT\n"
            + "Restriction: " + Savings.MESSAGE_CONSTRAINTS + "\n"
            + "Example: " + COMMAND_WORD + " 100";

    public static final String MESSAGE_WITHDRAW_SUCCESS = "Withdrawn from your Savings Account: $%1$s";

    private final Savings withdrawalAmount; // withdrawal is a negative saving.

    public WithdrawCommand(String savings) {
        this.withdrawalAmount = new Savings(savings,
                TimeStamp.generateCurrentTimeStamp(), true);

    }

    public WithdrawCommand(String savings, String time) {
        this.withdrawalAmount = new Savings(savings, time, true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.withdrawFromSavings(this.withdrawalAmount); // after this command, money value is negative.
            model.addToHistory(this.withdrawalAmount);
        } catch (BudgetAmountOutOfBoundsException | InsufficientSavingsException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_WITHDRAW_SUCCESS,
                this.withdrawalAmount.getSavingsAmount().getAmount().abs()));
    }
}
