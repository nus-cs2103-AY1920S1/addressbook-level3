package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Consumer;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
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
        Consumer<Index> updateStatusOp =
                targetLoanIndex -> loansManager.updateLoanStatus(targetLoanIndex, updatedStatus);

        actOnTargetLoans(targetLoanIndices, updateStatusOp);
    }
}
