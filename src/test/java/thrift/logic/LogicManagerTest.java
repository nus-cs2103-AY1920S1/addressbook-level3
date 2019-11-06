package thrift.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static thrift.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static thrift.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.CommandResult;
import thrift.logic.commands.CommandTestUtil;
import thrift.logic.commands.HelpCommand;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.ReadOnlyThrift;
import thrift.model.UserPrefs;
import thrift.storage.JsonCurrencyMappingsStorage;
import thrift.storage.JsonThriftStorage;
import thrift.storage.JsonUserPrefsStorage;
import thrift.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonThriftStorage thriftStorage =
                new JsonThriftStorage(temporaryFolder.resolve("thrift.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonCurrencyMappingsStorage currencyMappingsStorage =
                new JsonCurrencyMappingsStorage(temporaryFolder.resolve("currency.json"));
        StorageManager storage = new StorageManager(thriftStorage, userPrefsStorage, currencyMappingsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete i/9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validNonScrollingCommand_success() throws Exception {
        String helpCommand = HelpCommand.COMMAND_WORD;
        assertCommandSuccess(helpCommand, HelpCommand.SHOWING_HELP_MESSAGE, model);
    }

    @Test
    public void execute_validScrollingCommand_success() throws Exception {
        String addExpenseCommand = AddExpenseCommand.COMMAND_WORD + CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.VALUE_LAKSA + CommandTestUtil.REMARK_LAKSA + CommandTestUtil.TAG_BURSARY
                + CommandTestUtil.TAG_LAKSA;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = dtf.format(LocalDateTime.now());

        String expectedMessage = "New expense added: [-] " + CommandTestUtil.VALID_DESCRIPTION_LAKSA + " ($"
                + CommandTestUtil.VALID_VALUE_LAKSA + ") Date: " + currentDate + " Remarks: "
                + CommandTestUtil.VALID_REMARK_LAKSA + " Tags: [" + CommandTestUtil.VALID_TAG_AWARD + "]["
                + CommandTestUtil.VALID_TAG_LUNCH + "]";

        assertCommandSuccess(addExpenseCommand, expectedMessage, model);
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTransactionList().remove(0));
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
        CommandResult result = logic.execute(inputCommand, null, null, null);
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
        Model expectedModel = new ModelManager(model.getThrift(), new UserPrefs());
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
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand, null,
                null, null));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonThriftIoExceptionThrowingStub extends JsonThriftStorage {
        private JsonThriftIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveThrift(ReadOnlyThrift thrift, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
