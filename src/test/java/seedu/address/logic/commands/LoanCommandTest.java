package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_BOOK_ON_LOAN;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_BOOK;
import static seedu.address.commons.core.UserSettings.DEFAULT_LOAN_PERIOD;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_7;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;
import static seedu.address.testutil.TypicalBorrowers.HOON;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;
import static seedu.address.testutil.TypicalLoans.getTypicalLoanRecords;

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
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;

class LoanCommandTest {
    private Model model = new ModelManager(getTypicalCatalog(), getTypicalLoanRecords(),
            getTypicalBorrowerRecords(), new UserPrefs());

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
        Book loanedOutBook = new Book(BOOK_1.getTitle(), BOOK_1.getSerialNumber(),
                BOOK_1.getAuthor(), loan, BOOK_1.getGenres());

        String actualMessage;
        try {
            actualMessage = loanCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(LoanCommand.MESSAGE_SUCCESS, loanedOutBook, HOON);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_notInServeMode_loanUnsuccessful() {
        SerialNumber toLoan = BOOK_1.getSerialNumber();
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);

        Model model = new ModelManager(getTypicalCatalog(), new LoanRecords(),
                borrowerRecords, new UserPrefs());

        LoanCommand loanCommand = new LoanCommand(toLoan);

        String actualMessage;
        try {
            actualMessage = loanCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = MESSAGE_NOT_IN_SERVE_MODE;
        assertEquals(actualMessage, expectedMessage);
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

        String actualMessage;
        try {
            actualMessage = loanCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = MESSAGE_NO_SUCH_BOOK;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void execute_bookAlreadyOnLoan_loanUnsuccessful() {
        SerialNumber toLoan = BOOK_7.getSerialNumber();
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);
        BorrowerId servingBorrowerId = HOON.getBorrowerId();
        Catalog catalog = new Catalog();
        catalog.addBook(BOOK_7);

        Model model = new ModelManager(catalog, new LoanRecords(),
                borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        LoanCommand loanCommand = new LoanCommand(toLoan);

        String actualMessage;
        try {
            actualMessage = loanCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            actualMessage = e.getMessage();
        }
        String expectedMessage = String.format(MESSAGE_BOOK_ON_LOAN, BOOK_7);
        assertEquals(actualMessage, expectedMessage);
    }
}
