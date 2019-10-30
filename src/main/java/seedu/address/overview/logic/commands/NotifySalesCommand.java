package seedu.address.overview.logic.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NOTIFY_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_SALES_SUCCESS;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
import seedu.address.overview.model.Model;

/**
 * Represents a command to set the percentage value by which to notify the user for expense target.
 */
public class NotifySalesCommand extends NotifyCommand {

    public NotifySalesCommand(int amount) {
        this.amount = amount;
    }

    /**
     * Executes the command
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} representing the result of executing this commmand.
     */
    public CommandResult execute(Model model) throws InvalidValueException {

        if (amount < 0 || amount > 100) {
            throw new InvalidValueException(MESSAGE_INVALID_NOTIFY_AMOUNT);
        }

        model.setSalesThreshold(amount);
        return new CommandResult(String.format(MESSAGE_NOTIFY_SALES_SUCCESS, Double.toString(amount)));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NotifySalesCommand) {
            NotifySalesCommand nsc = (NotifySalesCommand) o;
            if (this.amount == nsc.amount) {
                return true;
            }
        }
        return false;
    }
}
