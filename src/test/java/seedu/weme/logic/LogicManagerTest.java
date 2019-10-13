package seedu.weme.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX;
import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.MemeListCommand;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.UserPrefs;
import seedu.weme.storage.JsonMemeBookStorage;
import seedu.weme.storage.JsonUserPrefsStorage;
import seedu.weme.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonMemeBookStorage addressBookStorage =
                new JsonMemeBookStorage(temporaryFolder.resolve("weme.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = MemeListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, MemeListCommand.MESSAGE_SUCCESS, model);
    }

    /* Test does not make sense.
    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonMemeBookIoExceptionThrowingStub
        JsonMemeBookStorage addressBookStorage =
                new JsonMemeBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = MemeAddCommand.COMMAND_WORD + FILEPATH_DESC_CHARMANDER
                + DESCRIPTION_DESC_CHARMANDER;
        Meme expectedMeme = new MemeBuilder(DOGE).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addMeme(expectedMeme);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
     */

    @Test
    public void getFilteredMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredMemeList().remove(0));
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
        Model expectedModel = new ModelManager(model.getMemeBook(), new UserPrefs());
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
    private static class JsonMemeBookIoExceptionThrowingStub extends JsonMemeBookStorage {
        private JsonMemeBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMemeBook(ReadOnlyMemeBook memeBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
