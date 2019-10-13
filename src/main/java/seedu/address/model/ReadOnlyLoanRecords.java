package seedu.address.model;

import java.util.Collection;

import seedu.address.model.loan.Loan;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyLoanRecords {

    Collection<Loan> getLoanCollection();
}
