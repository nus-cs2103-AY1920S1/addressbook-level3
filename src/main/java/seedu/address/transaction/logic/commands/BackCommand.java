package seedu.address.transaction.logic.commands;

import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_BACKED;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.model.Model;
import seedu.address.util.CommandResult;

/**
 * Backs out from a find command interface to the complete transaction list.
 */
public class BackCommand extends Command {
    public static final String COMMAND_WORD = "back";

    @Override
    public CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel) {
        model.resetPredicate();
        return new CommandResult(MESSAGE_BACKED);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
