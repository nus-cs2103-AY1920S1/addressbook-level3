package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_EXPENSE_SUCCESS;

public class SetExpenseCommand extends SetCommand {

    public SetExpenseCommand(double amount) {
        this.amount = amount;
    }

    public CommandResult execute(Model model) {
        model.setExpenseTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_EXPENSE_SUCCESS, Double.toString(amount)));
    }
}
