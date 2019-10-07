package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.BorrowerBuilder;

public class BorrowerIdGeneratorTest {

    @Test
    void generateBorrowerId_autoGenerateSuccess() {
        BorrowerRecords borrowers = new BorrowerRecords();
        BorrowerIdGenerator.setBorrowers(borrowers);
        assertEquals(BorrowerIdGenerator.generateBorrowerId(), new BorrowerId("K0001"));
        assertEquals(BorrowerIdGenerator.generateBorrowerId(), new BorrowerId("K0002"));
        assertEquals(BorrowerIdGenerator.generateBorrowerId(), new BorrowerId("K0003"));
        assertEquals(BorrowerIdGenerator.generateBorrowerId(), new BorrowerId("K0004"));
    }

    @Test
    void checkBorrowerIdExists() {
        BorrowerRecords borrowers = new BorrowerRecords();
        BorrowerIdGenerator.setBorrowers(borrowers);
        // returns false
        assertFalse(BorrowerIdGenerator.borrowerIdExists(new BorrowerId("K0001")));
        //returns true
        Borrower borrower = new BorrowerBuilder().actual_build();
        borrowers.addBorrower(borrower);
        assertTrue(BorrowerIdGenerator.borrowerIdExists(new BorrowerId("K0001")));

    }
}
