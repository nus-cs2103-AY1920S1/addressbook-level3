package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_NOT_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_LOANED_BY_BORROWER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_6;
import static seedu.address.testutil.TypicalBooks.BOOK_7;
import static seedu.address.testutil.TypicalBorrowers.HOON;
import static seedu.address.testutil.TypicalBorrowers.IDA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalLoans.LOAN_7;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.BorrowerId;

class ReturnCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReturnCommand(null));
    }

    @Test
    public void execute_validLoanedBook_returnSuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);
        BorrowerId servingBorrowerId = IDA.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        ReturnCommand returnCommand = new ReturnCommand(INDEX_FIRST_BOOK);

        Book returnedBook = new Book(BOOK_7.getTitle(), BOOK_7.getSerialNumber(),
                BOOK_7.getAuthor(), null, BOOK_7.getGenres());

        String actualMessage;
        try {
            actualMessage = returnCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(ReturnCommand.MESSAGE_SUCCESS, returnedBook, IDA);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_notInServeMode_returnUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());

        ReturnCommand returnCommand = new ReturnCommand(INDEX_FIRST_BOOK);

        String actualMessage;
        try {
            actualMessage = returnCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = MESSAGE_NOT_IN_SERVE_MODE;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_noSuchIndex_returnUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);
        BorrowerId servingBorrowerId = IDA.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        ReturnCommand returnCommand = new ReturnCommand(INDEX_SECOND_BOOK);

        String actualMessage;
        try {
            actualMessage = returnCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_bookNotOnLoan_returnUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);
        BorrowerId servingBorrowerId = IDA.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_6);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        ReturnCommand returnCommand = new ReturnCommand(INDEX_FIRST_BOOK);

        Book returnedBook = new Book(BOOK_6.getTitle(), BOOK_6.getSerialNumber(),
                BOOK_6.getAuthor(), null, BOOK_6.getGenres());

        String actualMessage;
        try {
            actualMessage = returnCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(MESSAGE_BOOK_NOT_ON_LOAN, returnedBook);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_borrowerDoesNotLoanThisBook_unsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);
        BorrowerId servingBorrowerId = HOON.getBorrowerId();

        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        ReturnCommand returnCommand = new ReturnCommand(INDEX_FIRST_BOOK);

        String actualMessage;
        try {
            actualMessage = returnCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(MESSAGE_NOT_LOANED_BY_BORROWER, HOON, BOOK_7);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void equals() {
        ReturnCommand returnCommand1 = new ReturnCommand(INDEX_FIRST_BOOK);
        ReturnCommand returnCommand2 = new ReturnCommand(INDEX_FIRST_BOOK);
        ReturnCommand returnCommand3 = new ReturnCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(returnCommand1.equals(returnCommand1));

        // same values -> returns true
        assertTrue(returnCommand1.equals(returnCommand2));

        // different values -> returns false
        assertFalse(returnCommand1.equals(returnCommand3));

        // null -> returns false
        assertFalse(returnCommand1.equals(null));

        // different type -> returns false
        assertFalse(returnCommand1.equals(1));
    }
}
