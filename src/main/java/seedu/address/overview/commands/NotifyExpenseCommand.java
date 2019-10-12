package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_EXPENSE_SUCCESS;

public class NotifyExpenseCommand extends NotifyCommand {

    public NotifyExpenseCommand (int amount) {
        this.amount = amount;
    }

    public CommandResult execute(Model model) {
        model.setExpenseThreshold(amount);
        return new CommandResult(String.format(MESSAGE_NOTIFY_EXPENSE_SUCCESS, Integer.toString(amount)));
    }
}
