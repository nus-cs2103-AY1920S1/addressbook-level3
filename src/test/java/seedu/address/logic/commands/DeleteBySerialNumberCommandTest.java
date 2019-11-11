package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.testutil.BookBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteByIndexCommand}.
 */
public class DeleteBySerialNumberCommandTest {
    // TODO implement and add getTypicalLoanRecords() and getTypicalBorrowerRecords()
    private Model model = new ModelManager(
            getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

    @Test
    public void execute_validSerialNumber_success() {
        // B00001 exists in typicalCatalog
        SerialNumber sn = new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        Book bookToDelete = retrieveBookFromCatalog(model, sn);
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(sn);

        String expectedMessage = String.format(deleteBySerialNumberCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        ModelManager expectedModel = new ModelManager(
                model.getCatalog(), model.getLoanRecords(), model.getBorrowerRecords(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);

        assertCommandSuccess(deleteBySerialNumberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSerialNumber_throwsCommandException() {
        SerialNumber outOfBoundSerialNumber = new SerialNumber("B99999");
        DeleteBySerialNumberCommand deleteBySerialNumberCommand =
                new DeleteBySerialNumberCommand(outOfBoundSerialNumber);

        assertCommandFailure(deleteBySerialNumberCommand, model, Messages.MESSAGE_NO_SUCH_BOOK);
    }

    @Test
    public void getUndoCommand_beforeExecute_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);

        assertThrows(NullPointerException.class, deleteBySerialNumberCommand::getUndoCommand);
    }

    @Test
    public void getUndoCommand_afterExecuteInvalidCommand_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);
        Model model = new ModelManager();

        try {
            deleteBySerialNumberCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, deleteBySerialNumberCommand::getUndoCommand);
    }

    @Test
    public void getUndoCommand_afterExecuteValidCommand_returnsUndoCommand() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);
        Model model = new ModelManager();

        // Adds a book to the catalog
        model.addBook(validBook);

        try {
            deleteBySerialNumberCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(deleteBySerialNumberCommand.getUndoCommand(), new AddCommand(validBook));
    }

    @Test
    public void getRedoCommand_beforeExecute_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);

        assertThrows(NullPointerException.class, deleteBySerialNumberCommand::getRedoCommand);
    }

    @Test
    public void getRedoCommand_afterExecuteInvalidCommand_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);
        Model model = new ModelManager();

        try {
            deleteBySerialNumberCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, deleteBySerialNumberCommand::getRedoCommand);
    }

    @Test
    public void getRedoCommand_afterExecuteValidCommand_returnsRedoCommand() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);
        Model model = new ModelManager();

        // Adds a book to the catalog
        model.addBook(validBook);

        try {
            deleteBySerialNumberCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(deleteBySerialNumberCommand.getRedoCommand(), deleteBySerialNumberCommand);
    }

    @Test
    public void getCommandResult_beforeExecute_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);

        assertThrows(NullPointerException.class, deleteBySerialNumberCommand::getCommandResult);
    }

    @Test
    public void getCommandResult_afterExecuteInvalidCommand_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);
        Model model = new ModelManager();

        try {
            deleteBySerialNumberCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, deleteBySerialNumberCommand::getCommandResult);
    }

    @Test
    public void getCommandResult_afterExecuteValidCommand_returnsCommandResult() {
        Book validBook = new BookBuilder().build();
        SerialNumber serialNumber = validBook.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);
        Model model = new ModelManager();

        // Adds a book to the catalog
        model.addBook(validBook);

        CommandResult expectedCommandResult = new CommandResult("Test");

        try {
            expectedCommandResult = deleteBySerialNumberCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(deleteBySerialNumberCommand.getCommandResult(), expectedCommandResult);
    }

    @Test
    public void equals() {
        SerialNumber sn1 = new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        SerialNumber sn2 = new SerialNumber(VALID_SERIAL_NUMBER_BOOK_2);
        DeleteBySerialNumberCommand deleteFirstCommand = new DeleteBySerialNumberCommand(sn1);
        DeleteBySerialNumberCommand deleteSecondCommand = new DeleteBySerialNumberCommand(sn2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBySerialNumberCommand deleteFirstCommandCopy = new DeleteBySerialNumberCommand(sn1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns Book from Catalog under the model.
     *
     * @param model Model to retrieve Book from.
     * @param serialNumber Serial number of target book.
     * @return Book object, the target book.
     */
    private Book retrieveBookFromCatalog(Model model, SerialNumber serialNumber) {
        return (Book) model.getCatalog()
                .getBookList()
                .stream()
                .filter(book -> book.getSerialNumber().equals(serialNumber))
                .toArray()[0];
    }
}
