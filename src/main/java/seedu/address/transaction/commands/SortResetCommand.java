package seedu.address.transaction.commands;

import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;

public class SortResetCommand extends SortCommand {
    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws NoSuchIndexException, CommandException, NoSuchPersonException {
        model.sortReset();
        return new CommandResult(TransactionMessages.RESET_TO_ORIGINAL_ORDER);
    }
}
