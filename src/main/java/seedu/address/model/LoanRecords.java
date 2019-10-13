package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;

import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;

/**
 * Wraps all {@code Loan} data at the LoanRecords level.
 */
public class LoanRecords implements ReadOnlyLoanRecords {

    private HashMap<LoanId, Loan> loansMap;

    /**
     * Creates a LoanRecords using the Loans in the {@code toBeCopied}
     */
    public LoanRecords(ReadOnlyLoanRecords toBeCopied) {
        requireNonNull(toBeCopied);

        loansMap = new HashMap<>();
        toBeCopied.getLoanCollection().forEach(loan -> loansMap.put(loan.getLoanId(), loan));
    }

    public LoanRecords() {
        loansMap = new HashMap<>();
    }

    /**
     * Get all the Loan objects tracked by LoanRecords.
     *
     * @return All the Loans in a {@Code Collection<Loan>}.
     */
    @Override
    public Collection<Loan> getLoanCollection() {
        return loansMap.values();
    }

    /**
     * Checks if the LoanRecords contains this {@code loan} object or not.
     *
     * @param loan Loan object to be checked.
     * @return True if LoanRecords contains this {@code loan} object, false otherwise.
     */
    public boolean hasLoan(Loan loan) {
        return loansMap.containsValue(loan);
    }

    /**
     * Checks if the LoanRecords contains a {@code loan} object with this {@code loanId} or not.
     *
     * @param loanId Loan ID of loan object to be checked.
     * @return True if LoanRecords contains a {@code loan} object with this {@code loanId}, false otherwise.
     */
    public boolean hasLoan(LoanId loanId) {
        return loansMap.containsKey(loanId);
    }

    /**
     * Add a {@code loan} object into the LoanRecords.
     *
     * @param loan Loan object to be added.
     */
    public void addLoan(Loan loan) {
        loansMap.put(loan.getLoanId(), loan);
    }

    /**
     * Returns the number of loans in {@code loansMap}.
     * Used to find the next LoanId in LoanIdGenerator.
     * Precondition: Loans will never be deleted or removed from storage.
     *
     * @return Number of loans in LoanRecords.
     */
    public int getLoanCount() {
        return loansMap.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanRecords // instanceof handles nulls
                && loansMap.equals(((LoanRecords) other).loansMap));
    }
}
