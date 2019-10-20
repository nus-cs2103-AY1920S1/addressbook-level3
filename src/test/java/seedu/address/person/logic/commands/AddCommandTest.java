package seedu.address.person.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.inventory.util.InventoryList;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.person.Person;
import seedu.address.person.storage.AddressBookStorage;
import seedu.address.person.storage.JsonAddressBookStorage;
import seedu.address.person.storage.JsonUserPrefsStorage;
import seedu.address.person.storage.UserPrefsStorage;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.stubs.PersonModelStub;
import seedu.address.stubs.PersonModelStubAcceptingPersonAdded;
import seedu.address.stubs.PersonModelStubWithPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.logic.LogicManager;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.util.TransactionList;


public class AddCommandTest {
    private static final String FILE_PATH_REIMBURSEMENT = "data/reimbursementInformation.txt";
    private static final String FILE_PATH_TRANSACTION = "data/transactionHistory.txt";
    private static final String FILE_PATH_INVENTORY = "data/inventoryInformation.txt";


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        PersonModelStubAcceptingPersonAdded modelStub = new PersonModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        TransactionList transactionList = new TransactionList();
        ReimbursementList reimbursementList = new ReimbursementList();
        InventoryList inventoryList = new InventoryList();
        seedu.address.cashier.util.InventoryList cashierList = new seedu.address.cashier.util.InventoryList();

        Path userPrefPath = Paths.get("data/test/userPrefs.txt");
        Path addressPath = Paths.get("data/test/address.txt");

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressPath);

        //For Person Storage and Manager
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();
        seedu.address.person.storage.StorageManager personManager=
                new seedu.address.person.storage.StorageManager(addressBookStorage, userPrefsStorage);

        //For Transaction Storage and Manager
        seedu.address.transaction.model.Model transactionModel = new ModelManager(transactionList);
        seedu.address.transaction.storage.StorageManager transactionManager =
                new StorageManager(FILE_PATH_TRANSACTION, personModel);

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
        seedu.address.inventory.model.Model inventoryModel =
                new seedu.address.inventory.model.ModelManager(inventoryList);
        seedu.address.inventory.storage.StorageManager inventoryManager =
                new seedu.address.inventory.storage.StorageManager(FILE_PATH_INVENTORY);

        //All related logics
        Logic logic = new LogicManager(transactionModel, transactionManager, personModel, personManager,
                reimbursementModel, reimbursementManager);
        seedu.address.reimbursement.logic.Logic reimbursementLogic =
                new seedu.address.reimbursement.logic.LogicManager(reimbursementModel, reimbursementManager,
                        transactionModel, transactionManager, personModel);
        seedu.address.cashier.logic.Logic cashierLogic =
                new seedu.address.cashier.logic.LogicManager(cashierModel, cashierManager, personModel,
                        transactionModel, transactionManager, inventoryModel, inventoryManager);
        CommandResult commandResult =
                new AddCommand(validPerson).execute(modelStub, logic, reimbursementLogic, cashierLogic);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.getPersonAdded());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        PersonModelStub personModelStub = new PersonModelStubWithPerson(validPerson);

        TransactionList transactionList = new TransactionList();
        ReimbursementList reimbursementList = new ReimbursementList();
        InventoryList inventoryList = new InventoryList();
        seedu.address.cashier.util.InventoryList cashierList = new seedu.address.cashier.util.InventoryList();

        Path userPrefPath = Paths.get("data/test/userPrefs.txt");
        Path addressPath = Paths.get("data/test/address.txt");

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressPath);

        //For Person Storage and Manager
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();
        seedu.address.person.storage.StorageManager personManager=
                new seedu.address.person.storage.StorageManager(addressBookStorage, userPrefsStorage);

        //For Transaction Storage and Manager
        seedu.address.transaction.model.Model transactionModel = new ModelManager(transactionList);
        seedu.address.transaction.storage.StorageManager transactionManager =
                new StorageManager(FILE_PATH_TRANSACTION, personModel);

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
        seedu.address.inventory.model.Model inventoryModel =
                new seedu.address.inventory.model.ModelManager(inventoryList);
        seedu.address.inventory.storage.StorageManager inventoryManager =
                new seedu.address.inventory.storage.StorageManager(FILE_PATH_INVENTORY);

        //All related logics
        Logic logic = new LogicManager(transactionModel, transactionManager, personModel, personManager,
                reimbursementModel, reimbursementManager);
        seedu.address.reimbursement.logic.Logic reimbursementLogic =
                new seedu.address.reimbursement.logic.LogicManager(reimbursementModel, reimbursementManager,
                        transactionModel, transactionManager, personModel);
        seedu.address.cashier.logic.Logic cashierLogic =
                new seedu.address.cashier.logic.LogicManager(cashierModel, cashierManager, personModel,
                        transactionModel, transactionManager, inventoryModel, inventoryManager);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON,
                () -> addCommand.execute(personModelStub, logic, reimbursementLogic, cashierLogic));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
