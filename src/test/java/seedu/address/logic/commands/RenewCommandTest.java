package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_IS_OVERDUE;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_NOT_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_LOANED_BY_BORROWER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_6;
import static seedu.address.testutil.TypicalBooks.BOOK_7_ON_LOAN;
import static seedu.address.testutil.TypicalBorrowers.HOON;
import static seedu.address.testutil.TypicalBorrowers.IDA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalLoans.LOAN_7;
import static seedu.address.testutil.UserSettingsBuilder.DEFAULT_RENEW_PERIOD;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BorrowerBuilder;
import seedu.address.testutil.LoanBuilder;

class RenewCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RenewCommand(null));
    }

    @Test
    public void execute_validLoanedBook_renewSuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);
        BorrowerId servingBorrowerId = IDA.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7_ON_LOAN);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model actualModel = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        actualModel.setServingBorrower(servingBorrowerId);
        Model expectedModel = new ModelManager(new Catalog(catalog), new LoanRecords(loanRecords),
                new BorrowerRecords(borrowerRecords), new UserPrefs());
        expectedModel.setServingBorrower(servingBorrowerId);

        RenewCommand renewCommand = new RenewCommand(INDEX_FIRST_BOOK);

        LocalDate extendedDueDate = DateUtil.extendDate(LOAN_7.getDueDate(), DEFAULT_RENEW_PERIOD);
        Loan renewedLoan = new LoanBuilder(LOAN_7).withDueDate(extendedDueDate.toString())
                .withRenewCount(1).build();

        Book renewedBook = new BookBuilder(BOOK_7_ON_LOAN).withLoan(renewedLoan).build();

        expectedModel.setBook(BOOK_7_ON_LOAN, renewedBook);
        expectedModel.servingBorrowerRenewLoan(LOAN_7, renewedLoan);
        expectedModel.updateLoan(LOAN_7, renewedLoan);

        String expectedMessage = String.format(RenewCommand.MESSAGE_SUCCESS, renewedBook,
                expectedModel.getServingBorrower(), extendedDueDate);

        assertCommandSuccess(renewCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notInServeMode_renewUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7_ON_LOAN);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());

        RenewCommand renewCommand = new RenewCommand(INDEX_FIRST_BOOK);

        String actualMessage;
        try {
            actualMessage = renewCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = MESSAGE_NOT_IN_SERVE_MODE;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_noSuchIndex_renewUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);
        BorrowerId servingBorrowerId = IDA.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7_ON_LOAN);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        RenewCommand renewCommand = new RenewCommand(INDEX_SECOND_BOOK);

        String actualMessage;
        try {
            actualMessage = renewCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_bookNotOnLoan_renewUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);
        BorrowerId servingBorrowerId = IDA.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_6);

        LoanRecords loanRecords = new LoanRecords();

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        RenewCommand renewCommand = new RenewCommand(INDEX_FIRST_BOOK);

        String actualMessage;
        try {
            actualMessage = renewCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(MESSAGE_BOOK_NOT_ON_LOAN, BOOK_6);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_borrowerDoesNotLoanThisBook_unsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);
        BorrowerId servingBorrowerId = HOON.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7_ON_LOAN);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        RenewCommand renewCommand = new RenewCommand(INDEX_FIRST_BOOK);

        String actualMessage;
        try {
            actualMessage = renewCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(MESSAGE_NOT_LOANED_BY_BORROWER, HOON, BOOK_7_ON_LOAN);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_bookOverdue_renewUnsuccessful() {
        LoanRecords loanRecords = new LoanRecords();
        Loan overdueLoan = new LoanBuilder(LOAN_7).withStartDate("2000-10-10")
                .withDueDate("2000-10-24").build();
        loanRecords.addLoan(overdueLoan);

        Catalog catalog = new Catalog();
        Book overdueBook = new BookBuilder(BOOK_7_ON_LOAN).withLoan(overdueLoan).build();
        catalog.addBook(overdueBook);

        BorrowerRecords borrowerRecords = new BorrowerRecords();
        Borrower borrower = new BorrowerBuilder(IDA).withCurrentLoan(overdueLoan).build();
        borrowerRecords.addBorrower(borrower);
        BorrowerId servingBorrowerId = borrower.getBorrowerId();

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        RenewCommand renewCommand = new RenewCommand(INDEX_FIRST_BOOK);

        String actualMessage;
        try {
            actualMessage = renewCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(MESSAGE_BOOK_IS_OVERDUE, overdueBook);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void equals() {
        RenewCommand renewCommand1 = new RenewCommand(INDEX_FIRST_BOOK);
        RenewCommand renewCommand2 = new RenewCommand(INDEX_FIRST_BOOK);
        RenewCommand renewCommand3 = new RenewCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(renewCommand1.equals(renewCommand1));

        // same values -> returns true
        assertTrue(renewCommand1.equals(renewCommand2));

        // different values -> returns false
        assertFalse(renewCommand1.equals(renewCommand3));

        // null -> returns false
        assertFalse(renewCommand1.equals(null));

        // different type -> returns false
        assertFalse(renewCommand1.equals(1));
    }
}
