//package seedu.address.logic;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
//import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalPersons.AMY;
//
//import java.io.IOException;
//import java.nio.file.Path;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import seedu.address.model.password.exceptions.DictionaryException;
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.logic.commands.CommandResult;
//import seedu.address.logic.commands.ListCommand;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.ReadOnlyCardBook;
//import seedu.address.model.ReadOnlyNoteBook;
//import seedu.address.model.ReadOnlyPasswordBook;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Person;
//import seedu.address.storage.JsonAddressBookStorage;
//import seedu.address.storage.JsonCardBookStorage;
//import seedu.address.storage.JsonFileBookStorage;
//import seedu.address.storage.JsonNoteBookStorage;
//import seedu.address.storage.JsonPasswordBookStorage;
//import seedu.address.storage.JsonUserPrefsStorage;
//import seedu.address.storage.StorageManager;
//import seedu.address.testutil.PersonBuilder;
//
//public class LogicManagerTest {
//    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");
//    private static final String PASSWORD = "password1";
//
//    @TempDir
//    public Path temporaryFolder;
//
//    private Model model = new ModelManager();
//    private Logic logic;
//
//    @BeforeEach
//    public void setUp() {
//        //TODO: add in new tests with password book storage
//        JsonPasswordBookStorage passwordBookStorage =
//                new JsonPasswordBookStorage(temporaryFolder.resolve("passwordBook.json"), PASSWORD);
//        JsonAddressBookStorage addressBookStorage =
//                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"), PASSWORD);
//        JsonFileBookStorage fileBookStorage =
//                new JsonFileBookStorage(temporaryFolder.resolve("fileBook.json"), PASSWORD);
//        JsonCardBookStorage cardBookStorage =
//                new JsonCardBookStorage(temporaryFolder.resolve("cardBook.json"), PASSWORD);
//        JsonNoteBookStorage noteBookStorage =
//                new JsonNoteBookStorage(temporaryFolder.resolve("noteBook.json"), PASSWORD);
//        JsonUserPrefsStorage userPrefsStorage =
//                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"), PASSWORD);
//        StorageManager storage = new StorageManager(addressBookStorage, fileBookStorage,
//                cardBookStorage, noteBookStorage, passwordBookStorage, userPrefsStorage, PASSWORD);
//
//        logic = new LogicManager(model, storage);
//    }
//
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
//    public void execute_validCommand_success() throws Exception {
//        String listCommand = ListCommand.COMMAND_WORD;
//        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
//    }
//
//    @Test
//    public void execute_storageThrowsIoException_throwsCommandException() {
//        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
//        JsonAddressBookStorage addressBookStorage =
//                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
//        JsonFileBookStorage fileBookStorage =
//                new JsonFileBookStorage(temporaryFolder.resolve("ioExceptionFileBook.json"));
//        JsonCardBookStorage cardBookStorage =
//                new JsonCardBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionCardBook.json"));
//        JsonUserPrefsStorage userPrefsStorage =
//                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"), PASSWORD);
//        JsonNoteBookStorage noteBookStorage =
//                new JsonNoteBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionNoteBook.json"));
//        JsonPasswordBookStorage passwordBookStorage =
//                new JsonPasswordBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionPasswordBook.json"));
//        StorageManager storage = new StorageManager(addressBookStorage, fileBookStorage,
//                cardBookStorage, noteBookStorage, passwordBookStorage, userPrefsStorage, PASSWORD);
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
//
//    @Test
//    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
//    }
//
//    /**
//     * Executes the command and confirms that
//     * - no exceptions are thrown <br>
//     * - the feedback message is equal to {@code expectedMessage} <br>
//     * - the internal model manager state is the same as that in {@code expectedModel} <br>
//     * @see #assertCommandFailure(String, Class, String, Model)
//     */
//    private void assertCommandSuccess(String inputCommand, String expectedMessage,
//            Model expectedModel) throws CommandException, ParseException, DictionaryException {
//        CommandResult result = logic.execute(inputCommand);
//        assertEquals(expectedMessage, result.getFeedbackToUser());
//        assertEquals(expectedModel, model);
//    }
//
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
//        Model expectedModel = new ModelManager(model.getAddressBook(), model.getFileBook(),
//                model.getCardBook(), model.getNoteBook(), model.getPasswordBook(), new UserPrefs());
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
//
//    /**
//     * A stub class to throw an {@code IOException} when the save method is called.
//     */
//    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
//        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
//            super(filePath, PASSWORD);
//        }
//
//        @Override
//        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
//            throw DUMMY_IO_EXCEPTION;
//        }
//    }
//
//    /**
//     * A stub class to throw an {@code IOException} when the save method is called.
//     */
//    private static class JsonCardBookIoExceptionThrowingStub extends JsonCardBookStorage {
//        private JsonCardBookIoExceptionThrowingStub(Path filePath) {
//            super(filePath, PASSWORD);
//        }
//        @Override
//        public void saveCardBook(ReadOnlyCardBook cardBook, Path filePath) throws IOException {
//            throw DUMMY_IO_EXCEPTION;
//        }
//    }
//
//    private static class JsonPasswordBookIoExceptionThrowingStub extends JsonPasswordBookStorage {
//        private JsonPasswordBookIoExceptionThrowingStub(Path filePath) {
//            super(filePath, PASSWORD);
//        }
//
//        @Override
//        public void savePasswordBook(ReadOnlyPasswordBook passwordBook, Path filePath) throws IOException {
//            throw DUMMY_IO_EXCEPTION;
//        }
//    }
//
//    private static class JsonNoteBookIoExceptionThrowingStub extends JsonNoteBookStorage {
//        private JsonNoteBookIoExceptionThrowingStub(Path filePath) {
//            super(filePath, PASSWORD);
//        }
//
//        @Override
//        public void saveNoteBook(ReadOnlyNoteBook noteBook, Path filePath) throws IOException {
//            throw DUMMY_IO_EXCEPTION;
//        }
//    }
//}
