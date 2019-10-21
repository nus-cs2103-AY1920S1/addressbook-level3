package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    }

    @Test
    public void mountLoanSlip_noLoanSlipMounted_success() {
        LoanSlipUtil.unmountLoanSlip();
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoanSlip(loan, book, borrower));
        assertTrue(LoanSlipUtil.isMounted());
    }

    @Test
    public void mountLoanSlip_inconsistentLoanAndFields_failure() {
        LoanSlipUtil.unmountLoanSlip();
        assertThrows(LoanSlipException.class, () -> LoanSlipUtil.mountLoanSlip(loan, BOOK_2, borrower));
        assertFalse(LoanSlipUtil.isMounted());
        assertThrows(LoanSlipException.class, () -> LoanSlipUtil.mountLoanSlip(loan, book, BOB));
        assertFalse(LoanSlipUtil.isMounted());
    }

    @Test
    public void mountLoanSlip_loanSlipMounted_mountOverrideSuccess() {
        LoanSlipUtil.unmountLoanSlip();
        assertFalse(LoanSlipUtil.isMounted());
        //Mount first loan
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoanSlip(loan, book, borrower));
        Loan loan2 = new Loan(
                new LoanId("L000002"),
                BOOK_2.getSerialNumber(),
                BOB.getBorrowerId(),
                DateUtil.getTodayPlusDays(10),
                DateUtil.getTodayPlusDays(24));
        //Mount second loan
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoanSlip(loan2, BOOK_2, BOB));
        //Check that second loan is mounted, overriding first loan
        assertTrue(LoanSlipUtil.isMounted());
        assertNotEquals(LoanSlipUtil.getCurrentLoan(), loan);
        assertEquals(LoanSlipUtil.getCurrentLoan(), loan2);
    }

    @Test
    public void createLoanSlipInDirectory_loanSlipMounted_success() {
        File file = new File("./data/loan_slips/L999999.pdf");
        file.delete();
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoanSlip(loan, book, borrower));
        assertFalse(file.exists());
        assertDoesNotThrow(() -> LoanSlipUtil.createLoanSlipInDirectory());
        assertTrue(file.exists());
        file.delete();
        assertFalse(file.exists());
    }

    @Test
    public void createLoanSlipInDirectory_noloanSlipMounted_failure() {
        LoanSlipUtil.unmountLoanSlip();
        assertThrows(LoanSlipException.class, () ->LoanSlipUtil.createLoanSlipInDirectory());
    }

    @Test
    public void openLoanSlip_loanSlipNotReady_failure() {
        LoanSlipUtil.unmountLoanSlip();
        assertThrows(LoanSlipException.class, () ->LoanSlipUtil.openLoanSlip());
        assertDoesNotThrow(() -> LoanSlipUtil.mountLoanSlip(loan, book, borrower));
        assertThrows(LoanSlipException.class, () ->LoanSlipUtil.openLoanSlip());
    }
}
