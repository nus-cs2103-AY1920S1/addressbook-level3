package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.ui.tab.Tab;

/**
 * Deletes a { @code Transaction } or { @code Budget } identified using it's displayed index from the bank account.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the transaction identified by the index number used in the displayed transaction list.\n"
            + "Parameters: INDEX (must be a positive integer) Transaction entries preceded by 't', \n"
            + "Budget entries preceded by 'b' \n"
            + "Ledger entries preceded by 'l' \n"
            + "Projection entries preceded by 'p' \n"
            + "Example: " + COMMAND_WORD + " t1\n"
            + COMMAND_WORD + " b1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Entry: %1$s";

    private final String type;
    private final Index targetIndex;

    public DeleteCommand(String type, Index targetIndex) {
        requireNonNull(type);
        this.type = type;

        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.type.equals(Model.TRANSACTION_TYPE)) {
            ObservableList<BankAccountOperation> lastShownList = model.getFilteredTransactionList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
            }

            BankAccountOperation transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.delete(transactionToDelete);
            model.updateProjectionsAfterDelete(transactionToDelete);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, transactionToDelete),
                    false, false, Tab.TRANSACTION);
        } else if (this.type.equals(Model.BUDGET_TYPE)) {
            ObservableList<Budget> lastShownList = model.getFilteredBudgetList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
            }
            Budget budgetToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.delete(budgetToDelete);
            model.updateProjectionsAfterDelete(budgetToDelete);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, budgetToDelete),
                    false, false, Tab.BUDGET);
        } else if (this.type.equals(Model.PROJECTION_TYPE)) {
            ObservableList<Projection> lastShownList = model.getFilteredProjectionsList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PROJECTION_DISPLAYED_INDEX);
            }

            Projection projectionToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.delete(projectionToDelete);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, projectionToDelete),
                    false, false, Tab.PROJECTION);
        } else if (this.type.equals(Model.LEDGER_TYPE)) {
            ObservableList<LedgerOperation> lastShownList = model.getFilteredLedgerOperationsList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_LEDGER_DISPLAYED_INDEX);
            }

            LedgerOperation ledgerToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.delete(ledgerToDelete);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, ledgerToDelete),
                    false, false, Tab.LEDGER);
        } else {
            throw new CommandException("Unknown command error");
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
