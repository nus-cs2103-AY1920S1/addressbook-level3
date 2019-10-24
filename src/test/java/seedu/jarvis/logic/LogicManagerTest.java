package seedu.jarvis.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.jarvis.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jarvis.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.jarvis.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.jarvis.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.jarvis.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.address.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.address.ListAddressCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.storage.StorageManager;
import seedu.jarvis.storage.address.JsonAddressBookStorage;
import seedu.jarvis.storage.cca.JsonCcaTrackerStorage;
import seedu.jarvis.storage.history.JsonHistoryManagerStorage;
import seedu.jarvis.storage.userprefs.JsonUserPrefsStorage;
import seedu.jarvis.testutil.address.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonHistoryManagerStorage historyManagerStorage = new JsonHistoryManagerStorage(
                temporaryFolder.resolve("historymanager.json"));
        JsonCcaTrackerStorage ccaTrackerStorage = new JsonCcaTrackerStorage(
                temporaryFolder.resolve("ccatracker.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, historyManagerStorage,
                ccaTrackerStorage);
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
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests the successful execution of a command with no inverse.
     * @throws Exception If there was a {@code CommandException} or {@code ParseException} thrown during the execution.
     */
    @Test
    public void execute_validCommandWithoutInverse_success() throws Exception {
        String listCommand = ListAddressCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListAddressCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Tests the successful execution of an invertible command.
     * @throws Exception If there was a {@code CommandException} or {@code ParseException} thrown during the execution.
     */
    @Test
    public void execute_validCommandWithInverse_success() throws Exception {
        String addCommand = AddAddressCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = String.format(AddAddressCommand.MESSAGE_SUCCESS, expectedPerson);
        assertCommandSuccess(addCommand, expectedMessage, expectedModel);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonHistoryManagerStorage historyManagerStorage = new JsonHistoryManagerStorage(
                temporaryFolder.resolve("ioExceptionHistoryManager.json"));
        JsonCcaTrackerStorage ccaTrackerStorage = new JsonCcaTrackerStorage(
                temporaryFolder.resolve("ioExceptionCcaTracker.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, historyManagerStorage,
                ccaTrackerStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddAddressCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        expectedModel.rememberExecutedCommand(new AddAddressCommand(expectedPerson));
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
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
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
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
    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
