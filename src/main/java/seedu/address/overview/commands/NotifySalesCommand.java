package seedu.address.overview.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_SALES_SUCCESS;

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
    public CommandResult execute(Model model) {
        model.setSalesThreshold(amount);
        return new CommandResult(String.format(MESSAGE_NOTIFY_SALES_SUCCESS, Integer.toString(amount)));
    }
}
