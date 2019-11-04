package budgetbuddy.model.loan;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.testutil.loanutil.TypicalLoans;

public class LoanSortersTest {

    private List<Loan> loans;

    @BeforeEach
    public void initialize() {
        loans = new ArrayList<Loan>();
        loans.add(TypicalLoans.JOHN_OUT_UNPAID);
        loans.add(TypicalLoans.PETER_OUT_PAID);
    }

    @Test
    public void dateNewestSorter_sortList_loansSortedByNewestDateFirst() {
        loans.sort(LoanSorters.DATE_NEWEST);
        assertTrue(IntStream.range(0, loans.size() - 1)
                .allMatch(i -> loans.get(i).getDate().isAfter(loans.get(i + 1).getDate())
                        || loans.get(i).getDate().equals(loans.get(i + 1).getDate())));
    }

    @Test
    public void personSorter_sortList_loansSortedInAlphabeticalOrder() {
        loans.sort(LoanSorters.PERSON);
        assertTrue(IntStream.range(0, loans.size() - 1)
                .allMatch(i -> loans.get(i).getPerson().getName().toString()
                        .compareTo(loans.get(i + 1).getPerson().getName().toString()) <= 0));
    }

    @Test
    public void amountAscendingSorter_sortList_loansSortedBySmallestAmountFirst() {
        loans.sort(LoanSorters.AMOUNT_ASC);
        assertTrue(IntStream.range(0, loans.size() - 1)
                .allMatch(i -> loans.get(i).getAmount().toLong()
                        <= loans.get(i + 1).getAmount().toLong()));
    }
}
