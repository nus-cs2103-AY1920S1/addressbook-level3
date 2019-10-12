package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_BUDGET_SUCCESS;

public class NotifyBudgetCommand extends NotifyCommand {

    public NotifyBudgetCommand(int amount) {
        this.amount = amount;
    }
    public CommandResult execute(Model model) {

        model.setBudgetThreshold(amount);
        return new CommandResult(String.format(MESSAGE_NOTIFY_BUDGET_SUCCESS, Integer.toString(amount)));
    }
}
