package seedu.scheduler.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.scheduler.logic.commands.CommandTestUtil.DEPARTMENT_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.EMAIL_NUS_WORK_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.SLOT_DESC_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_ROLE_AMY_INTVR;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.AMY_INTERVIEWER_MANUAL;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.commands.CommandResult;
import seedu.scheduler.logic.commands.DeleteCommand;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.ReadOnlyList;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.storage.JsonIntervieweeListStorage;
import seedu.scheduler.storage.JsonInterviewerListStorage;
import seedu.scheduler.storage.JsonUserPrefsStorage;
import seedu.scheduler.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonIntervieweeListStorage intervieweeListStorage =
                new JsonIntervieweeListStorage(temporaryFolder.resolve("interviewee.json"));
        JsonInterviewerListStorage interviewerListStorage =
                new JsonInterviewerListStorage(temporaryFolder.resolve("interviewer.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(intervieweeListStorage, interviewerListStorage, userPrefsStorage);
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
        assertParseException(deleteCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        JsonIntervieweeListStorage intervieweeListStorage =
                new JsonIntervieweeListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionInterviewee.json"));
        JsonInterviewerListStorage interviewerListStorage =
                new JsonInterviewerListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionInterviewer.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(intervieweeListStorage, interviewerListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + " " + VALID_ROLE_AMY_INTVR + NAME_DESC_AMY + PHONE_DESC_AMY
                + TAG_DESC_FRIEND + EMAIL_NUS_WORK_DESC_AMY + DEPARTMENT_DESC_AMY + SLOT_DESC_AMY;
        ModelManager expectedModel = new ModelManager();
        expectedModel.addInterviewer(AMY_INTERVIEWER_MANUAL);
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
        Model expectedModel = new ModelManager(model.getMutableIntervieweeList(), model.getMutableInterviewerList(),
                new UserPrefs(), new LinkedList<>());
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
     * A stub class to throw an {@code IOException} when the save method is called for IntervieweeList.
     */
    public static class JsonIntervieweeListIoExceptionThrowingStub extends JsonIntervieweeListStorage {
        private JsonIntervieweeListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called for InterviewerList.
     */
    public static class JsonInterviewerListIoExceptionThrowingStub extends JsonInterviewerListStorage {
        private JsonInterviewerListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
