package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.ui.TransactionMessages;

public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    private final TransactionContainsKeywordsPredicate predicate;

    public FindCommand(TransactionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(TransactionMessages.findCommandMessage(model.getFilteredList().size()));
    }
}
