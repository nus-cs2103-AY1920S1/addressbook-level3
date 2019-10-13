package seedu.address.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;

public class LoanTest {

    @Test
    public void equals() {
        // same object -> returns true
        Loan loanA = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        assertTrue(loanA.equals(loanA));

        // same values -> returns true
        Loan loanB = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        assertTrue(loanA.equals(loanB));

        // null -> returns false
        assertFalse(loanA.equals(null));

        // different types -> returns false
        assertFalse(loanA.equals(123));

        // different serial number -> returns false
        Loan loanC = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_2),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        assertFalse(loanA.equals(loanC));

        // different borrower ID -> returns false
        Loan loanD = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_ID_AMY), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        assertFalse(loanA.equals(loanD));

        // different start date -> returns false
        Loan loanE = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayPlusDays(14), DateUtil.getTodayPlusDays(30));
        assertFalse(loanA.equals(loanE));

        // different end date -> returns false
        Loan loanF = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(14));
        assertFalse(loanA.equals(loanF));
    }

    @Test
    public void hashCode_sameLoanSameHashCode_assertEquals() {
        Loan loanA = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        Loan loanB = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        assertEquals(loanA.hashCode(), loanB.hashCode());
    }

    @Test
    public void toString_correctStringRepresentation_returnsTrue() {
        Loan loanA = new Loan(new LoanId("L123456"), new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1),
                new BorrowerId(VALID_BORROWER_ID), DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));

        // TODO CHANGE ALL TO USE LOANBUILDER AND VALID_LOAN_ID
        String stringRep = "Loan ID: " + "L123456" + " Book Serial Number: " + VALID_SERIAL_NUMBER_BOOK_1
                + " Borrower ID: " + VALID_BORROWER_ID + " Loaned from " + DateUtil.getTodayDate()
                + " to " + DateUtil.getTodayPlusDays(30);

        assertTrue(loanA.toString().equals(stringRep));
    }
}
