package seedu.address.cashier.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.cashier.ui.CashierMessages.CLEARED_SUCCESSFULLY;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_NO_COMMAND;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_INDEX_CASHIER;
import static seedu.address.inventory.model.Item.DECIMAL_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.ClearCommand;
import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.AmountExceededException;
import seedu.address.cashier.storage.Storage;
import seedu.address.cashier.storage.StorageManager;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.ReadInUpdatedListOnlyModel;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.AddTransactionOnlyModel;
import seedu.address.util.CommandResult;

public class LogicManagerTest {

    private Model model;
    private Storage storage;
    //private seedu.address.person.model.Model personModel;
    //private seedu.address.transaction.storage.Storage transactionStorage;
    //private seedu.address.transaction.model.Model transactionModel;
    //private seedu.address.inventory.model.Model inventoryModel;
    private seedu.address.inventory.storage.Storage inventoryStorage;
    private Logic logic;

    LogicManagerTest() throws Exception {
        File iFile;
        File tFile;
        File rFile;
        seedu.address.person.model.Model personModel;
        //Storage storage;
        //seedu.address.person.model.CheckAndGetPersonByNameModel personModel;
        //seedu.address.person.model.Model personModel;
        seedu.address.transaction.model.Model transactionModel = null;
        seedu.address.inventory.model.Model inventoryModel;
        seedu.address.transaction.logic.Logic transactionLogic;
        seedu.address.inventory.logic.Logic inventoryLogic;
        seedu.address.transaction.storage.Storage transactionStorage;
        //seedu.address.inventory.storage.Storage inventoryStorage;
        //seedu.address.reimbursement.logic.Logic reimbursementLogic = null;
        //seedu.address.reimbursement.storage.Storage reimbursementStorage = null;
        //Logic logic;

        try {
            model = new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
            personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            iFile = File.createTempFile("testing", "tempInventory.txt");
            tFile = File.createTempFile("testing", "tempTransaction.txt");
            rFile = File.createTempFile("testing", "tempReimbursement.txt");

            seedu.address.reimbursement.storage.StorageManager reimbursementManager =
                    new seedu.address.reimbursement.storage.StorageManager(rFile);
            seedu.address.reimbursement.model.Model reimbursementModel =
                    new seedu.address.reimbursement.model.ModelManager(
                            reimbursementManager.getReimbursementFromFile(model.getTransactionList()));

            transactionStorage =
                    new seedu.address.transaction.storage.StorageManager(tFile,
                            (CheckAndGetPersonByNameModel) personModel);


            transactionModel = new seedu.address.transaction.model.ModelManager(
                    TypicalTransactions.getTypicalTransactionList());

            transactionLogic = new seedu.address.transaction.logic.LogicManager(transactionModel,
                    transactionStorage, (CheckAndGetPersonByNameModel) personModel);


            inventoryModel =
                    new seedu.address.inventory.model.ModelManager(
                            new seedu.address.inventory.storage.StorageManager(iFile).getInventoryList());
            inventoryStorage =
                    new seedu.address.inventory.storage.StorageManager(iFile);

            inventoryLogic = new seedu.address.inventory.logic.LogicManager(
                    (seedu.address.inventory.model.ModelManager) inventoryModel,
                    (seedu.address.inventory.storage.StorageManager) inventoryStorage);
            storage = new StorageManager(inventoryLogic, transactionLogic);

            logic =
                    new LogicManager(model, storage, (CheckAndGetPersonByNameModel) personModel,
                            (AddTransactionOnlyModel) transactionModel,
                            (ReadInUpdatedListOnlyModel) inventoryModel);
        } catch (IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        }

    }

    @Test
    public void getCashier_successful() throws NoCashierFoundException {
        Person p = new PersonBuilder().build();
        model.setCashier(p);
        assertEquals(String.valueOf(p.getName()), logic.getCashier());
    }

    @Test
    public void getCashier_failure() throws NoCashierFoundException {
        model.resetCashier();
        assertEquals("", logic.getCashier());
    }

    @Test
    public void getAmount_successful() throws AmountExceededException {
        double amount = model.getTotalAmount();
        assertEquals(String.valueOf(DECIMAL_FORMAT.format(amount)), logic.getAmount());
    }

    @Test
    public void getSalesList_successful() throws Exception {
        ArrayList<Item> list = model.getSalesList();
        assertEquals(list, logic.getSalesList());
    }

    @Test
    public void getInventoryList_successful() throws Exception {
        assertEquals(logic.getInventoryList(), model.getInventoryList());
    }

    @Test
    public void writeInInventoryFile_successful() throws Exception {
        logic.writeInInventoryFile();
        assertEquals(storage.getInventoryList(), model.getInventoryList());
    }


    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "yufkvghkk";
        assertParseException(invalidCommand, MESSAGE_NO_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 4684";
        assertCommandException(deleteCommand, NO_SUCH_INDEX_CASHIER);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ClearCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, CLEARED_SUCCESSFULLY, model);
    }



    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) {
        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(expectedModel, model);
        } catch (Exception e) {
            throw new AssertionError("There should not be an error");
        }

    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, Exception.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, seedu.address.cashier.model.Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, Exception.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new seedu.address.cashier.model.ModelManager(model.getInventoryList(),
                model.getTransactionList());
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
        assertEquals(expectedModel.getInventoryList(), model.getInventoryList());
        assertEquals(expectedModel.getSalesList(), model.getSalesList());
    }


}
