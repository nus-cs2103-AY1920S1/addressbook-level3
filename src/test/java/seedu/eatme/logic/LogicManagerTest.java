package seedu.eatme.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX;
import static seedu.eatme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_CATEGORY_WITH_PREFIX;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_WITH_PREFIX_MAC;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalEateries.MCDONALD;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eatme.logic.commands.AddCommand;
import seedu.eatme.logic.commands.CommandResult;
import seedu.eatme.logic.commands.ListCommand;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.storage.JsonEateryListStorage;
import seedu.eatme.storage.JsonFeedListStorage;
import seedu.eatme.storage.JsonUserPrefsStorage;
import seedu.eatme.storage.StorageManager;
import seedu.eatme.testutil.EateryBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonEateryListStorage eateryListStorage =
                new JsonEateryListStorage(temporaryFolder.resolve("eatMe.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonFeedListStorage feedListStorage =
                new JsonFeedListStorage(temporaryFolder.resolve("feedList.json"));
        StorageManager storage = new StorageManager(eateryListStorage, feedListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 7";
        assertCommandException(deleteCommand, MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonEateryListIoExceptionThrowingStub
        JsonEateryListStorage eateryListStorage =
                new JsonEateryListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionEatMe.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonFeedListStorage feedListStorage =
                new JsonFeedListStorage(temporaryFolder.resolve("ioExceptionFeedList.json"));
        StorageManager storage = new StorageManager(eateryListStorage, feedListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + VALID_NAME_WITH_PREFIX_MAC
            + VALID_ADDRESS_WITH_PREFIX_MAC + VALID_CATEGORY_WITH_PREFIX;
        Eatery expectedEatery = new EateryBuilder(MCDONALD).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addEatery(expectedEatery);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredEateryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredEateryList().remove(0));
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
        Model expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
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
    private static class JsonEateryListIoExceptionThrowingStub extends JsonEateryListStorage {
        private JsonEateryListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveEateryList(ReadOnlyEateryList eateryList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFeedListIoExceptionThrowingStub extends JsonFeedListStorage {
        private JsonFeedListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFeedList(ReadOnlyFeedList feedList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
