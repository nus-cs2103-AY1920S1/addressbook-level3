package seedu.address.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROWER_ID_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROWER_ID_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.testutil.TypicalLoans.LOAN_1;
import static seedu.address.testutil.TypicalLoans.LOAN_7_RETURNED;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.LoanBuilder;

public class LoanTest {

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(LOAN_1, LOAN_1);

        // same values -> returns true
        Loan loanB = new LoanBuilder(LOAN_1).build();
        assertEquals(LOAN_1, loanB);

        // null -> returns false
        assertNotEquals(null, LOAN_1);

        // different types -> returns false
        assertNotEquals("123", LOAN_1);

        // different serial number -> returns false
        Loan loanC = new LoanBuilder(LOAN_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertNotEquals(LOAN_1, loanC);

        // different borrower ID -> returns false
        Loan loanD = new LoanBuilder(LOAN_1).withBorrowerId(VALID_BORROWER_ID_2).build();
        assertNotEquals(LOAN_1, loanD);

        // different start date -> returns false
        Loan loanE = new LoanBuilder(LOAN_1).withStartDate("2019-10-14").build();
        assertNotEquals(LOAN_1, loanE);

        // different due date -> returns false
        Loan loanF = new LoanBuilder(LOAN_1).withDueDate("2019-10-20").build();
        assertNotEquals(LOAN_1, loanF);

        // different return date -> returns false
        Loan loanG = new LoanBuilder(LOAN_1).withReturnDate("2019-10-25").build();
        assertNotEquals(LOAN_1, loanG);

        // different renew count -> returns false
        Loan loanH = new LoanBuilder(LOAN_1).withRenewCount(1).build();
        assertNotEquals(LOAN_1, loanH);

        // different remaining fine amount -> returns false
        Loan loanI = new LoanBuilder(LOAN_1).withRemainingFineAmount(100).build();
        assertNotEquals(LOAN_1, loanI);

        // different paid fine amount -> returns false
        Loan loanJ = new LoanBuilder(LOAN_1).withPaidFineAmount(20).build();
    }

    @Test
    public void hashCode_sameLoanSameHashCode_assertEquals() {
        Loan loanA = new Loan(new LoanId(VALID_LOAN_ID), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID_1), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        Loan loanB = new Loan(new LoanId(VALID_LOAN_ID), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID_1), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        assertEquals(loanA.hashCode(), loanB.hashCode());
    }

    @Test
    public void toString_correctStringRepresentation_returnsTrue() {
        String loan1StringRep = "Loan ID: " + LOAN_1.getLoanId() + " Book Serial Number: "
                + LOAN_1.getBookSerialNumber() + " Borrower ID: " + LOAN_1.getBorrowerId() + " Loaned from "
                + LOAN_1.getStartDate() + " to " + LOAN_1.getDueDate();
        assertEquals(LOAN_1.toString(), loan1StringRep);

        String loan7ReturnedStringRep = "Loan ID: " + LOAN_7_RETURNED.getLoanId() + " Book Serial Number: "
                + LOAN_7_RETURNED.getBookSerialNumber() + " Borrower ID: " + LOAN_7_RETURNED.getBorrowerId()
                + " Loaned from " + LOAN_7_RETURNED.getStartDate() + " to " + LOAN_7_RETURNED.getDueDate()
                + ". Returned on " + LOAN_7_RETURNED.getReturnDate();
        assertEquals(LOAN_7_RETURNED.toString(), loan7ReturnedStringRep);
    }
}
