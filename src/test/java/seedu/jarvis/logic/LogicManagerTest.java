package seedu.jarvis.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX;
import static seedu.jarvis.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jarvis.logic.commands.CommandTestUtil.CCA_DESC;
import static seedu.jarvis.logic.commands.CommandTestUtil.CCA_TYPE;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_CCA_DESC_TRACK;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_CCA_TYPE_TRACK;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.cca.AddCcaCommand;
import seedu.jarvis.logic.commands.cca.ListCcaCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.storage.StorageManager;
import seedu.jarvis.storage.cca.JsonCcaTrackerStorage;
import seedu.jarvis.storage.course.JsonCoursePlannerStorage;
import seedu.jarvis.storage.finance.JsonFinanceTrackerStorage;
import seedu.jarvis.storage.history.JsonHistoryManagerStorage;
import seedu.jarvis.storage.planner.JsonPlannerStorage;
import seedu.jarvis.storage.userprefs.JsonUserPrefsStorage;
import seedu.jarvis.testutil.cca.CcaBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonHistoryManagerStorage historyManagerStorage = new JsonHistoryManagerStorage(
                temporaryFolder.resolve("historymanager.json"));
        JsonCcaTrackerStorage ccaTrackerStorage = new JsonCcaTrackerStorage(
                temporaryFolder.resolve("ccatracker.json"));
        JsonCoursePlannerStorage coursePlannerStorage = new JsonCoursePlannerStorage(
                temporaryFolder.resolve("courseplanner.json"));
        JsonPlannerStorage plannerStorage = new JsonPlannerStorage(temporaryFolder.resolve("planner.json"));
        JsonFinanceTrackerStorage financeTrackerStorage = new JsonFinanceTrackerStorage(
                temporaryFolder.resolve("financetracker.json"));
        StorageManager storage = new StorageManager(userPrefsStorage, historyManagerStorage, ccaTrackerStorage,
                coursePlannerStorage, plannerStorage, financeTrackerStorage);
        model = new ModelManager();
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete-cca 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
    }

    /**
     * Tests the successful execution of a command with no inverse.
     * @throws Exception If there was a {@code CommandException} or {@code ParseException} thrown during the execution.
     */
    @Test
    public void execute_validCommandWithoutInverse_success() throws Exception {
        String listCommand = ListCcaCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCcaCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Tests the successful execution of an invertible command.
     * @throws Exception If there was a {@code CommandException} or {@code ParseException} thrown during the execution.
     */
    @Test
    public void execute_validCommandWithInverse_success() throws Exception {
        String addCommand = AddCcaCommand.COMMAND_WORD + CCA_DESC + CCA_TYPE;
        Cca cca = new CcaBuilder()
                .withName(VALID_CCA_DESC_TRACK)
                .withType(VALID_CCA_TYPE_TRACK)
                .withEquipmentList()
                .build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addCca(cca);
        String expectedMessage = String.format(AddCcaCommand.MESSAGE_SUCCESS, cca);
        assertCommandSuccess(addCommand, expectedMessage, expectedModel);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonHistoryManagerStorage historyManagerStorage = new JsonHistoryManagerStorage(
                temporaryFolder.resolve("ioExceptionHistoryManager.json"));
        JsonCcaTrackerStorage ccaTrackerStorage = new JsonCcaTrackerIoExceptionThrowingStub(
                temporaryFolder.resolve("ioExceptionCcaTracker.json"));
        JsonCoursePlannerStorage coursePlannerStorage = new JsonCoursePlannerStorage(
                temporaryFolder.resolve("ioExceptionCoursePlanner.json"));
        JsonPlannerStorage plannerStorage = new JsonPlannerStorage(temporaryFolder.resolve("ioExceptionPlanner.json"));
        JsonFinanceTrackerStorage financeTrackerStorage = new JsonFinanceTrackerStorage(
                temporaryFolder.resolve("ioExceptionFinanceTracker.json"));
        StorageManager storage = new StorageManager(userPrefsStorage, historyManagerStorage,
                ccaTrackerStorage, coursePlannerStorage, plannerStorage, financeTrackerStorage);
        logic = new LogicManager(model, storage);

        String addCommand = AddCcaCommand.COMMAND_WORD + CCA_DESC + CCA_TYPE;
        Cca cca = new CcaBuilder()
                .withName(VALID_CCA_DESC_TRACK)
                .withType(VALID_CCA_TYPE_TRACK)
                .withEquipmentList()
                .build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addCca(cca);
        expectedModel.rememberExecutedCommand(new AddCcaCommand(cca));
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
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

        // syncs history manager, which would have remembered the successfully executed command if it has an inverse.
        expectedModel.setHistoryManager(model.getHistoryManager());

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
        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
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
    private static class JsonCcaTrackerIoExceptionThrowingStub extends JsonCcaTrackerStorage {
        private JsonCcaTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCcaTracker(CcaTracker ccaTracker, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
