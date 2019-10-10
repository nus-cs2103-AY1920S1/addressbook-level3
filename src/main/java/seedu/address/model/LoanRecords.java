package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;

import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class LoanRecords implements ReadOnlyLoanRecords {

    private HashMap<LoanId, Loan> loansMap = new HashMap<>();

    public LoanRecords(ReadOnlyLoanRecords toBeCopied) {

    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public LoanRecords() {

    }

    @Override
    public Collection<Loan> getLoanCollection() {
        return loansMap.values();
    }

    public boolean hasLoan(Loan loan) {
        return loansMap.containsValue(loan);
    }

    public boolean hasLoan(LoanId loanId) {
        return loansMap.containsKey(loanId);
    }

    public void addLoan(Loan loan) {
        loansMap.put(loan.getLoanId() , loan);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanRecords // instanceof handles nulls
                && loansMap.equals(((LoanRecords) other).loansMap));
    }
}
