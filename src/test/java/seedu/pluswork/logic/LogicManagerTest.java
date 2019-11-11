package seedu.pluswork.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.pluswork.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_NAME_DESC_FINANCE;
import static seedu.pluswork.testutil.Assert.assertThrows;
import static seedu.pluswork.testutil.TypicalTasksMembers.REVIEW_BUDGET;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.task.ListTaskCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.commands.task.AddTaskCommand;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.storage.JsonProjectDashboardStorage;
import seedu.pluswork.storage.JsonUserPrefsStorage;
import seedu.pluswork.storage.JsonUserSettingsStorage;
import seedu.pluswork.storage.StorageManager;
import seedu.pluswork.testutil.TaskBuilder;

//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
//import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_FINANCE;
//import static seedu.address.testutil.TypicalTasksMembers.REVIEW_BUDGET;
//import seedu.address.model.task.Task;
//import seedu.address.testutil.TaskBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonProjectDashboardStorage projectDashboardStorage =
                new JsonProjectDashboardStorage(temporaryFolder.resolve("projectDashboard.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonUserSettingsStorage userSettingsStorage =
                new JsonUserSettingsStorage(temporaryFolder.resolve("plusworksettings.json"));
        StorageManager storage = new StorageManager(projectDashboardStorage, userPrefsStorage, userSettingsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteTaskCommand = "delete-task ti/9";
        assertCommandException(deleteTaskCommand, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListTaskCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListTaskCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonProjectDashboardIoExceptionThrowingStub
        JsonProjectDashboardStorage projectDashboardStorage =
                new JsonProjectDashboardIoExceptionThrowingStub(
                        temporaryFolder.resolve("ioExceptionProjectDashboard.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonUserSettingsStorage userSettingsStorage =
                new JsonUserSettingsStorage(temporaryFolder.resolve("ioExceptionUserSettings.json"));
        StorageManager storage = new StorageManager(projectDashboardStorage, userPrefsStorage, userSettingsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command

        String addTaskCommand = AddTaskCommand.COMMAND_WORD + TASK_NAME_DESC_FINANCE;
        Task expectedTask = new TaskBuilder(REVIEW_BUDGET).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTask(expectedTask);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addTaskCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTaskList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) throws
            CommandException, ParseException, FileNotFoundException {
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
        Model expectedModel = new ModelManager(model.getProjectDashboard(), new UserPrefs(), new UserSettings());
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
    private static class JsonProjectDashboardIoExceptionThrowingStub extends JsonProjectDashboardStorage {
        private JsonProjectDashboardIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
