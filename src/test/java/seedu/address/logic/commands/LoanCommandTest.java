package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_7;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;
import static seedu.address.testutil.TypicalBorrowers.HOON;
import static seedu.address.testutil.TypicalLoans.LOAN_7;
import static seedu.address.testutil.UserSettingsBuilder.DEFAULT_LOAN_PERIOD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanList;
import seedu.address.testutil.BookBuilder;

class LoanCommandTest {

    @Test
    public void constructor_nullSerialNumber_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LoanCommand(null));
    }

    @Test
    public void execute_validUnloanedBook_loanSuccessful() {
        SerialNumber toLoan = BOOK_1.getSerialNumber();
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);
        BorrowerId servingBorrowerId = HOON.getBorrowerId();

        Model model = new ModelManager(getTypicalCatalog(), new LoanRecords(),
                borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        LoanCommand loanCommand = new LoanCommand(toLoan);

        Loan loan = new Loan(new LoanId("L000001"), toLoan, servingBorrowerId,
                DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(DEFAULT_LOAN_PERIOD));
        Book loanedOutBook = BOOK_1.loanOut(loan);
        Book updatedLoanedOutBook = loanedOutBook.addToLoanHistory(loan);

        String actualMessage;
        try {
            actualMessage = loanCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        } finally {
            LoanSlipUtil.clearSession();
        }
        String expectedMessage = String.format(LoanCommand.MESSAGE_SUCCESS, updatedLoanedOutBook,
                model.getServingBorrower());
        assertEquals(actualMessage, expectedMessage);
        LoanList updatedHistory = new LoanList();
        updatedHistory.add(loan);
        assertTrue(model.getServingBorrower().hasCurrentLoan(loan));
        assertEquals(updatedLoanedOutBook.getLoanHistory(), updatedHistory);
    }

    @Test
    public void execute_notInServeMode_loanUnsuccessful() {
        SerialNumber toLoan = BOOK_1.getSerialNumber();
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);

        Model model = new ModelManager(getTypicalCatalog(), new LoanRecords(),
                borrowerRecords, new UserPrefs());

        LoanCommand loanCommand = new LoanCommand(toLoan);
        String expectedMessage = MESSAGE_NOT_IN_SERVE_MODE;

        assertCommandFailure(loanCommand, model, expectedMessage);
    }

    @Test
    public void execute_noSuchBook_loanUnsuccessful() {
        SerialNumber toLoan = BOOK_1.getSerialNumber();
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);
        BorrowerId servingBorrowerId = HOON.getBorrowerId();

        Model model = new ModelManager(new Catalog(), new LoanRecords(),
                borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        LoanCommand loanCommand = new LoanCommand(toLoan);
        String expectedMessage = MESSAGE_NO_SUCH_BOOK;

        assertCommandFailure(loanCommand, model, expectedMessage);
    }

    @Test
    public void execute_bookAlreadyOnLoan_loanUnsuccessful() {
        SerialNumber toLoan = BOOK_7.getSerialNumber();
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);
        BorrowerId servingBorrowerId = HOON.getBorrowerId();
        Catalog catalog = new Catalog();
        Book onLoan = new BookBuilder(BOOK_7).build().loanOut(LOAN_7);
        catalog.addBook(onLoan);

        Model model = new ModelManager(catalog, new LoanRecords(),
                borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        LoanCommand loanCommand = new LoanCommand(toLoan);
        String expectedMessage = String.format(MESSAGE_BOOK_ON_LOAN, BOOK_7);

        assertCommandFailure(loanCommand, model, expectedMessage);

    }

    @Test
    public void equals() {
        LoanCommand loanCommand1 = new LoanCommand(new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1));
        LoanCommand loanCommand2 = new LoanCommand(new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1));
        LoanCommand loanCommand3 = new LoanCommand(new SerialNumber(VALID_SERIAL_NUMBER_BOOK_2));

        // same object -> returns true
        assertTrue(loanCommand1.equals(loanCommand1));

        // same values -> returns true
        assertTrue(loanCommand1.equals(loanCommand2));

        // different values -> returns false
        assertFalse(loanCommand1.equals(loanCommand3));

        // null -> returns false
        assertFalse(loanCommand1.equals(null));

        // different type -> returns false
        assertFalse(loanCommand1.equals(1));
    }
}
