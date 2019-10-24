package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;

import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyLoanRecords {

    Collection<Loan> getLoanCollection();

    HashMap<LoanId, Loan> getLoansMap();

}
