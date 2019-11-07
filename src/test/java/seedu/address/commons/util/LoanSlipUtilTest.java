package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.BOB;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.LoanSlipException;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;

class LoanSlipUtilTest {
    private Borrower borrower;
    private Book book;
    private Loan loan;
    private Loan loan2;
    private Loan loan3;

    @BeforeEach
    public void setUp() {
        borrower = ALICE;
        book = BOOK_1;
        loan = new Loan(
                new LoanId("L999999"),
                book.getSerialNumber(),
                borrower.getBorrowerId(),
                DateUtil.getTodayDate(),
                DateUtil.getTodayPlusDays(14));
        loan2 = new Loan(
                new LoanId("L000002"),
                BOOK_2.getSerialNumber(),
                BOB.getBorrowerId(),
                DateUtil.getTodayPlusDays(10),
                DateUtil.getTodayPlusDays(24));
        loan3 = new Loan(
                new LoanId("L000002"),
                BOOK_2.getSerialNumber(),
                borrower.getBorrowerId(),
                DateUtil.getTodayPlusDays(10),
                DateUtil.getTodayPlusDays(24));
    }

    @Test
    public void mountLoanSlip_noLoanSlipMounted_success() {
        LoanSlipUtil.clearSession();
        assertFalse(LoanSlipUtil.isMounted());
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        assertTrue(LoanSlipUtil.isMounted());
    }

    @Test
    public void mountLoanSlip_inconsistentLoanAndFields_failure() {
        LoanSlipUtil.clearSession();
        assertThrows(LoanSlipException.class, () -> LoanSlipUtil.mountLoan(loan, BOOK_2, borrower));
        assertFalse(LoanSlipUtil.isMounted());
        assertThrows(LoanSlipException.class, () -> LoanSlipUtil.mountLoan(loan, book, BOB));
        assertFalse(LoanSlipUtil.isMounted());
    }

    @Test
    public void mountLoanSlip_loanSlipMountedDifferentBorrower_failure() {
        LoanSlipUtil.clearSession();
        assertFalse(LoanSlipUtil.isMounted());
        //Mount first loan
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        //Mount second loan
        //Check that second loan cannot be mounted since it is a different borrower
        assertThrows(AssertionError.class, () -> LoanSlipUtil.mountLoan(loan2, BOOK_2, BOB));
    }

    @Test
    public void mountLoanSlip_loanSlipMountedSameBorrower_success() {
        LoanSlipUtil.clearSession();
        assertFalse(LoanSlipUtil.isMounted());
        //Mount first loan
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        //Mount second loan
        //Check that second loan cannot be mounted since it is a different borrower
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan3, BOOK_2, borrower));
    }


    @Test
    public void createLoanSlipInDirectory_multipleLoanSlipMounted_success() {
        File file = new File("./data/loan_slips/L999999.pdf");
        file.delete();
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan3, BOOK_2, borrower));
        assertFalse(file.exists());
        assertDoesNotThrow(() -> LoanSlipUtil.createLoanSlipInDirectory());
        assertTrue(file.exists());
        //assertDoesNotThrow(() -> LoanSlipUtil.openGeneratedLoanSlip());

        file.delete();
        assertFalse(file.exists());
    }

    @Test
    public void openLoanSlip_loanSlipNotReady_failure() {
        LoanSlipUtil.clearSession();
        assertThrows(LoanSlipException.class, () -> LoanSlipUtil.openGeneratedLoanSlip());
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        assertThrows(LoanSlipException.class, () -> LoanSlipUtil.openGeneratedLoanSlip());
    }

    @Test
    public void unmountSpecificLoan_success() {
        // Unmount loan, loan not mounted, nothing happens
        LoanSlipUtil.clearSession();
        assertFalse(LoanSlipUtil.isMounted());
        //Mount first loan
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        assertDoesNotThrow(() -> LoanSlipUtil.unmountSpecificLoan(loan2, BOOK_2));

        // Unmount loan, only 1 loan mounted, entire LoanSlipUtil is unmounted
        LoanSlipUtil.clearSession();
        assertFalse(LoanSlipUtil.isMounted());
        //Mount first loan
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        assertDoesNotThrow(() -> LoanSlipUtil.unmountSpecificLoan(loan, book));
        assertFalse(LoanSlipUtil.isMounted());

        //Unmount loan, still have other loans mounted, LoanSlipUtil is still mounted
        LoanSlipUtil.clearSession();
        assertFalse(LoanSlipUtil.isMounted());
        //Mount first loan two loans
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan, book, borrower));
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoan(loan3, BOOK_2, borrower));
        //Unmount first loan
        assertDoesNotThrow(() -> LoanSlipUtil.unmountSpecificLoan(loan, book));
        assertTrue(LoanSlipUtil.isMounted());
        //Unmount second loan
        assertDoesNotThrow(() -> LoanSlipUtil.unmountSpecificLoan(loan3, BOOK_2));
        assertFalse(LoanSlipUtil.isMounted());
    }
}
