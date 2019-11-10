package seedu.address.overview.logic.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NOTIFY_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_BUDGET_SUCCESS;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
import seedu.address.overview.model.Model;

/**
 * Represents a command to set the percentage value by which to notify the user for budget target.
 */
public class NotifyBudgetCommand extends NotifyCommand {

    public NotifyBudgetCommand(long amount) {
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

        logger.info("Setting notification threshold for budget to " + amount);
        model.setBudgetThreshold(amount);
        return new CommandResult(String.format(MESSAGE_NOTIFY_BUDGET_SUCCESS, DECIMAL_FORMAT.format(amount)));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NotifyBudgetCommand) {
            NotifyBudgetCommand nbc = (NotifyBudgetCommand) o;
            if (this.amount == nbc.amount) {
                return true;
            }
        }
        return false;
    }
}
