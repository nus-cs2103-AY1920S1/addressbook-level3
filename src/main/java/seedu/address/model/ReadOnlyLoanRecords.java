package seedu.address.model;

import java.util.Arrays;

import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;

public interface ReadOnlyLoanRecords {

    ObservableList<Loan> getLoanList();
}
