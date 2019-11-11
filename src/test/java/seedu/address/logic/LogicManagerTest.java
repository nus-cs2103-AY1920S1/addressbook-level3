package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ALCOHOL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.VODKA;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListDefaultExpensesCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.BudgetList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBudgetList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;
import seedu.address.storage.JsonBudgetListStorage;
import seedu.address.storage.JsonExchangeDataStorage;
import seedu.address.storage.JsonExpenseListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.ExpenseBuilder;

public class LogicManagerTest {

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonExpenseListStorage expenseListStorage =
            new JsonExpenseListStorage(temporaryFolder.resolve("expenseList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonExchangeDataStorage exchangeDataStorage =
            new JsonExchangeDataStorage(temporaryFolder.resolve("exchangedata.json"));
        JsonBudgetListStorage budgetListStorage =
            new JsonBudgetListStorage(temporaryFolder.resolve("budgetList.json"));
        StorageManager storage = new StorageManager(expenseListStorage, budgetListStorage,
            exchangeDataStorage, userPrefsStorage);
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
        String listDefaultExpensesCommand = ListDefaultExpensesCommand.COMMAND_WORD;
        assertCommandSuccess(listDefaultExpensesCommand, ListDefaultExpensesCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonExpenseListIoExceptionThrowingStub
        JsonExpenseListStorage expenseListStorage =
            new JsonExpenseListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionExpenseList.json"));
        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonBudgetListStorage budgetListStorage =
            new JsonBudgetListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionBudgetList.json"));
        JsonExchangeDataStorage exchangeDataStorage =
            new JsonExchangeDataStorage(temporaryFolder.resolve("ioExceptionExchangeData.json"));
        StorageManager storage = new StorageManager(expenseListStorage, budgetListStorage, exchangeDataStorage,
            userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addExpenseCommand =
            AddExpenseCommand.COMMAND_WORD + NAME_DESC_VODKA + AMOUNT_DESC_VODKA + DATE_DESC_VODKA
                + TAG_DESC_ALCOHOL;
        Expense expectedExpense = new ExpenseBuilder(VODKA).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addExpense(expectedExpense);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addExpenseCommand, CommandException.class, expectedMessage, expectedModel);
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
     *
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
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getExpenseList(), new BudgetList(), model.getExchangeData(),
            new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
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
    private static class JsonExpenseListIoExceptionThrowingStub extends JsonExpenseListStorage {

        private JsonExpenseListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveExpenseList(ReadOnlyExpenseList expenseList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonBudgetListIoExceptionThrowingStub extends JsonBudgetListStorage {

        private JsonBudgetListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveBudgetList(ReadOnlyBudgetList budgetList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
