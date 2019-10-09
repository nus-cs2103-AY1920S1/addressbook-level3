package seedu.tarence.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.tarence.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.tarence.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.MATRIC_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.NUSNET_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.TUTORIAL_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_AMY;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalStudents.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tarence.logic.commands.AddStudentCommand;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.ListCommand;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.JsonApplicationStorage;
import seedu.tarence.storage.JsonUserPrefsStorage;
import seedu.tarence.storage.StorageManager;
import seedu.tarence.testutil.ModuleBuilder;
import seedu.tarence.testutil.StudentBuilder;
import seedu.tarence.testutil.TutorialBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonApplicationStorage applicationStorage =
                new JsonApplicationStorage(temporaryFolder.resolve("application.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(applicationStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deleteStudent 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonApplicationIoExceptionThrowingStub
        JsonApplicationStorage applicationStorage =
                new JsonApplicationIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionApplication.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(applicationStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddStudentCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + MODULE_DESC_AMY
            + TUTORIAL_DESC_AMY + MATRIC_DESC_AMY + NUSNET_DESC_AMY;
        Student expectedStudent = new StudentBuilder(AMY).build();
        ModelManager expectedModel = new ModelManager();
        Module validModule = new ModuleBuilder().withModCode(VALID_MODULE_AMY).build();
        Tutorial validTutorial = new TutorialBuilder().withTutName(VALID_TUTORIAL_NAME_AMY)
                .withModCode(VALID_MODULE_AMY).build();
        validModule.addTutorial(validTutorial);
        expectedModel.addModule(validModule);
        model.addModule(validModule);
        expectedModel.addTutorial(validTutorial);
        model.addTutorial(validTutorial);
        expectedModel.addStudent(expectedStudent);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredModuleList().remove(0));
    }

    @Test
    public void getFilteredTutorialList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTutorialList().remove(0));
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
        assertEquals(expectedMessage + "\n", result.getFeedbackToUser());
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
        Model expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
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
    private static class JsonApplicationIoExceptionThrowingStub extends JsonApplicationStorage {
        private JsonApplicationIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveApplication(ReadOnlyApplication application, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
