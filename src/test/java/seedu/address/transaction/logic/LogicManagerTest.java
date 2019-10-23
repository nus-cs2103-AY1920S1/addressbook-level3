package seedu.address.transaction.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_COMMAND;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.logic.commands.BackCommand;
import seedu.address.transaction.logic.commands.CommandResult;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.storage.Storage;
import seedu.address.transaction.storage.StorageManager;

class LogicManagerTest {
    private File file;
    private File rFile;
    private Model model;
    private seedu.address.person.model.Model personModel;
    private Storage storage;
    private seedu.address.reimbursement.storage.Storage reimbursementStorage;
    private seedu.address.reimbursement.model.Model reimbursementModel;
    private Logic logic;

    LogicManagerTest() throws Exception {
        try {

            model =
                    new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
            personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            file = File.createTempFile("testing", "tempTransaction.txt");
            storage = new StorageManager(file, personModel);
            rFile = File.createTempFile("testing","tempReimbursement.txt");
            model.getTransactionList();
            reimbursementStorage =
                    new seedu.address.reimbursement.storage.StorageManager(rFile);
            reimbursementModel =
                    new seedu.address.reimbursement.model.ModelManager(
                            reimbursementStorage.getReimbursementFromFile(model.getTransactionList()));
            logic =
                    new LogicManager(model, storage, personModel, reimbursementModel,
                            reimbursementStorage);
        } catch (IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        }
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_NO_SUCH_COMMAND);
    }
    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 20";
        assertCommandException(deleteCommand, MESSAGE_NO_SUCH_TRANSACTION);
    }
    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = BackCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, "", model);
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredList().delete(0));
    }
    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) {
        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(expectedModel, model);
        } catch (Exception e) {
            throw new AssertionError("There should not be an error");
        }

    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, Exception.class, expectedMessage);
    }
    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, seedu.address.transaction.model.Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, Exception.class, expectedMessage);
    }
    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new seedu.address.transaction.model.ModelManager(model.getTransactionList());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }
    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }
}
