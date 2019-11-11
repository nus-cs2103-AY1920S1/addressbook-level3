package seedu.address.overview.logic.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NOTIFY_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_EXPENSE_SUCCESS;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
import seedu.address.overview.model.Model;
import seedu.address.util.CommandResult;

/**
 * Represents a command to set the percentage value by which to notify the user for expense target.
 */
public class NotifyExpenseCommand extends NotifyCommand {

    public NotifyExpenseCommand (long amount) {
        this.amount = amount;
    }

    /**
     * Executes the command
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} representing the result of executing this commmand.
     */
    public CommandResult execute(Model model) throws InvalidValueException {

        if (amount < 0 || amount > 100) {
            logger.warning("Value not between 0 and 100, throwing exception.");
            throw new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT);
        }
        logger.info("Setting notification threshold for expense to " + amount);

        model.setExpenseThreshold(amount);
        return new CommandResult(String.format(MESSAGE_NOTIFY_EXPENSE_SUCCESS, DECIMAL_FORMAT.format(amount)));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NotifyExpenseCommand) {
            NotifyExpenseCommand nec = (NotifyExpenseCommand) o;
            if (this.amount == nec.amount) {
                return true;
            }
        }
        return false;
    }
}
