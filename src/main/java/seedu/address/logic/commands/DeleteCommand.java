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
            + "Parameters: INDEX (must be a positive integer) Transaction entries preceded by 't', "
            + "Budget entries preceded by 'b' \n"
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

        if (this.type.equals("t")) {
            ObservableList<BankAccountOperation> lastShownList = model.getFilteredTransactionList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
            }

            BankAccountOperation transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteTransaction(transactionToDelete);
            model.updateProjectionsAfterDelete(transactionToDelete);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, transactionToDelete),
                    false, false, Tab.TRANSACTION);
        } else if (this.type.equals("b")) {
            ObservableList<Budget> lastShownList = model.getFilteredBudgetList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
            }

            //TODO: updateProjectionsAfterDelete(Budget budget)
            Budget budgetToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteBudget(budgetToDelete);
            model.getFilteredProjectionsList().forEach(x -> {
                if (x.getBudgets().isPresent() && x.getBudgets().equals(budgetToDelete)) {
                    model.deleteProjection(x);
                    model.add(new Projection(x.getTransactionHistory(), x.getDate()));
                }
            });
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, budgetToDelete),
                    false, false, Tab.BUDGET);
        } else if (this.type.equals("p")) {
            ObservableList<Projection> lastShownList = model.getFilteredProjectionsList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
            }

            Projection projectionToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteProjection(projectionToDelete);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, projectionToDelete),
                    false, false, Tab.PROJECTION);
            // delete command for Split
        } else if (this.type.equals("l")) {
            ObservableList<LedgerOperation> lastShownList = model.getFilteredLedgerOperationsList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_LEDGER_DISPLAYED_INDEX);
            }

            LedgerOperation ledgerToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteLedger(ledgerToDelete);
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
