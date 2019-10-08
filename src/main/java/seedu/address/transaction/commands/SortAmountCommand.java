package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;

public class SortAmountCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortByAmount();
        return new CommandResult(TransactionMessages.SORTED_BY_AMOUNT);
    }
}
