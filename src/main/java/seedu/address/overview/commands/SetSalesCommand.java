package seedu.address.overview.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_SALES_SUCCESS;

import seedu.address.overview.model.Model;

/**
 * Command representing setting of sales target.
 */
public class SetSalesCommand extends SetCommand {

    public SetSalesCommand(double amount) {
        this.amount = amount;
    }

    /**
     * Executes the command.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} representing the outcome.
     */
    public CommandResult execute(Model model) {
        model.setSalesTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_SALES_SUCCESS, Double.toString(amount)));
    }
}
