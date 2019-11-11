package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.Borrower;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BorrowerBuilder;
import seedu.address.testutil.EditBorrowerDescriptorBuilder;
import seedu.address.testutil.SetUserSettingsDescriptorBuilder;

public class UndoCommandTest {
    private static final Book VALID_BOOK = new BookBuilder()
            .withTitle("Hello World")
            .withAuthor("Yeo Tong")
            .withSerialNumber("B00000")
            .build();

    private static final Borrower VALID_BORROWER = new BorrowerBuilder()
            .withName("Yeo Tong")
            .withPhone("10101010")
            .withEmail("test@testing.com")
            .build();

    private Model model = new ModelManager();
    private UndoCommand undoCommand = new UndoCommand();


    @Test
    public void execute_noUndoableCommand_throwsCommandException() {
        assertThrows(CommandException.class, () -> undoCommand.execute(model));
    }

    @Test
    public void execute_undoAfterServeDone_throwsCommandException() {
        ensureBorrowerInModel(VALID_BORROWER, model);

        try {
            AddCommand addCommand = new AddCommand(VALID_BOOK);
            addCommand.execute(model);
            model.commitCommand(addCommand);

            new ServeCommand(VALID_BORROWER.getBorrowerId()).execute(model);
            assertTrue(model.isServeMode());
            assertThrows(CommandException.class, () -> undoCommand.execute(model));

            LoanCommand loanCommand = new LoanCommand(VALID_BOOK.getSerialNumber());
            loanCommand.execute(model);
            model.commitCommand(loanCommand);

            new DoneCommand().execute(model);
            assertFalse(model.isServeMode());
            assertThrows(CommandException.class, () -> undoCommand.execute(model));
        } catch (CommandException e) {
            fail();
        }
    }

