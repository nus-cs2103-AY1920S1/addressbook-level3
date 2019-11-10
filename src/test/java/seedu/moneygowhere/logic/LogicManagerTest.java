package seedu.moneygowhere.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_SPENDING_DISPLAYED_INDEX;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.COST_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.AMY;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;
import static seedu.moneygowhere.testutil.TypicalSpendings.DESSERT;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.moneygowhere.logic.commands.AddCommand;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.ListCommand;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.storage.JsonSpendingBookStorage;
import seedu.moneygowhere.storage.JsonUserPrefsStorage;
import seedu.moneygowhere.storage.StorageManager;
import seedu.moneygowhere.testutil.SpendingBuilder;

public class LogicManagerTest {

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonSpendingBookStorage spendingBookStorage =
                new JsonSpendingBookStorage(temporaryFolder.resolve("moneygowhere.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(spendingBookStorage, userPrefsStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_SPENDING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonSpendingBookStorage spendingBookStorage =
                new JsonSpendingBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(spendingBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DATE_DESC_AMY + REMARK_DESC_AMY
                + COST_DESC_AMY;
        Spending expectedSpending = new SpendingBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addSpending(expectedSpending);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredSpendingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredSpendingList().remove(0));
    }

    @Test
    public void getPrevCommand_emptyStorage_success() {
        assertEquals("", logic.getPrevCommand());
    }

    @Test
    public void getPrevCommand_withInput_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        logic.execute(listCommand);
        assertEquals(listCommand, logic.getPrevCommand());
    }

    @Test
    public void getNextCommand_emptyStorage_success() {
        assertEquals("", logic.getPrevCommand());
    }

    @Test
    public void getNextCommand_withInput_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        logic.execute(listCommand);
        assertEquals("", logic.getNextCommand());
        logic.getPrevCommand();
        logic.getPrevCommand();
        assertEquals(listCommand, logic.getNextCommand());
    }

    @Test
    public void getStatsData_success() {
        model.addSpending(APPLE);
        model.addSpending(BANANA);
        model.addSpending(DESSERT);
        Date startDate = APPLE.getDate();
        Date endDate = DESSERT.getDate();
        Predicate<Spending> expectedPredicate = s-> {
            return s.getDate().value.compareTo(startDate.value) >= 0
                && s.getDate().value.compareTo(endDate.value) <= 0;
        };
        LinkedHashMap<String, Double> statsData = new LinkedHashMap<>();
        statsData.put(APPLE.getTags().iterator().next().tagName, Double.parseDouble(APPLE.getCost().toString())
            + Double.parseDouble(BANANA.getCost().toString()));
        statsData.put(DESSERT.getTags().iterator().next().tagName, Double.parseDouble(DESSERT.getCost().toString()));
        model.updateFilteredSpendingList(expectedPredicate);
        assertEquals(statsData, logic.getStatsData());
    }

    @Test
    public void getGraphData_success() {
        model.addSpending(APPLE);
        model.addSpending(APPLE);
        model.addSpending(BANANA);
        Date startDate = APPLE.getDate();
        Date endDate = BANANA.getDate();
        Predicate<Spending> expectedPredicate = s-> {
            return s.getDate().value.compareTo(startDate.value) >= 0
                && s.getDate().value.compareTo(endDate.value) <= 0;
        };
        LinkedHashMap<String, Double> graphData = new LinkedHashMap<>();
        graphData.put(APPLE.getDate().value, 2 * Double.parseDouble(APPLE.getCost().toString()));
        graphData.put(BANANA.getDate().value, Double.parseDouble(BANANA.getCost().toString()));
        model.updateFilteredSpendingList(expectedPredicate);
        assertEquals(graphData, logic.getGraphData());
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
        Model expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
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
    private static class JsonSpendingBookIoExceptionThrowingStub extends JsonSpendingBookStorage {

        private JsonSpendingBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveSpendingBook(ReadOnlySpendingBook spendingBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
