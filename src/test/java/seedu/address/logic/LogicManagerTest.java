package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_NUMBER_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.model.util.SampleDataUtil.getSampleArchivedOrderBook;
import static seedu.address.model.util.SampleDataUtil.getSampleCustomerBook;
import static seedu.address.model.util.SampleDataUtil.getSampleOrderBook;
import static seedu.address.model.util.SampleDataUtil.getSamplePhoneBook;
import static seedu.address.model.util.SampleDataUtil.getSampleScheduleBook;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.addcommand.AddCustomerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.listcommand.ListCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.statistic.Statistic;
import seedu.address.statistic.StatisticManager;
import seedu.address.storage.JsonCustomerBookStorage;
import seedu.address.storage.JsonOrderBookStorage;
import seedu.address.storage.JsonPhoneBookStorage;
import seedu.address.storage.JsonScheduleBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.CustomerBuilder;

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

        JsonOrderBookStorage archivedOrderBookStorage =
                new JsonOrderBookStorage(temporaryFolder.resolve("archivedOrderBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));

        StorageManager storage = new StorageManager(customerBookStorage, phoneBookStorage,
                scheduleBookStorage, orderBookStorage, archivedOrderBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage, statistic);
        logic = new LogicManager(model, storage, new StatisticManager());
    }


    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete-c 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws CommandException, ParseException {
        String listCommand = ListCustomerCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCustomerCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredCustomerList().remove(0));
    }

    @Test
    public void getFilteredPhoneList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPhoneList().remove(0));
    }

    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredOrderList().remove(0));
    }

    @Test
    public void getFilteredScheduleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredScheduleList().remove(0));
    }

    @Test
    public void getFilteredArchivedOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredArchivedOrderList().remove(0));
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        JsonCustomerBookStorage customerBookStorage =
                new JsonCustomerBookIoExceptionThrowingStub(temporaryFolder);
        JsonPhoneBookStorage phoneBookStorage =
                new JsonPhoneBookStorage(temporaryFolder.resolve("phoneBook.json"));
        JsonScheduleBookStorage scheduleBookStorage =
                new JsonScheduleBookStorage(temporaryFolder.resolve("scheduleBook.json"));
        JsonOrderBookStorage orderBookStorage =
                new JsonOrderBookStorage(temporaryFolder.resolve("orderBook.json"));
        JsonOrderBookStorage archivedOrderBookStorage =
                new JsonOrderBookStorage(temporaryFolder.resolve("archivedOrderBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));

        StorageManager storage = new StorageManager(customerBookStorage, phoneBookStorage,
                scheduleBookStorage, orderBookStorage, archivedOrderBookStorage, userPrefsStorage);

        model = new ModelManager(getSampleCustomerBook(), getSamplePhoneBook(), getSampleOrderBook(),
                getSampleScheduleBook(), getSampleArchivedOrderBook(), new UserPrefs());

        String addCommand = AddCustomerCommand.COMMAND_WORD + NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_ALICE;
        Customer alice = new CustomerBuilder().withName(VALID_NAME_ALICE).withContactNumber(VALID_CONTACT_NUMBER_ALICE)
                .withEmail(VALID_EMAIL_ALICE).build();
        Model expectedModel = new ModelManager(getSampleCustomerBook(), getSamplePhoneBook(), getSampleOrderBook(),
                getSampleScheduleBook(), getSampleArchivedOrderBook(), new UserPrefs());

        expectedModel.addCustomer(alice);
        String expectedMessage = String.format(AddCustomerCommand.MESSAGE_SUCCESS, alice);
        assertCommandSuccess(addCommand, expectedMessage, expectedModel);

        String errorMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;

        assertThrows(IOException.class, () -> customerBookStorage.saveCustomerBook(model.getCustomerBook()));

    }

    @Test
    public void setCustomerBook_validCustomerBook_success() {
        ReadOnlyDataBook<Customer> book = new DataBook<Customer>();
        model.setCustomerBook(book);
        assertEquals(book, logic.getOrderBook());
    }

    @Test
    public void setPhoneBook_validPhoneBook_success() {
        ReadOnlyDataBook<Phone> book = new DataBook<Phone>();
        model.setPhoneBook(book);
        assertEquals(book, logic.getPhoneBook());
    }

    @Test
    public void setOrderBook_validOrderBook_success() {
        ReadOnlyDataBook<Order> book = new DataBook<Order>();
        model.setOrderBook(book);
        assertEquals(book, logic.getOrderBook());
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
        Model expectedModel = new ModelManager(new UserPrefs());
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
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonCustomerBookIoExceptionThrowingStub extends JsonCustomerBookStorage {

        private JsonCustomerBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCustomerBook(ReadOnlyDataBook<Customer> customerBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

}