    @Test void execute_undoAfterNonReversibleCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);

        AddCommand addCommand = new AddCommand(VALID_BOOK);
        assertNotNull(addCommand);

        executeModel(addCommand, updatedModel);

        new ClearCommand().execute(updatedModel);

        assertEquals(model.getCatalog(), updatedModel.getCatalog());
    }

    @Test
    public void execute_undoAddCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);

        AddCommand addCommand = new AddCommand(VALID_BOOK);
        assertNotNull(addCommand);

        executeModel(addCommand, updatedModel);

        assertEquals(model.getCatalog(), updatedModel.getCatalog());
    }

    @Test
    public void execute_undoDeleteByIndexCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        ensureBookInModel(VALID_BOOK, updatedModel);
        ensureBookInModel(VALID_BOOK, model);

        // Index is 1 because catalog is sorted and added book has serial number B00000.
        DeleteByIndexCommand deleteByIndexCommand = new DeleteByIndexCommand(Index.fromOneBased(1));
        assertNotNull(deleteByIndexCommand);

        executeModel(deleteByIndexCommand, updatedModel);

        assertEquals(model.getCatalog(), updatedModel.getCatalog());
    }

    @Test
    public void execute_undoDeleteBySerialNumberCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        ensureBookInModel(VALID_BOOK, updatedModel);
        ensureBookInModel(VALID_BOOK, model);

        SerialNumber serialNumber = VALID_BOOK.getSerialNumber();
        DeleteBySerialNumberCommand deleteBySerialNumberCommand = new DeleteBySerialNumberCommand(serialNumber);
        assertNotNull(deleteBySerialNumberCommand);

        executeModel(deleteBySerialNumberCommand, updatedModel);

        assertEquals(model.getCatalog(), updatedModel.getCatalog());
    }

    @Test
    public void execute_undoEditBorrowerCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        ensureBorrowerInModel(VALID_BORROWER, updatedModel);
        ensureBorrowerInModel(VALID_BORROWER, model);

        EditBorrowerCommand.EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder()
                .withName("Test")
                .build();

        EditBorrowerCommand editBorrowerCommand = null;
        try {
            new ServeCommand(VALID_BORROWER.getBorrowerId()).execute(updatedModel);
            editBorrowerCommand = new EditBorrowerCommand(descriptor);
        } catch (CommandException e) {
            fail();
        }

        assertNotNull(editBorrowerCommand);

        executeModel(editBorrowerCommand, updatedModel);

        assertEquals(model.getBorrowerRecords(), updatedModel.getBorrowerRecords());
    }

    @Test
    public void execute_undoLoanCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        ensureBorrowerInModel(VALID_BORROWER, updatedModel);
        ensureBorrowerInModel(VALID_BORROWER, model);

        ensureBookInModel(VALID_BOOK, updatedModel);
        ensureBookInModel(VALID_BOOK, model);

        LoanCommand loanCommand = new LoanCommand(VALID_BOOK.getSerialNumber());
        assertNotNull(loanCommand);

        try {
            new ServeCommand(VALID_BORROWER.getBorrowerId()).execute(updatedModel);
        } catch (CommandException e) {
            fail();
        }

        executeModel(loanCommand, updatedModel);

        assertEquals(model.getBorrowerRecords(), updatedModel.getBorrowerRecords());
        assertEquals(model.getLoanRecords(), updatedModel.getLoanRecords());
        assertEquals(model.getCatalog(), updatedModel.getCatalog());
    }

    @Test
    public void execute_undoRegisterCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        RegisterCommand registerCommand = new RegisterCommand(VALID_BORROWER);
        assertNotNull(registerCommand);

        executeModel(registerCommand, updatedModel);

        assertEquals(model.getBorrowerRecords(), updatedModel.getBorrowerRecords());
    }

    @Test
    public void execute_undoRenewCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        ensureBorrowerInModel(VALID_BORROWER, updatedModel);
        ensureBorrowerInModel(VALID_BORROWER, model);

        ensureBookInModel(VALID_BOOK, updatedModel);
        ensureBookInModel(VALID_BOOK, model);

        SetCommand setCommand = new SetCommand(new SetUserSettingsDescriptorBuilder()
                .withMaxRenews("2")
                .build());
        assertNotNull(setCommand);

        ServeCommand serveCommand = new ServeCommand(VALID_BORROWER.getBorrowerId());
        assertNotNull(serveCommand);

        LoanCommand loanCommand = new LoanCommand(VALID_BOOK.getSerialNumber());
        assertNotNull(loanCommand);

        RenewCommand renewCommand = new RenewCommand(Index.fromOneBased(1));
        assertNotNull(renewCommand);

        try {
            setCommand.execute(updatedModel);
            serveCommand.execute(updatedModel);
            loanCommand.execute(updatedModel);
            LoanSlipUtil.clearSession();
        } catch (CommandException e) {
            fail();
        }

        executeModel(renewCommand, updatedModel);

        assertEquals(model.getBorrowerRecords(), updatedModel.getBorrowerRecords());
        assertEquals(model.getCatalog(), updatedModel.getCatalog());
    }

    @Test
    public void execute_undoSetCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        SetCommand setCommand = new SetCommand(new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod("1")
                .withRenewPeriod("1")
                .withFineIncrement("1")
                .withMaxRenews("3")
                .build());
        assertNotNull(setCommand);

        executeModel(setCommand, updatedModel);

        assertEquals(model.getUserSettings(), updatedModel.getUserSettings());
    }

    @Test
    public void execute_undoToggleUiCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        ToggleUiCommand toggleUiCommand = new ToggleUiCommand();
        assertNotNull(toggleUiCommand);

        executeModel(toggleUiCommand, updatedModel);

        assertEquals(model.getUserPrefs(), updatedModel.getUserPrefs());
    }

    @Test
    public void execute_undoUnregisterCommand_successful() {
        Model updatedModel = new ModelManager();
        assertNotNull(updatedModel);
        assertEquals(model, updatedModel);

        ensureBorrowerInModel(VALID_BORROWER, updatedModel);
        ensureBorrowerInModel(VALID_BORROWER, model);

        UnregisterCommand unregisterCommand = new UnregisterCommand(VALID_BORROWER.getBorrowerId());
        assertNotNull(unregisterCommand);

        executeModel(unregisterCommand, updatedModel);

        assertEquals(model.getBorrowerRecords(), updatedModel.getBorrowerRecords());
    }

    @Test
    public void equal() {
        UndoCommand standardCommand = new UndoCommand();

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);
    }

    /**
     * Executes the {@code ReversibleCommand} and undoes the command.
     *
     * @param command to execute and undo.
     * @param modelToUpdate model that the command is executed on.
     */
    private void executeModel(ReversibleCommand command, Model modelToUpdate) {
        assertNotNull(command);

        // Execute command.
        try {
            command.execute(modelToUpdate);
        } catch (CommandException e) {
            fail();
        }
        modelToUpdate.commitCommand(command);

        // Execute undo command;
        try {
            undoCommand.execute(modelToUpdate);
        } catch (CommandException e) {
            fail();
        }
    }

    /**
     * Ensures that the book is in the model specified.
     *
     * @param book to be ensured.
     * @param modelToAdd the book to if not present.
     */
    private void ensureBookInModel(Book book, Model modelToAdd) {
        if (!modelToAdd.hasBook(book.getSerialNumber())) {
            try {
                new AddCommand(book).execute(modelToAdd);
            } catch (CommandException e) {
                fail();
            }
        }
        assertTrue(modelToAdd.hasBook(book.getSerialNumber()));
    }

    /**
     * Ensures that the borrower is in the specified model.
     *
     * @param borrower to be ensured.
     * @param modelToAdd the borrower to add to the model.
     */
    private void ensureBorrowerInModel(Borrower borrower, Model modelToAdd) {
        if (!modelToAdd.hasBorrower(borrower)) {
            try {
                new RegisterCommand(borrower).execute(modelToAdd);
            } catch (CommandException e) {
                fail();
            }
        }
        assertTrue(modelToAdd.hasBorrower(borrower));
    }
}
