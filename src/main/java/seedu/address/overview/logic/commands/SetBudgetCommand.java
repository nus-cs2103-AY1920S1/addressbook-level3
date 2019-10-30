package seedu.address.overview.logic.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_SET_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_BUDGET_SUCCESS;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
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
    public CommandResult execute(Model model) throws InvalidValueException {

        if (amount < 0) {
            throw new InvalidValueException(MESSAGE_INVALID_SET_AMOUNT);
        }

        model.setBudgetTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_BUDGET_SUCCESS, Double.toString(amount)));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SetBudgetCommand) {
            SetBudgetCommand sbc = (SetBudgetCommand) o;
            if (this.amount == sbc.amount) {
                return true;
            }
        }
        return false;
    }
}
