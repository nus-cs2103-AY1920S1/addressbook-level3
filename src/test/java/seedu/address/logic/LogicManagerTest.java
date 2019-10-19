package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.statistic.Statistic;
import seedu.address.statistic.StatisticManager;
import seedu.address.storage.JsonCustomerBookStorage;
import seedu.address.storage.JsonOrderBookStorage;
import seedu.address.storage.JsonPhoneBookStorage;
import seedu.address.storage.JsonScheduleBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Statistic statistic = new StatisticManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCustomerBookStorage customerBookStorage =
                new JsonCustomerBookStorage(temporaryFolder.resolve("customerBook.json"));
        JsonPhoneBookStorage phoneBookStorage =
                new JsonPhoneBookStorage(temporaryFolder.resolve("phoneBook.json"));
        JsonScheduleBookStorage scheduleBookStorage =
                new JsonScheduleBookStorage(temporaryFolder.resolve("scheduleBook.json"));
        JsonOrderBookStorage orderBookStorage =
                new JsonOrderBookStorage(temporaryFolder.resolve("orderBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(customerBookStorage, phoneBookStorage,
                scheduleBookStorage, orderBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage, statistic);
        logic = new LogicManager(model, storage, new StatisticManager());
    }

//    @Test
//    public void execute_invalidCommandFormat_throwsParseException() {
//        String invalidCommand = "uicfhmowqewca";
//        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    @Test
//    public void execute_commandExecutionError_throwsCommandException() {
//        String deleteCommand = "delete 9";
//        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_storageThrowsIoException_throwsCommandException() {
//        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
//        JsonAddressBookStorage addressBookStorage =
//                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
//        JsonUserPrefsStorage userPrefsStorage =
//                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
//        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
//        logic = new LogicManager(model, storage);
//
//        // Execute add command
//        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
//                + ADDRESS_DESC_AMY;
//        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
//        ModelManager expectedModel = new ModelManager();
//        expectedModel.addPerson(expectedPerson);
//        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
//        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
//    }

//    /**
//     * Executes the command and confirms that
//     * - no exceptions are thrown <br>
//     * - the feedback message is equal to {@code expectedMessage} <br>
//     * - the internal model manager state is the same as that in {@code expectedModel} <br>
//     * @see #assertCommandFailure(String, Class, String, Model)
//     */
//    private void assertCommandSuccess(String inputCommand, String expectedMessage,
//            Model expectedModel) throws CommandException, ParseException {
//        CommandResult result = logic.execute(inputCommand);
//        assertEquals(expectedMessage, result.getFeedbackToUser());
//        assertEquals(expectedModel, model);
//    }

//    /**
//     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
//     * @see #assertCommandFailure(String, Class, String, Model)
//     */
//    private void assertParseException(String inputCommand, String expectedMessage) {
//        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
//    }
//
//    /**
//     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
//     * @see #assertCommandFailure(String, Class, String, Model)
//     */
//    private void assertCommandException(String inputCommand, String expectedMessage) {
//        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
//    }
//
//    /**
//     * Executes the command, confirms that the exception is thrown and that the result message is correct.
//     * @see #assertCommandFailure(String, Class, String, Model)
//     */
//    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
//            String expectedMessage) {
//        Model expectedModel = new ModelManager(new UserPrefs());
//        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
//    }
//
//    /**
//     * Executes the command and confirms that
//     * - the {@code expectedException} is thrown <br>
//     * - the resulting error message is equal to {@code expectedMessage} <br>
//     * - the internal model manager state is the same as that in {@code expectedModel} <br>
//     * @see #assertCommandSuccess(String, String, Model)
//     */
//    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
//            String expectedMessage, Model expectedModel) {
//        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
//        assertEquals(expectedModel, model);
//    }

}
