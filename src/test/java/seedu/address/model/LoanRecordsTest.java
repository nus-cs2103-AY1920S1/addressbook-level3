package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLoans.LOAN_1;
import static seedu.address.testutil.TypicalLoans.LOAN_2;
import static seedu.address.testutil.TypicalLoans.LOAN_3;
import static seedu.address.testutil.TypicalLoans.LOAN_7;
import static seedu.address.testutil.TypicalLoans.LOAN_7_RETURNED;
import static seedu.address.testutil.TypicalLoans.getTypicalLoanRecords;
import static seedu.address.testutil.TypicalLoans.getTypicalLoans;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.loan.Loan;

class LoanRecordsTest {

    @Test
    public void constructor_noArgument_emptyLoanRecords() {
        LoanRecords loanRecords = new LoanRecords();
        assertTrue(loanRecords.getLoanCollection().isEmpty());
        assertTrue(loanRecords.getLoansMap().isEmpty());
        assertEquals(loanRecords.getLoanCount(), 0);
    }

    @Test
    public void contructor_copiedLoanRecords_loanRecordsNotEmpty() {
        LoanRecords loanRecords = new LoanRecords(getTypicalLoanRecords());
        assertEquals(loanRecords.getLoanCollection().size(), getTypicalLoans().size());
        assertEquals(loanRecords.getLoansMap().size(), getTypicalLoans().size());
        assertEquals(loanRecords.getLoanCount(), getTypicalLoans().size());
        assertEquals(loanRecords, getTypicalLoanRecords());
    }

    @Test
    public void getLoanCollection() {
        LoanRecords loanRecords = new LoanRecords(getTypicalLoanRecords());
        assertEquals(new ArrayList<Loan>().addAll(loanRecords.getLoanCollection()),
                new ArrayList<Loan>().addAll(getTypicalLoans()));
    }

    @Test
    public void hasLoan() {
        LoanRecords loanRecords = new LoanRecords(getTypicalLoanRecords());
        assertTrue(loanRecords.hasLoan(LOAN_1));
        assertTrue(loanRecords.hasLoan(LOAN_2));
        assertTrue(loanRecords.hasLoan(LOAN_3.getLoanId()));
        assertFalse(loanRecords.hasLoan(LOAN_7));
        assertFalse(loanRecords.hasLoan(LOAN_7.getLoanId()));
    }

    @Test
    public void addLoan() {
        LoanRecords loanRecords = new LoanRecords(getTypicalLoanRecords());
        loanRecords.addLoan(LOAN_7);
        assertTrue(loanRecords.hasLoan(LOAN_7));
    }

    @Test
    public void updateLoan() {
        LoanRecords loanRecords = new LoanRecords(getTypicalLoanRecords());
        loanRecords.addLoan(LOAN_7);
        loanRecords.updateLoan(LOAN_7, LOAN_7_RETURNED);
        assertNotEquals(loanRecords.getLoansMap().get(LOAN_7.getLoanId()).getReturnDate(), null);
    }
}
