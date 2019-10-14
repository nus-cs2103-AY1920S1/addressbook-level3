package seedu.address.overview.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_EXPENSE_SUCCESS;

import seedu.address.overview.model.Model;

/**
 * Command representing setting of expense target.
 */
public class SetExpenseCommand extends SetCommand {

    public SetExpenseCommand(double amount) {
        this.amount = amount;
    }

    /**
     * Executes the command.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} representing the outcome.
     */
    public CommandResult execute(Model model) {
        model.setExpenseTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_EXPENSE_SUCCESS, Double.toString(amount)));
    }
}
