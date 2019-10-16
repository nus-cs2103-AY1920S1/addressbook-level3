package seedu.address.person.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.person.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.person.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.person.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.person.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.person.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.person.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.inventory.util.InventoryList;
import seedu.address.person.logic.commands.AddCommand;
import seedu.address.person.logic.commands.CommandResult;
import seedu.address.person.logic.commands.ListCommand;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.logic.parser.exceptions.ParseException;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.ReadOnlyAddressBook;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.person.storage.*;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.testutil.PersonBuilder;
import seedu.address.transaction.util.TransactionList;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");
    private static final String FILE_PATH_REIMBURSEMENT = "data/reimbursementInformation.txt";
    private static final String FILE_PATH_TRANSACTION = "data/transactionHistory.txt";
    private static final String FILE_PATH_INVENTORY = "data/inventoryInformation.txt";

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));

        TransactionList transactionList = new TransactionList();
        ReimbursementList reimbursementList = new ReimbursementList();
        InventoryList inventoryList = new InventoryList();
        seedu.address.cashier.util.InventoryList cashierList = new seedu.address.cashier.util.InventoryList();

        //For Person Storage and Manager
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();
        seedu.address.person.storage.StorageManager personManager=
                new seedu.address.person.storage.StorageManager(addressBookStorage, userPrefsStorage);

        //For Transaction Storage and Manager
        seedu.address.transaction.model.Model transactionModel = new seedu.address.transaction.model.ModelManager(transactionList);
        seedu.address.transaction.storage.StorageManager transactionManager =
                new seedu.address.transaction.storage.StorageManager(FILE_PATH_TRANSACTION, personModel);

        //For Reimbursement Storage and Manager
        seedu.address.reimbursement.model.Model reimbursementModel=
                new seedu.address.reimbursement.model.ModelManager(reimbursementList);
        seedu.address.reimbursement.storage.StorageManager reimbursementManager =
                new seedu.address.reimbursement.storage.StorageManager(
                        FILE_PATH_REIMBURSEMENT, transactionModel);

        //For Cashier Storage and Manager
        seedu.address.cashier.model.ModelManager cashierModel =
                new seedu.address.cashier.model.ModelManager(cashierList);
        seedu.address.cashier.storage.StorageManager cashierManager =
                new seedu.address.cashier.storage.StorageManager(FILE_PATH_INVENTORY,
                        FILE_PATH_TRANSACTION, personModel);

        //For Inventory Storage and Manager
        seedu.address.inventory.model.ModelManager inventoryModel =
                new seedu.address.inventory.model.ModelManager(inventoryList);
        seedu.address.inventory.storage.StorageManager inventoryManager =
                new seedu.address.inventory.storage.StorageManager(FILE_PATH_INVENTORY);

        //All related logics
        seedu.address.transaction.logic.Logic transactionLogic = new seedu.address.transaction.logic.LogicManager(transactionModel, transactionManager, personModel, personManager,
                reimbursementModel, reimbursementManager);
        seedu.address.reimbursement.logic.Logic reimbursementLogic =
                new seedu.address.reimbursement.logic.LogicManager(reimbursementModel, reimbursementManager,
                        transactionModel, transactionManager, personModel);
        seedu.address.cashier.logic.Logic cashierLogic =
                new seedu.address.cashier.logic.LogicManager(cashierModel, cashierManager, personModel,
                        personManager, reimbursementModel, reimbursementManager, transactionModel,
                        transactionManager, inventoryModel, inventoryManager);
        seedu.address.inventory.logic.Logic inventoryLogic =
                new seedu.address.inventory.logic.LogicManager(cashierModel, cashierManager, inventoryModel,
                        inventoryManager);
        logic = new LogicManager(model, personManager, transactionLogic, reimbursementLogic,
                cashierLogic, inventoryLogic);
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

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
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
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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
