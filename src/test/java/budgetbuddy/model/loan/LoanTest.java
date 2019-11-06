package budgetbuddy.model.loan;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class LoanTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // all fields null
        assertThrows(NullPointerException.class, () -> new Loan(
                null, null, null, null, null, null));

        // person field null
        assertThrows(NullPointerException.class, () -> new LoanBuilder().withPerson(null).build());

        // amount field null
        assertThrows(NullPointerException.class, () -> new LoanBuilder().withAmount(null).build());

        // direction field null
        assertThrows(NullPointerException.class, () -> new LoanBuilder().withDirection(null).build());

        // description field null
        assertThrows(NullPointerException.class, () -> new LoanBuilder().withDescription(null).build());

        // date field null
        assertThrows(NullPointerException.class, () -> new LoanBuilder().withDate(null).build());

        // status field null
        assertThrows(NullPointerException.class, () -> new LoanBuilder().withStatus(null).build());
    }

    @Test
    public void isPaid() {
        assert !TypicalLoans.JOHN_OUT_UNPAID.isPaid();
        assert TypicalLoans.PETER_OUT_PAID.isPaid();
    }

    @Test
    public void isSameLoan() {
        Loan loan = TypicalLoans.JOHN_OUT_UNPAID;

        // identical -> returns true
        Loan loanCopy = new LoanBuilder(loan).build();
        assertTrue(loan.isSameLoan(loanCopy));

        // different status -> returns true
        Loan loanWithDifferentStatus = new LoanBuilder(loan).withStatus("PAID").build();
        assertTrue(loan.isSameLoan(loanWithDifferentStatus));

        // other different properties -> returns false

        Loan loanOther = new LoanBuilder(loan).withAmount(loan.getAmount().toLong() + 1L).build();
        assertFalse(loan.isSameLoan(loanOther));

        loanOther = new LoanBuilder(loan).withPerson("Mary").build();
        assertFalse(loan.isSameLoan(loanOther));

        loanOther = new LoanBuilder(loan).withDate(loan.getDate().minusDays(1)).build();
        assertFalse(loan.isSameLoan(loanOther));

        loanOther = new LoanBuilder(loan).withDescription("Bogus description").build();
        assertFalse(loan.isSameLoan(loanOther));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Loan johnOutUnpaidCopy = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).build();
        assertEquals(TypicalLoans.JOHN_OUT_UNPAID, johnOutUnpaidCopy);

        // same object -> returns true
        assertEquals(TypicalLoans.JOHN_OUT_UNPAID, TypicalLoans.JOHN_OUT_UNPAID);

        // null -> returns false
        assertNotEquals(null, TypicalLoans.JOHN_OUT_UNPAID);

        // different type -> returns false
        assertNotEquals(5, TypicalLoans.JOHN_OUT_UNPAID);

        // different loan -> returns false
        assertNotEquals(TypicalLoans.JOHN_OUT_UNPAID, TypicalLoans.MARY_IN_UNPAID);

        // different person -> returns false
        Loan editedJohnOutUnpaid = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).withPerson("Dredd").build();
        assertNotEquals(TypicalLoans.JOHN_OUT_UNPAID, editedJohnOutUnpaid);

        // different direction -> returns false
        editedJohnOutUnpaid = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).withDirection("IN").build();
        assertNotEquals(TypicalLoans.JOHN_OUT_UNPAID, editedJohnOutUnpaid);

        // different amount -> returns false
        editedJohnOutUnpaid = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID)
                .withAmount(TypicalLoans.JOHN_OUT_UNPAID.getAmount().toLong() + 1L).build();
        assertNotEquals(TypicalLoans.JOHN_OUT_UNPAID, editedJohnOutUnpaid);

        // different description -> returns false
        editedJohnOutUnpaid = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID)
                .withDescription("Different description.").build();
        assertNotEquals(TypicalLoans.JOHN_OUT_UNPAID, editedJohnOutUnpaid);

        // different date -> returns false
        editedJohnOutUnpaid = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID)
                .withDate(LocalDate.ofEpochDay(10)).build();
        assertNotEquals(TypicalLoans.JOHN_OUT_UNPAID, editedJohnOutUnpaid);
    }
}
