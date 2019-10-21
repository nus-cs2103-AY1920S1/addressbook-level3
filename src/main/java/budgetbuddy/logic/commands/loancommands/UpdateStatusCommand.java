package budgetbuddy.logic.commands.loancommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Consumer;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.util.PersonLoanIndexPair;
import budgetbuddy.model.person.Person;

/**
 * Updates the status of a loan.
 */
public abstract class UpdateStatusCommand extends MultiLoanCommand {

    public UpdateStatusCommand(
            List<PersonLoanIndexPair> personLoanIndexPairs, List<Index> personIndices) throws CommandException {
        super(personLoanIndexPairs, personIndices);
    }

    /**
     * Updates the statuses of one or more existing loans to the given status.
     */
    public void updateStatuses(LoansManager loansManager, Status updatedStatus) throws CommandException {
        requireAllNonNull(loansManager, updatedStatus);

        LoanList targetLoans = constructTargetLoanList(loansManager);
        Consumer<Loan> operation = targetLoan -> {
            Person targetPerson = null;
            for (Person person : loansManager.getPersonsList()) {
                if (person.isSamePerson(targetLoan.getPerson())) {
                    targetPerson = person;
                }
            }

            Loan updatedLoan = createUpdatedLoan(targetLoan, updatedStatus);
            loansManager.updateLoanStatus(targetPerson, targetLoan, updatedLoan);
        };

        try {
            actOnTargetLoans(targetLoans, operation);
        } catch (CommandException e) {
            throw new CommandException("One or more targeted loans could not be found.");
        }
    }

    /**
     * Creates a copy of a loan, but with its status updated to the given status.
     * @return The loan with the updated status.
     */
    protected static Loan createUpdatedLoan(Loan loanToUpdate, Status updatedStatus) {
        assert loanToUpdate != null;
        return new Loan(
                loanToUpdate.getPerson(),
                loanToUpdate.getDirection(),
                loanToUpdate.getAmount(),
                loanToUpdate.getDate(),
                loanToUpdate.getDescription(),
                updatedStatus
        );
    }
}
