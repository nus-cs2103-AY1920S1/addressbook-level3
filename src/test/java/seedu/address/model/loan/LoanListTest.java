package seedu.address.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalLoans.LOAN_1;
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
}
