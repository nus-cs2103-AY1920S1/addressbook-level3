package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;

public class SortResetCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortReset();
        return new CommandResult(TransactionMessages.RESET_TO_ORIGINAL_ORDER);
    }
}
