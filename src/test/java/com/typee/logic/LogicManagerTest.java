package com.typee.logic;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.commands.ListCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;
import com.typee.storage.JsonEngagementListStorage;
import com.typee.storage.JsonTypeeStorage;
import com.typee.storage.JsonUserPrefsStorage;
import com.typee.storage.StorageManager;

public class LogicManagerTest {

    //private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonEngagementListStorage engagementListStorage =
                new JsonEngagementListStorage(temporaryFolder.resolve("engagementList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonTypeeStorage typeeStorage = new JsonTypeeStorage(temporaryFolder.resolve("tabMenus.json"));
        StorageManager storage = new StorageManager(engagementListStorage, userPrefsStorage, typeeStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, Messages.MESSAGE_INVALID_ENGAGEMENT_DISPLAYED_INDEX);
    }
     */

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    /*
    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonEngagementListIoExceptionThrowingStub
        JsonEngagementListStorage engagementListStorage =
                new JsonEngagementListIoExceptionThrowingStub(temporaryFolder
                        .resolve("ioExceptionEngagementList.json")
                );
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonTypeeStorage typeeStorage = new JsonTypeeStorage(temporaryFolder.resolve("tabMenus.json"));
        StorageManager storage = new StorageManager(engagementListStorage, userPrefsStorage, typeeStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + CommandTestUtil.NAME_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addEngagement(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
     */

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredEngagementList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandBehavior(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(inputCommand, null, expectedMessage, expectedModel);
    }


    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
        assertCommandBehavior(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandBehavior(String inputCommand, Class<?> expectedException,
                                       String expectedMessage, Model expectedModel) {
        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    /*
    private static class JsonEngagementListIoExceptionThrowingStub extends JsonEngagementListStorage {
        private JsonEngagementListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveEngagementList(ReadOnlyEngagementList engagementList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
     */

}
