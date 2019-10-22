package mams.logic;

import static mams.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static mams.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mams.logic.commands.CommandResult;
import mams.logic.commands.ListCommand;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.parser.exceptions.ParseException;
import mams.model.Model;
import mams.model.ModelManager;
import mams.model.ReadOnlyMams;
import mams.model.UserPrefs;
import mams.storage.JsonMamsStorage;
import mams.storage.JsonUserPrefsStorage;
import mams.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonMamsStorage mamsStorage =
                new JsonMamsStorage(temporaryFolder.resolve("mams.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(mamsStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistorySuccess(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        // delete command removed. New tests to be implemented later.
    }

    @Test //TODO YongKuan clean up this test to make it less hard-codey
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, String.format(ListCommand.MESSAGE_SUCCESS,
                " " + ListCommand.APPEALS
                + " " + ListCommand.MODULES
                + " " + ListCommand.STUDENTS), model);

        assertHistorySuccess(ListCommand.COMMAND_WORD);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonMamsIoExceptionThrowingStub
        JsonMamsStorage mamsStorage =
                new JsonMamsIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionMams.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(mamsStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command removed, to be implemented with new test later.
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredStudentList().remove(0));
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
        Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());
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
     * Asserts that the {@code CommandHistory} object within {@code LogicManager} is storing the correct sequence
     * of command inputs.
     * @param sequenceOfExpectedCommands
     */
    private void assertHistorySuccess(String... sequenceOfExpectedCommands) {
        ObservableList<String> expected = FXCollections.unmodifiableObservableList(
                FXCollections.observableList(new ArrayList<String>(Arrays.asList(sequenceOfExpectedCommands))));
        assertEquals(expected, logic.getCommandHistory());
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonMamsIoExceptionThrowingStub extends JsonMamsStorage {
        private JsonMamsIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMams(ReadOnlyMams mams, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
