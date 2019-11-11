package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.CLEARED_SUCCESSFULLY;

import seedu.address.util.CommandResult;

/**
 * Clears the sales list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    /**
     * Creates a ClearCommand to clear all the sales items.
     */
    public ClearCommand() {
    }

    @Override
    public CommandResult execute(seedu.address.cashier.model.Model model,
                                 seedu.address.person.model.CheckAndGetPersonByNameModel personModel)
            throws Exception {
        model.clearSalesList();
        model.resetCashier();
        return new CommandResult(CLEARED_SUCCESSFULLY);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearCommand); // instanceof handles nulls
    }

}

