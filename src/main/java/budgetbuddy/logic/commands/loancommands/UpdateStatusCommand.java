package budgetbuddy.logic.commands.loancommands;

import java.util.ArrayList;
import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.person.exceptions.PersonNotFoundException;
import budgetbuddy.model.person.loan.Loan;
import budgetbuddy.model.person.loan.Status;
import budgetbuddy.model.person.loan.exceptions.LoanNotFoundException;

/**
 * Updates the status of a loan.
 */
public abstract class UpdateStatusCommand extends MultiLoanCommand {

    public UpdateStatusCommand(
            List<Index> targetPersonsIndices, List<Index> targetLoansIndices) throws CommandException {
        super(targetPersonsIndices, targetLoansIndices);
    }

    /**
     * Updates the statues of one or more existing loans to the given status.
     * @return A list of person-loan index pairs that could not be found among existing loans.
     */
    public List<PersonLoanIndexPair> updateStatuses(LoansManager loansManager, Status updatedStatus) {
        List<PersonLoanIndexPair> pairsNotFound = new ArrayList<PersonLoanIndexPair>();
        for (int i = 0; i < personLoanIndexPairs.size(); i++) {
            try {
                Person targetPerson = loansManager.getPersonsList().get(
                        personLoanIndexPairs.get(i).getPersonIndex().getZeroBased()
                );

                Loan targetLoan = targetPerson.getLoans().get(
                        personLoanIndexPairs.get(i).getLoanIndex().getZeroBased()
                );

                Loan updatedLoan = createUpdatedLoan(targetLoan, updatedStatus);

                loansManager.updateLoanStatus(targetPerson, targetLoan, updatedLoan);
            } catch (IndexOutOfBoundsException | PersonNotFoundException | LoanNotFoundException e) {
                pairsNotFound.add(personLoanIndexPairs.get(i));
            }
        }
        return pairsNotFound;
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
