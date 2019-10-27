package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;

/**
 * Deletes a transaction identified using it's displayed index from THRIFT.
 */
public class DeleteCommand extends NonScrollingCommand implements Undoable {

    public static final String COMMAND_WORD = "delete";

    public static final String HELP_MESSAGE = COMMAND_WORD
            + ": Deletes the transaction identified by the index number used in the displayed transaction list.\n"
            + "Format: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer)\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To delete transaction at index 1 in the displayed transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the transaction identified by the index number used in the displayed transaction list.\n"
            + "Parameters: " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Transaction: %1$s";

    public static final String UNDO_SUCCESS = "Added transaction: %1$s";
    public static final String REDO_SUCCESS = "Deleted transaction: %1$s";

    private final Index targetIndex;
    private Transaction transactionToDelete;
    private Index actualIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.transactionToDelete = null;
        this.actualIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        actualIndex = model.getIndexInFullTransactionList(transactionToDelete).get();
        model.deleteTransaction(actualIndex);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    @Override
    public String undo(Model model) {
        requireAllNonNull(model, transactionToDelete);
        if (transactionToDelete instanceof Expense) {
            model.addExpense((Expense) transactionToDelete, actualIndex);
        } else if (transactionToDelete instanceof Income) {
            model.addIncome((Income) transactionToDelete, actualIndex);
        }
        return String.format(UNDO_SUCCESS, transactionToDelete);
    }

    @Override
    public String redo(Model model) {
        requireAllNonNull(model, actualIndex);
        model.deleteTransaction(actualIndex);
        return String.format(REDO_SUCCESS, transactionToDelete);
    }
}
