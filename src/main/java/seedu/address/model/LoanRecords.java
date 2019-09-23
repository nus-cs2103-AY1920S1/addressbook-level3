package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;

public class LoanRecords implements ReadOnlyLoanRecords {
    List<Loan> list_of_loans = new ArrayList<>();

    public LoanRecords () {

    }

    public void populateLoans () {
        for (int i = 0; i < 10; i++) {
            list_of_loans.add(new Loan("hahaha"));
        }
    }

    @Override
    public ObservableList<Loan> getLoanList() {
        return null;
    }

    public boolean hasLoan(Loan loan) {
        return true;
    }

    public void addLoan(Loan loan) {
    }
}
