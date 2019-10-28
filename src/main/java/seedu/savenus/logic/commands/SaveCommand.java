package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;

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
        this.savingsAmount = new Savings(savings);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // deduct from wallet in model
        try {
            model.deductFromWallet(this.savingsAmount);
        } catch (InsufficientFundsException e) {
            throw new CommandException(e.getMessage() + " to add to savings account!");
        }

        // add to the savings account in the model.
        model.addToSavings(this.savingsAmount);

        return new CommandResult(String.format(MESSAGE_SAVINGS_SUCCESS, savingsAmount.toString()));
    }
}
