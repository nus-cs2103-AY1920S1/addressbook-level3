package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Consumer;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;

/**
 * Updates the status of a loan.
 */
public abstract class UpdateStatusCommand extends MultiLoanCommand {

    public UpdateStatusCommand(List<Index> loanIndices, List<Person> persons) throws CommandException {
        super(loanIndices, persons);
    }

    /**
     * Updates the statuses of one or more existing loans to the given status.
     */
    protected void updateStatuses(LoansManager loansManager, Status updatedStatus) {
        requireAllNonNull(loansManager, updatedStatus);

        List<Index> targetLoanIndices = constructTargetLoanIndicesList(loansManager);
        Consumer<Index> updateStatusOp = targetIndex -> {
            Loan updatedLoan = createUpdatedLoan(loansManager.getLoan(targetIndex), updatedStatus);
            loansManager.updateStatus(targetIndex, updatedLoan);
        };

        actOnTargetLoans(loansManager, targetLoanIndices, updateStatusOp);
    }

    /**
     * Creates a new {@code Loan} with the updated {@code Status}.
     * @param oldLoan The {@code Loan} targeted for a status update.
     * @param updatedStatus The new {@code Status}.
     * @return The new, updated {@code Loan}.
     */
    private Loan createUpdatedLoan(Loan oldLoan, Status updatedStatus) {
        return new Loan(oldLoan.getPerson(), oldLoan.getDirection(), oldLoan.getAmount(),
                oldLoan.getDate(), oldLoan.getDescription(), updatedStatus);
    }
}
