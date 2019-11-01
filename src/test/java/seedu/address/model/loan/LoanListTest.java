package seedu.address.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLoans.LOAN_1;
import static seedu.address.testutil.TypicalLoans.LOAN_2;
import static seedu.address.testutil.TypicalLoans.LOAN_3;
import static seedu.address.testutil.TypicalLoans.LOAN_4;
import static seedu.address.testutil.TypicalLoans.LOAN_8;
import static seedu.address.testutil.TypicalLoans.LOAN_9;
import static seedu.address.testutil.TypicalLoans.getTypicalLoans;

import java.util.List;

import org.junit.jupiter.api.Test;

class LoanListTest {

    @Test
    public void add() {
        LoanList loanList = new LoanList();
        List<Loan> typicalLoans = getTypicalLoans();

        for (int i = 0; i < typicalLoans.size(); i++) {
            loanList.add(typicalLoans.get(i));
            assertEquals(loanList.size(), i + 1);
        }
    }

    @Test
    public void size() {
        LoanList loanList = new LoanList();
        assertEquals(loanList.size(), 0);

        loanList.add(LOAN_1);
        assertEquals(loanList.size(), 1);
    }

    @Test
    public void contains() {
        LoanList loanList = new LoanList();
        loanList.add(LOAN_1);

        assertTrue(loanList.contains(LOAN_1));
        assertFalse(loanList.contains(LOAN_2));
    }

    @Test
    public void addToNewCopy() {
        LoanList loanList1 = new LoanList();
        loanList1.add(LOAN_1);
        LoanList loanList2 = loanList1.addToNewCopy(LOAN_2);
        loanList1.add(LOAN_2);

        assertEquals(loanList1, loanList2);
        assertNotSame(loanList1, loanList2);
    }

    @Test
    public void removeFromNewCopy() {
        LoanList loanList1 = new LoanList();
        loanList1.add(LOAN_1);
        loanList1.add(LOAN_2);
        LoanList loanList2 = loanList1.removeFromNewCopy(LOAN_2);

        assertEquals(loanList2.size(), 1);
        assertNotEquals(loanList1, loanList2);
    }

    @Test
    public void replaceInNewCopy() {
        LoanList loanList1 = new LoanList();
        loanList1.add(LOAN_1);
        loanList1.add(LOAN_2);
        loanList1.add(LOAN_3);
        LoanList loanList2 = loanList1.replaceInNewCopy(LOAN_2, LOAN_4);

        assertTrue(loanList2.contains(LOAN_4));
        assertFalse(loanList2.contains(LOAN_2));
        assertTrue(loanList1.contains(LOAN_2));
        assertFalse(loanList1.contains(LOAN_4));
    }

    @Test
    public void calculateOutstandingFineAmount() {
        LoanList loanList = new LoanList();
        loanList.add(LOAN_8);
        loanList.add(LOAN_9);

        assertEquals(loanList.calculateOutstandingFineAmount(),
                LOAN_8.getRemainingFineAmount() + LOAN_9.getRemainingFineAmount());
    }
}
