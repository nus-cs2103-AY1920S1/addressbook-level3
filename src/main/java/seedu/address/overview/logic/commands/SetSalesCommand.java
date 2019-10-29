package seedu.address.overview.logic.commands;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_SET_AMOUNT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_SALES_SUCCESS;

import seedu.address.overview.logic.commands.exception.InvalidValueException;
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
    public CommandResult execute(Model model) throws InvalidValueException {

        if (amount < 0) {
            throw new InvalidValueException(MESSAGE_INVALID_SET_AMOUNT);
        }

        model.setSalesTarget(amount);
        return new CommandResult(String.format(MESSAGE_SET_SALES_SUCCESS, Double.toString(amount)));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SetSalesCommand) {
            SetSalesCommand ssc = (SetSalesCommand) o;
            if (this.amount == ssc.amount) {
                return true;
            }
        }
        return false;
    }
}
