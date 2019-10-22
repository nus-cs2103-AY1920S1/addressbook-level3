package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.BENSON;
import static seedu.address.testutil.TypicalBorrowers.CARL;
import static seedu.address.testutil.TypicalBorrowers.GEORGE;
import static seedu.address.testutil.TypicalBorrowers.IDA;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;
import static seedu.address.testutil.TypicalLoans.LOAN_1;

import org.junit.jupiter.api.Test;

import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.exceptions.BorrowerNotFoundException;
import seedu.address.model.borrower.exceptions.DuplicateBorrowerException;
import seedu.address.testutil.BorrowerBuilder;

public class BorrowerRecordsTest {
    @Test
    void checkIfBorrowerExists() {
        BorrowerRecords borrowers = new BorrowerRecords();
        Borrower borrower1 = new BorrowerBuilder().actualBuild();
        Borrower borrower2 = new BorrowerBuilder().withPhone("12341234")
                .withEmail("hello@yo.com").actualBuild();
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

    @Test
    public void contains() {
        BorrowerRecords borrowerRecords = getTypicalBorrowerRecords();
        assertTrue(borrowerRecords.listContains(CARL));
        assertFalse(borrowerRecords.listContains(IDA));
    }

    @Test
    public void setBorrower_validTargetAndEdited_success() {
        BorrowerRecords borrowerRecords = getTypicalBorrowerRecords();
        Borrower loanAddedCarl = new Borrower(CARL.getName(), CARL.getPhone(), CARL.getEmail(),
                CARL.getBorrowerId(), CARL.getAddedCurrentLoanList(LOAN_1), CARL.getReturnedLoanList());

        borrowerRecords.setBorrower(CARL, loanAddedCarl);

        assertTrue(borrowerRecords.getBorrowerFromId(CARL.getBorrowerId()).hasCurrentLoan(LOAN_1));
    }

    @Test
    public void setBorrower_invalidTarget_throwsBorrowerNotFoundException() {
        BorrowerRecords borrowerRecords = getTypicalBorrowerRecords();
        Borrower loanAddedIda = new Borrower(IDA.getName(), IDA.getPhone(), IDA.getEmail(),
                IDA.getBorrowerId(), IDA.getAddedCurrentLoanList(LOAN_1), IDA.getReturnedLoanList());

        assertThrows(BorrowerNotFoundException.class, () ->
                borrowerRecords.setBorrower(IDA, loanAddedIda));
    }

    @Test
    public void setBorrower_invalidEdited_throwsDuplicateBorrowerException() {
        BorrowerRecords borrowerRecords = getTypicalBorrowerRecords();

        assertThrows(DuplicateBorrowerException.class, () -> borrowerRecords.setBorrower(ALICE, BENSON));
    }
}
