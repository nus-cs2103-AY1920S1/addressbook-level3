package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_FIND_COMMAND;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;
import seedu.address.util.CommandResult;

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
    public CommandResult execute(Model model, CheckAndGetPersonByNameModel personModel) {
        requireNonNull(model);
        requireNonNull(personModel);
        model.updatePredicate(this.predicate);
        return new CommandResult(String.format(MESSAGE_FIND_COMMAND, model.getFilteredList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate));
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
