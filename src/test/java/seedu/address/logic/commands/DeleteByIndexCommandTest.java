package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;
import static seedu.address.testutil.TypicalBorrowers.HOON;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalLoans.LOAN_1;
import static seedu.address.testutil.UserSettingsBuilder.DEFAULT_LOAN_PERIOD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.testutil.BookBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteByIndexCommand}.
 */
public class DeleteByIndexCommandTest {
    // TODO implement and add getTypicalLoanRecords() and getTypicalBorrowerRecords()
    private Model model = new ModelManager(
            getTypicalCatalog(), new LoanRecords(), getTypicalBorrowerRecords(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book loanedBookToDelete = bookToDelete.loanOut(LOAN_1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(DeleteByIndexCommand.MESSAGE_DELETE_BOOK_SUCCESS, loanedBookToDelete);

        ModelManager expectedModel = new ModelManager(
                model.getCatalog(), model.getLoanRecords(), model.getBorrowerRecords(), new UserPrefs());
        expectedModel.deleteBook(loanedBookToDelete);

        assertCommandSuccess(deleteByIndexCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteByIndexCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(DeleteByIndexCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        Model expectedModel = new ModelManager(
                model.getCatalog(), model.getLoanRecords(), model.getBorrowerRecords(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        showNoBook(expectedModel);

        assertCommandSuccess(deleteByIndexCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalog().getBookList().size());

        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteByIndexCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void getUndoCommand_beforeExecute_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);

        assertThrows(NullPointerException.class, deleteByIndexCommand::getUndoCommand);
    }

    @Test
    public void getUndoCommand_afterExecuteInvalidCommand_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);
        Model model = new ModelManager();

        try {
            deleteByIndexCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, deleteByIndexCommand::getUndoCommand);
    }

    @Test
    public void getUndoCommand_afterExecuteValidCommand_returnsUndoCommand() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);
        Model model = new ModelManager();

        // Adds a book to the catalog
        Book validBook = new BookBuilder().build();
        model.addBook(validBook);

        try {
            deleteByIndexCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(deleteByIndexCommand.getUndoCommand(), new AddCommand(validBook));
    }


    @Test
    public void getRedoCommand_beforeExecute_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);

        assertThrows(NullPointerException.class, deleteByIndexCommand::getRedoCommand);
    }

    @Test
    public void getRedoCommand_afterExecuteInvalidCommand_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);
        Model model = new ModelManager();

        try {
            deleteByIndexCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, deleteByIndexCommand::getRedoCommand);
    }

    @Test
    public void getRedoCommand_afterExecuteValidCommand_returnsRedoCommand() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);
        Model model = new ModelManager();

        // Adds a book to the catalog
        Book validBook = new BookBuilder().build();
        model.addBook(validBook);

        try {
            deleteByIndexCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(deleteByIndexCommand.getRedoCommand(),
                new DeleteBySerialNumberCommand(validBook.getSerialNumber()));
    }

    @Test
    public void getCommandResult_beforeExecute_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);

        assertThrows(NullPointerException.class, deleteByIndexCommand::getCommandResult);
    }

    @Test
    public void getCommandResult_afterExecuteInvalidCommand_throwsNullPointerException() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);
        Model model = new ModelManager();

        try {
            deleteByIndexCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, deleteByIndexCommand::getCommandResult);
    }

    @Test
    public void getCommandResult_afterExecuteValidCommand_returnsCommandResult() {
        Index validIndex = Index.fromOneBased(1);
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(validIndex);
        Model model = new ModelManager();

        // Adds a book to the catalog
        Book validBook = new BookBuilder().build();
        model.addBook(validBook);

        CommandResult expectedCommandResult = new CommandResult("Test");

        try {
            expectedCommandResult = deleteByIndexCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(deleteByIndexCommand.getCommandResult(), expectedCommandResult);
    }

    @Test
    public void equals() {
        DeleteByIndexCommand deleteFirstCommand = new DeleteByIndexCommand(INDEX_FIRST_BOOK);
        DeleteByIndexCommand deleteSecondCommand = new DeleteByIndexCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByIndexCommand deleteFirstCommandCopy = new DeleteByIndexCommand(INDEX_FIRST_BOOK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void markBookAsReturned() {
        SerialNumber toLoan = BOOK_1.getSerialNumber();
        Book target = new BookBuilder(BOOK_1).build();
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        borrowerRecords.addBorrower(HOON);
        BorrowerId servingBorrowerId = HOON.getBorrowerId();

        model = new ModelManager(getTypicalCatalog(), new LoanRecords(),
                borrowerRecords, new UserPrefs());
        model.setServingBorrower(servingBorrowerId);

        LoanCommand loanCommand = new LoanCommand(toLoan);

        Loan loan = new Loan(new LoanId("L000001"), toLoan, servingBorrowerId,
                DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(DEFAULT_LOAN_PERIOD));
        Book loanedOutBook = target.loanOut(loan);
        assertTrue(loanedOutBook.isCurrentlyLoanedOut());

        try {
            LoanSlipUtil.clearSession();
            loanCommand.execute(model).getFeedbackToUser();
        } catch (CommandException e) {
            assert false : "Command should not fail here";
        }

        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(INDEX_FIRST_BOOK);

        deleteByIndexCommand.returnBook(model, loanedOutBook);
        Book result2 = model.getBook(target.getSerialNumber());
        assertFalse(result2.isCurrentlyLoanedOut());

    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBook(Model model) {
        model.updateFilteredBookList(p -> false);

        assertTrue(model.getFilteredBookList().isEmpty());
    }
}
