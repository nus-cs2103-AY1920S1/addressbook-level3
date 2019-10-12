package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_SALES_SUCCESS;

public class NotifySalesCommand extends NotifyCommand {

    public NotifySalesCommand(int amount) {
        this.amount = amount;
    }

    public CommandResult execute(Model model) {
        model.setSalesThreshold(amount);
        return new CommandResult(String.format(MESSAGE_NOTIFY_SALES_SUCCESS, Integer.toString(amount)));
    }
}
