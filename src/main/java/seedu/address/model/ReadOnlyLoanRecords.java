package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyLoanRecords {

    ObservableList<Loan> getLoanList();
}
