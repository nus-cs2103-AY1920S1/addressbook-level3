package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.GEORGE;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;

import org.junit.jupiter.api.Test;

import seedu.address.model.borrower.Borrower;
import seedu.address.testutil.BorrowerBuilder;

public class BorrowerRecordsTest {
    @Test
    void checkIfBorrowerExists() {
        BorrowerRecords borrowers = new BorrowerRecords();
        Borrower borrower1 = new BorrowerBuilder().actual_build();
        Borrower borrower2 = new BorrowerBuilder().actual_build();
        borrowers.addBorrower(borrower1);
        //return true
        assertTrue(borrowers.hasBorrower(borrower1));
        //return false
        assertFalse(borrowers.hasBorrower(borrower2));
    }

    @Test
    public void getBorrowerFromId_borrowerExists_success() {
        BorrowerRecords borrowers = getTypicalBorrowerRecords();
        Borrower borrower = borrowers.getBorrowerFromId(ALICE.getBorrowerId());
        assertEquals(borrower, ALICE);
    }

    @Test
    public void getBorrowerFromId_borrowerDoesNotExists_assertNullPointerExceptionThrown() {
        BorrowerRecords borrowers = getTypicalBorrowerRecords();
        assertThrows(NullPointerException.class, () -> borrowers.getBorrowerFromId(GEORGE.getBorrowerId()));
    }
}
