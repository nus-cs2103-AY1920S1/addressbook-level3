package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;

public class LoanRecords implements ReadOnlyLoanRecords {

    ObservableList<Loan> listOfLoans = FXCollections.observableArrayList();

    public LoanRecords(ReadOnlyLoanRecords toBeCopied) {

    }

    public LoanRecords() {

    }

    public void populateLoans () {
        for (int i = 0; i < 10; i++) {
            listOfLoans.add(new Loan("01298" + i));
        }
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
}
