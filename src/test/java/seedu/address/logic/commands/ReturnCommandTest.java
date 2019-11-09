package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_7;
import static seedu.address.testutil.TypicalBorrowers.HOON;
import static seedu.address.testutil.TypicalBorrowers.IDA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalLoans.LOAN_7;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.FineUtil;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.loan.Loan;
import seedu.address.testutil.BookBuilder;

class ReturnCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReturnCommand(null));
    }

    @Test
    public void execute_validLoanedBook_returnSuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);

        Catalog catalog = new Catalog();
        Book onLoan = new BookBuilder(BOOK_7).withLoan(LOAN_7).build().addToLoanHistory(LOAN_7);
        catalog.addBook(onLoan);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model actualModel = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());
        actualModel.setServingBorrower(IDA);
        Model expectedModel = new ModelManager(new Catalog(catalog), new LoanRecords(loanRecords),
                new BorrowerRecords(borrowerRecords), new UserPrefs());
        expectedModel.setServingBorrower(IDA);

        ReturnCommand returnCommand = new ReturnCommand(INDEX_FIRST_BOOK);

        LocalDate returnDate = DateUtil.getTodayDate();
        int dailyFineIncrement = expectedModel.getUserSettings().getFineIncrement();
        int fineAmount = DateUtil.getNumOfDaysOverdue(LOAN_7.getDueDate(), returnDate) * dailyFineIncrement;
        Loan returnedLoan = LOAN_7.returnLoan(returnDate, fineAmount);
        Book returnedBook = onLoan.returnBook().addToLoanHistory(LOAN_7);

        expectedModel.setBook(onLoan, returnedBook);
        expectedModel.servingBorrowerReturnLoan(LOAN_7, returnedLoan);
        expectedModel.updateLoan(LOAN_7, returnedLoan);

        String expectedMessage = String.format(ReturnCommand.MESSAGE_SUCCESS, returnedBook,
                actualModel.getServingBorrower(), FineUtil.centsToDollarString(fineAmount)).trim();

        assertCommandSuccess(returnCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notInServeMode_returnUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(IDA);

        Catalog catalog = new Catalog();
        Book onLoan = new BookBuilder(BOOK_7).withLoan(LOAN_7).build();
        catalog.addBook(onLoan);

        LoanRecords loanRecords = new LoanRecords();
        loanRecords.addLoan(LOAN_7);

        Model model = new ModelManager(catalog, loanRecords, borrowerRecords, new UserPrefs());

        ReturnCommand returnCommand = new ReturnCommand();
        String expectedMessage = MESSAGE_NOT_IN_SERVE_MODE;

        assertCommandFailure(returnCommand, model, expectedMessage);
    }

    @Test
    public void execute_noSuchIndex_returnUnsuccessful() {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);

        Model model = new ModelManager(new Catalog(), new LoanRecords(), borrowerRecords, new UserPrefs());
        model.setServingBorrower(HOON);

        ReturnCommand returnCommand = new ReturnCommand(INDEX_SECOND_BOOK);
        String expectedMessage = MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;

        assertCommandFailure(returnCommand, model, expectedMessage);
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
