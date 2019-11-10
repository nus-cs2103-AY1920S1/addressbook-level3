package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.exceptions.SavingsOutOfBoundException;
import seedu.savenus.model.util.TimeStamp;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;
import seedu.savenus.storage.savings.exceptions.InvalidSavingsAmountException;

//@@author fatclarence
/**
 * Saves an amount of money into the user's savings account.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set User's savings\n"
            + "Parameters: SAVING_AMT\n"
            + "Restriction: " + Savings.MESSAGE_CONSTRAINTS + "\n"
            + "Example: " + COMMAND_WORD + " 100";

    public static final String MESSAGE_SAVINGS_SUCCESS = "Added to your Savings Account: $%1$s";

    private final Savings savingsAmount;

    public SaveCommand(String savings) {
        this.savingsAmount = new Savings(savings, TimeStamp.generateCurrentTimeStamp(), false);
    }

    public SaveCommand(String savings, String time) {
        this.savingsAmount = new Savings(savings, time, false);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // deduct from wallet in model
        try {
            // Deposit into savings only possible if the wallet has sufficient funds
            model.depositInSavings(this.savingsAmount);
            model.addToHistory(this.savingsAmount);
        } catch (InsufficientFundsException e) {
            throw new CommandException(e.getMessage() + " to add to savings account!");
        } catch (InvalidSavingsAmountException | SavingsOutOfBoundException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SAVINGS_SUCCESS, savingsAmount.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveCommand // instanceof handles nulls
                && this.savingsAmount.equals(((SaveCommand) other).savingsAmount)); // state check
    }
}
