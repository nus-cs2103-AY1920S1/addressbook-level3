package seedu.address.model.borrower;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.BorrowerRecords;
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
        borrowers.addBorrower(new BorrowerBuilder(ALICE).withBorrowerId("K0007").build());
        borrowers.addBorrower(new BorrowerBuilder(BOB).withBorrowerId("K0009").build());
        assertEquals(BorrowerIdGenerator.generateBorrowerId(), new BorrowerId("K0005"));
        assertEquals(BorrowerIdGenerator.generateBorrowerId(), new BorrowerId("K0006"));
        assertEquals(BorrowerIdGenerator.generateBorrowerId(), new BorrowerId("K0008"));
    }

    @Test
    void checkBorrowerIdExists() {
        BorrowerRecords borrowers = new BorrowerRecords();
        BorrowerIdGenerator.setBorrowers(borrowers);
        // returns false
        assertFalse(BorrowerIdGenerator.borrowerIdExists(new BorrowerId("K0001")));
        //returns true
        Borrower borrower = new BorrowerBuilder().actualBuild();
        borrowers.addBorrower(borrower);
        assertTrue(BorrowerIdGenerator.borrowerIdExists(new BorrowerId("K0001")));

    }
}
