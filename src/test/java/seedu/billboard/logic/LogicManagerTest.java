package seedu.billboard.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static seedu.billboard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.billboard.logic.commands.CommandTestUtil.AMOUNT_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.DESCRIPTION_DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.NAME_DESC_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.DINNER;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.billboard.logic.commands.AddCommand;
import seedu.billboard.logic.commands.CommandResult;
import seedu.billboard.logic.commands.ListCommand;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.storage.JsonBillboardStorage;
import seedu.billboard.storage.JsonUserPrefsStorage;
import seedu.billboard.storage.StorageManager;
import seedu.billboard.testutil.ExpenseBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonBillboardStorage billboardStorage =
                new JsonBillboardStorage(temporaryFolder.resolve("billboard.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(billboardStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonBillboardIoExceptionThrowingStub
        JsonBillboardStorage billboardStorage =
                new JsonBillboardIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(billboardStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_DINNER + DESCRIPTION_DESC_DINNER
                + AMOUNT_DESC_DINNER;
        Expense expectedExpense = new ExpenseBuilder(DINNER).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addExpense(expectedExpense);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredExpenseList().remove(0));
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
        Model expectedModel = new ModelManager(model.getBillboardExpenses(), new UserPrefs());
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
    private static class JsonBillboardIoExceptionThrowingStub extends JsonBillboardStorage {
        private JsonBillboardIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveBillboard(ReadOnlyBillboard addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
