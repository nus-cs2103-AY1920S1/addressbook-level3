package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class LoanRecords implements ReadOnlyLoanRecords {

    private ObservableList<Loan> listOfLoans = FXCollections.observableArrayList();

    public LoanRecords(ReadOnlyLoanRecords toBeCopied) {

    }
    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public LoanRecords() {

    }

    @Override
    public ObservableList<Loan> getLoanList() {
        return FXCollections.unmodifiableObservableList(listOfLoans);
    }

    public boolean hasLoan(Loan loan) {
        return false;
    }

    public void addLoan(Loan loan) {
        listOfLoans.add(loan);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanRecords // instanceof handles nulls
                && listOfLoans.equals(((LoanRecords) other).listOfLoans));
    }
}
