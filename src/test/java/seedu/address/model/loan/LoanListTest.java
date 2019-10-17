package seedu.address.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLoans.LOAN_1;
import static seedu.address.testutil.TypicalLoans.LOAN_2;
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
    public void remove() {
        LoanList loanList = new LoanList();
        loanList.add(LOAN_1);

        loanList.remove(LOAN_1);
        assertFalse(loanList.contains(LOAN_1));
    }
}
