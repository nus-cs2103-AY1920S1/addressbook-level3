package seedu.address.overview.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_BUDGET_SUCCESS;

import seedu.address.overview.model.Model;

/**
 * Command representing setting of budget target.
 */
public class SetBudgetCommand extends SetCommand {

    public SetBudgetCommand(double amount) {
        this.amount = amount;
    }

    /**
     * Executes the command.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} representing the outcome.
     */
    public CommandResult execute(Model model) {
        model.setBudgetTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_BUDGET_SUCCESS, Double.toString(amount)));
    }
}
