package seedu.address.transaction.commands;

import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_FIND_COMMAND;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;

/**
 * Finds a transaction in the transaction list.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    private final TransactionContainsKeywordsPredicate predicate;

    /**
     * Creates a FincCommand to find the specified {@code Transaction}
     */
    public FindCommand(TransactionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.updatePredicate(this.predicate);
        return new CommandResult(String.format(MESSAGE_FIND_COMMAND, model.getFilteredList().size()));
    }
}
