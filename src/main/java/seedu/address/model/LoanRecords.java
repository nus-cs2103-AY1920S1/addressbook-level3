package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.loan.Loan;

public class LoanRecords {
    List<Loan> list_of_loans = new ArrayList<>();

    public LoanRecords () {

    }

    public void populateLoans () {
        for (int i = 0; i < 10; i++) {
            list_of_loans.add(new Loan("hahaha"));
        }
    }
}
