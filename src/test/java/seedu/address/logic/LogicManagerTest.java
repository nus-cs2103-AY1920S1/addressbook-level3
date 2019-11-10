package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;

import seedu.address.logic.commands.InCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserState;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.JsonUserStateStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.BankOperationBuilder;
import seedu.address.testutil.TypicalTransactions;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonUserStateStorage userStateStorage =
            new JsonUserStateStorage(temporaryFolder.resolve("userState.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(userStateStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void getUserState() {
        UserState userState = new UserState();
        assertEquals(userState, logic.getUserState());
    }

    @Test
    public void getGuiSettings() {
        assertEquals(new GuiSettings(), logic.getGuiSettings());
    }

    @Test
    public void setGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(300.00, 300.00, 10, 10);
        logic.setGuiSettings(guiSettings);
        assertEquals(guiSettings, logic.getGuiSettings());
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandInvalidIndexError_throwsCommandException() {
        String deleteCommand = "delete t9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String inCommand = InCommand.COMMAND_WORD + " $/100 n/milk c/food d/10112019";
        assertCommandSuccess(inCommand, String.format(InCommand.MESSAGE_SUCCESS, TypicalTransactions.ALICE), model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonUserStateIoExceptionThrowingStub
        JsonUserStateStorage userStateStorage =
            new JsonUserStateIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionUserState.json"));
        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(userStateStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute in command
        String inCommand = InCommand.COMMAND_WORD + " n/milk" + " d/19112019" + " $/69";
        BankAccountOperation expectedOp = new BankOperationBuilder()
            .withDescription("milk")
            .withAmount("69")
            .withDate("19112019")
            .withCategories("GENERAL")
            .build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.add(expectedOp);
        expectedModel.commitUserState();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(inCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTransactionList().remove(0));
    }

    @Test
    public void getTransactionList_modifyList_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> logic.getTransactionList().remove(0));
    }

    @Test
    public void getBudgetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getBudgetList().remove(0));
    }

    @Test
    public void getLedgerOperationsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getLedgerOperationsList().remove(0));
    }

    @Test
    public void getPeopleInLedger_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getPeopleInLedger().remove(0));
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
        Model expectedModel = new ModelManager(model.getUserState(), new UserPrefs());
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
    private static class JsonUserStateIoExceptionThrowingStub extends JsonUserStateStorage {
        private JsonUserStateIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveUserState(ReadOnlyUserState userState, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

}
