package seedu.revision.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX;
import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.revision.logic.commands.CommandTestUtil.CATEGORY_DESC_UML;
import static seedu.revision.logic.commands.CommandTestUtil.CORRECT_ANSWER_DESC_BROWNFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.DIFFICULTY_DESC_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.MCQ_WRONG_ANSWER_DESC;
import static seedu.revision.logic.commands.CommandTestUtil.QUESTION_DESC_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.QUESTION_TYPE_MCQ;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalMcqs.MCQ_A;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.History;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.storage.JsonHistoryStorage;
import seedu.revision.storage.JsonRevisionToolStorage;
import seedu.revision.storage.JsonUserPrefsStorage;
import seedu.revision.storage.Storage;
import seedu.revision.storage.StorageManager;
import seedu.revision.stubs.StorageStub;
import seedu.revision.testutil.McqBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;
    private Storage storageStub = new StorageStub();

    @BeforeEach
    public void setUp() {
        logic = new LogicManager(model, storageStub);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }

    /*
    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }
    */

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonRevisionToolIoExceptionThrowingStub
        JsonRevisionToolStorage addressBookStorage =
                new JsonRevisionToolIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonHistoryStorage historyStorage =
                new JsonHistoryStorage((temporaryFolder.resolve("ioExceptionHistory.json")));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, historyStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + QUESTION_TYPE_MCQ + QUESTION_DESC_ALPHA
                + CORRECT_ANSWER_DESC_BROWNFIELD + CATEGORY_DESC_UML + MCQ_WRONG_ANSWER_DESC + DIFFICULTY_DESC_ALPHA;
        Answerable expectedAnswerable = new McqBuilder(MCQ_A).withCategories("UML").build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addAnswerable(expectedAnswerable);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredAnswerableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredAnswerableList().remove(0));
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
        Model expectedModel = new ModelManager(model.getRevisionTool(), new UserPrefs(), new History());
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
    private static class JsonRevisionToolIoExceptionThrowingStub extends JsonRevisionToolStorage {
        private JsonRevisionToolIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRevisionTool(ReadOnlyRevisionTool revisionTool, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

}
