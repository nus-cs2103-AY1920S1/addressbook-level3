package seedu.address.person.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.util.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.util.CliSyntax.PREFIX_EMAIL;
import static seedu.address.util.CliSyntax.PREFIX_NAME;
import static seedu.address.util.CliSyntax.PREFIX_PHONE;
import static seedu.address.util.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.inventory.util.InventoryList;
import seedu.address.person.commons.core.index.Index;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.AddressBook;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.NameContainsKeywordsPredicate;
import seedu.address.person.model.person.Person;
<<<<<<< HEAD
import seedu.address.stubs.TransactionLogicStub;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Transaction;
=======
import seedu.address.person.storage.AddressBookStorage;
import seedu.address.person.storage.JsonAddressBookStorage;
import seedu.address.person.storage.JsonUserPrefsStorage;
import seedu.address.person.storage.UserPrefsStorage;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.logic.LogicManager;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.util.TransactionList;
>>>>>>> upstream/master

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    private static seedu.address.transaction.model.ModelManager model =
            new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
    private static Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private static TransactionLogicStub transactionLogicStub = new TransactionLogicStub(model, personModel);

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            TransactionList transactionList = new TransactionList();
            ReimbursementList reimbursementList = new ReimbursementList();
            InventoryList inventoryList = new InventoryList();
            seedu.address.cashier.util.InventoryList cashierList = new seedu.address.cashier.util.InventoryList();

            Path userPrefPath = Paths.get("data/test/userPrefs.txt");
            Path addressPath = Paths.get("data/test/address.txt");

            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);
            AddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressPath);


            //all related ModelManagers
            seedu.address.transaction.model.Model transactionModel = new ModelManager(transactionList);
            seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();
            seedu.address.reimbursement.model.Model reimbursementModel=
                    new seedu.address.reimbursement.model.ModelManager(reimbursementList);
            seedu.address.cashier.model.ModelManager cashierModel =
                    new seedu.address.cashier.model.ModelManager(cashierList);
            seedu.address.inventory.model.Model inventoryModel =
                    new seedu.address.inventory.model.ModelManager(inventoryList);

            //all related StorageManagers
            seedu.address.transaction.storage.StorageManager transactionManager =
                    new StorageManager("", personModel);
            seedu.address.person.storage.StorageManager personManager=
                    new seedu.address.person.storage.StorageManager(addressBookStorage, userPrefsStorage);
            seedu.address.reimbursement.storage.StorageManager reimbursementManager =
                    new seedu.address.reimbursement.storage.StorageManager(
                            "data/test/reimbursement.txt", transactionModel);
            seedu.address.cashier.storage.StorageManager cashierManager = new seedu.address.cashier.storage.StorageManager(
                    "data/test/inventory.txt", "data/test/transaction.txt", personModel);
            seedu.address.inventory.storage.StorageManager inventoryManager =
                    new seedu.address.inventory.storage.StorageManager("data/test/inventory.txt");

            Logic logic = new LogicManager(transactionModel, transactionManager, personModel, personManager,
                    reimbursementModel, reimbursementManager);
            seedu.address.reimbursement.logic.Logic reimbursementLogic =
                    new seedu.address.reimbursement.logic.LogicManager(reimbursementModel, reimbursementManager,
                            transactionModel, transactionManager, personModel);
            seedu.address.cashier.logic.Logic cashierLogic =
                    new seedu.address.cashier.logic.LogicManager(cashierModel, cashierManager, personModel,
                            personManager, reimbursementModel, reimbursementManager, transactionModel,
                            transactionManager, inventoryModel, inventoryManager);

            CommandResult result = command.execute(actualModel, transactionLogicStub, reimbursementLogic, cashierLogic);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException | IOException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        TransactionList transactionList = new TransactionList();
        ReimbursementList reimbursementList = new ReimbursementList();
        InventoryList inventoryList = new InventoryList();
        seedu.address.cashier.util.InventoryList cashierList = new seedu.address.cashier.util.InventoryList();

        Path userPrefPath = Paths.get("data/test/userPrefs.txt");
        Path addressPath = Paths.get("data/test/address.txt");

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefPath);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressPath);


        //all related ModelManagers
        seedu.address.transaction.model.Model transactionModel = new ModelManager(transactionList);
        seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();
        seedu.address.reimbursement.model.Model reimbursementModel=
                new seedu.address.reimbursement.model.ModelManager(reimbursementList);
        seedu.address.cashier.model.ModelManager cashierModel =
                new seedu.address.cashier.model.ModelManager(cashierList);
        seedu.address.inventory.model.Model inventoryModel =
                new seedu.address.inventory.model.ModelManager(inventoryList);

        //all related StorageManagers
        seedu.address.transaction.storage.StorageManager transactionManager =
                new StorageManager("", personModel);
        seedu.address.person.storage.StorageManager personManager=
                new seedu.address.person.storage.StorageManager(addressBookStorage, userPrefsStorage);
        seedu.address.reimbursement.storage.StorageManager reimbursementManager =
                new seedu.address.reimbursement.storage.StorageManager(
                        "data/test/reimbursement.txt", transactionModel);
        seedu.address.cashier.storage.StorageManager cashierManager = new seedu.address.cashier.storage.StorageManager(
                "data/test/inventory.txt", "data/test/transaction.txt", personModel);
        seedu.address.inventory.storage.StorageManager inventoryManager =
                new seedu.address.inventory.storage.StorageManager("data/test/inventory.txt");

        Logic logic = new LogicManager(transactionModel, transactionManager, personModel, personManager,
                reimbursementModel, reimbursementManager);
        seedu.address.reimbursement.logic.Logic reimbursementLogic =
                new seedu.address.reimbursement.logic.LogicManager(reimbursementModel, reimbursementManager,
                        transactionModel, transactionManager, personModel);
        seedu.address.cashier.logic.Logic cashierLogic =
                new seedu.address.cashier.logic.LogicManager(cashierModel, cashierManager, personModel,
                        personManager, reimbursementModel, reimbursementManager, transactionModel,
                        transactionManager, inventoryModel, inventoryManager);

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel,
                logic, reimbursementLogic, cashierLogic));

        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }
}
