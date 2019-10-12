package seedu.address.overview.commands;

import seedu.address.overview.model.Model;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_SALES_SUCCESS;

public class SetSalesCommand extends SetCommand {

    public SetSalesCommand(double amount) {
        this.amount = amount;
    }

    public CommandResult execute(Model model) {
        model.setSalesTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_SALES_SUCCESS, Double.toString(amount)));
    }
}
