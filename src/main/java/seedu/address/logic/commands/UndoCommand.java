package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEDGER_OPERATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoUserState()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoUserState();
        model.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        model.updateFilteredLedgerList(PREDICATE_SHOW_ALL_LEDGER_OPERATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
