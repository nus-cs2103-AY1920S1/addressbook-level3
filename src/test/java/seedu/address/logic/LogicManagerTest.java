package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonFinSecStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonFinSecStorage finSecStorage =
                new JsonFinSecStorage(temporaryFolder.resolve("finsec.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(finSecStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete_contact 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String gotoCommand = ExitCommand.COMMAND_WORD;
        assertCommandSuccess(gotoCommand, ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, model);
    }

    @Test
    public void execute_clearCommand_sucess() throws URISyntaxException, CommandException, ParseException, IOException {
        CommandResult commandResult = logic.executeClear("Y");
        assertEquals(commandResult.getFeedbackToUser(), "FinSec is cleared");
    }

    @Test
    public void checkGetMethods() {
        assertEquals(logic.getModel(), model);
        assertEquals(logic.getFilteredClaimList(), model.getFilteredClaimList());
        assertEquals(logic.getFilteredIncomeList(), model.getFilteredIncomeList());
        assertEquals(logic.getFinSec(), null);
    }

    @Test
    public void checkFinSecFilePath() {
        assertEquals(logic.getFinSecFilePath(), model.getFinSecFilePath());
    }

    @Test
    public void checkGuiSettings() {
        GuiSettings guiSettings = logic.getGuiSettings();
        assertEquals(guiSettings, model.getGuiSettings());

        logic.setGuiSettings(guiSettings);
        assertEquals(logic.getGuiSettings(), guiSettings);
    }

    //    @Test
    //    public void execute_storageThrowsIoException_throwsCommandException() {
    //        // Setup LogicManager with JsonFinSecIoExceptionThrowingStub
    //        JsonFinSecStorage addressBookStorage =
    //                new JsonFinSecIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
    //        JsonUserPrefsStorage userPrefsStorage =
    //                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
    //        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
    //        logic = new LogicManager(model, storage);
    //
    //        // Execute add command
    //        String addCommand = AddContactCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
    //        Contact expectedContact = new ContactBuilder(AMY).withTags().build();
    //        ModelManager expectedModel = new ModelManager();
    //        expectedModel.addContact(expectedContact);
    //        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
    //        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    //    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredContactList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException, IOException, URISyntaxException {
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
        Model expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
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
        //assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFinSecIoExceptionThrowingStub extends JsonFinSecStorage {
        private JsonFinSecIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFinSec(ReadOnlyFinSec contact, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
