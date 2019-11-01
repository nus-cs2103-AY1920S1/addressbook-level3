package seedu.ichifund.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ichifund.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.ichifund.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.ichifund.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.ichifund.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.PERSON_AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ichifund.logic.commands.AddCommand;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.HelpCommand;
import seedu.ichifund.logic.commands.ListCommand;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.commands.transaction.DeleteTransactionCommand;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ModelManager;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.UserPrefs;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.storage.JsonFundBookStorage;
import seedu.ichifund.storage.JsonUserPrefsStorage;
import seedu.ichifund.storage.StorageManager;
import seedu.ichifund.testutil.PersonBuilder;
import seedu.ichifund.testutil.TransactionBuilder;
import seedu.ichifund.testutil.TransactionUtil;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonFundBookStorage addressBookStorage =
                new JsonFundBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteTransactionCommand = DeleteTransactionCommand.COMMAND_WORD + " 20";
        assertCommandException(deleteTransactionCommand, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String helpCommand = HelpCommand.COMMAND_WORD;
        assertCommandSuccess(helpCommand, HelpCommand.SHOWING_HELP_MESSAGE, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonFundBookStorage fundBookStorage =
                new JsonFundBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(fundBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        Transaction expectedTransaction = new TransactionBuilder().build();
        String addTransactionCommand = TransactionUtil.getAddTransactionCommand(expectedTransaction);
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTransaction(expectedTransaction);
        expectedModel.updateTransactionContext(expectedTransaction);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addTransactionCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getFundBook(), new UserPrefs());
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

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFundBookIoExceptionThrowingStub extends JsonFundBookStorage {
        private JsonFundBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFundBook(ReadOnlyFundBook fundBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
