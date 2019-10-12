package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_BUDGET_SUCCESS;

public class SetBudgetCommand extends SetCommand {

    public SetBudgetCommand(double amount) {
        this.amount = amount;
    }

    public CommandResult execute(Model model) {
        model.setBudgetTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_BUDGET_SUCCESS, Double.toString(amount)));
    }
}
