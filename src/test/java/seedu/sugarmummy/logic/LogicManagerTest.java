package seedu.sugarmummy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.logic.commands.records.ListCommand;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ModelManager;
import seedu.sugarmummy.model.UserPrefs;
import seedu.sugarmummy.model.calendar.Calendar;
import seedu.sugarmummy.model.recmf.UniqueFoodList;
import seedu.sugarmummy.model.records.UniqueRecordList;
import seedu.sugarmummy.storage.JsonUserPrefsStorage;
import seedu.sugarmummy.storage.StorageManager;
import seedu.sugarmummy.storage.biography.JsonUserListStorage;
import seedu.sugarmummy.storage.calendar.JsonCalendarStorage;
import seedu.sugarmummy.storage.recmf.JsonFoodListStorage;
import seedu.sugarmummy.storage.records.JsonRecordListStorage;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonUserListStorage userListStorage = new JsonUserListStorage(temporaryFolder.resolve("userList.json"));
        JsonFoodListStorage jsonFoodListStorage = new JsonFoodListStorage(temporaryFolder.resolve("foodList.json"));
        JsonRecordListStorage jsonRecordListStorage = new JsonRecordListStorage(
                temporaryFolder.resolve("recordList.json")
        );
        JsonCalendarStorage jsonCalendarStorage = new JsonCalendarStorage(temporaryFolder.resolve("eventlist.json"),
                temporaryFolder.resolve("reminderlist.json"));
        StorageManager storage = new StorageManager(userPrefsStorage, userListStorage,
                jsonFoodListStorage, jsonRecordListStorage, jsonCalendarStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    //TODO: rewrite test with different add command
    //    @Test
    //    public void execute_storageThrowsIoException_throwsCommandException() {
    //        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
    //        JsonAddressBookStorage addressBookStorage =
    //                new JsonAddressBookIoExceptionThrowingStub(
    //                temporaryFolder.resolve("ioExceptionAddressBook.json"));
    //        JsonUserPrefsStorage userPrefsStorage =
    //                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
    //        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
    //        logic = new LogicManager(sugarmummy.recmfood.model, storage);
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

    /**
     * Executes the command and confirms that - no exceptions are thrown <br> - the feedback message is equal to {@code
     * expectedMessage} <br> - the internal sugarmummy.recmfood.model manager state is the same as that in {@code
     * expectedModel} <br>
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
        Model expectedModel = new ModelManager(new UserPrefs(), model.getUserList(),
                new UniqueFoodList(), new UniqueRecordList(), new Calendar());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that - the {@code expectedException} is thrown <br> - the resulting error
     * message is equal to {@code expectedMessage} <br> - the internal sugarmummy.recmfood.model manager state is the
     * same as that in {@code expectedModel} <br>
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
    /*
    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
    */
}
