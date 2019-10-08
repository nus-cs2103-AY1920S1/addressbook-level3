package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.ui.TransactionMessages;

import static java.util.Objects.requireNonNull;

public class FindCommand extends Command {
    private final TransactionContainsKeywordsPredicate predicate;

    public FindCommand(TransactionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel)  {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(TransactionMessages.MESSAGE_PERSONS_LISTED_OVERVIEW);
    }
}
