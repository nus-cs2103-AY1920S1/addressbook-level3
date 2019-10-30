package seedu.address.cashier.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.util.InventoryList;
import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

public class StorageManagerTest {
    //private File iFile;
    //private File tFile;
    private StorageManager storageManager;
    //private Model model;

    public StorageManagerTest() {
        try {
            Model model;
            File iFile;
            File tFile;
            iFile = File.createTempFile("testing", "tempInventory.txt");
            tFile = File.createTempFile("testing", "tempTransaction.txt");
            iFile.deleteOnExit();
            tFile.deleteOnExit();
            model = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            storageManager = new StorageManager(iFile, tFile, model);
        } catch (IOException e) {
            throw new AssertionError("This constructor should not throw an exception.");
        }
    }

    @Test
    public void fileReadSaveForInventory() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly writing and reading to the
         * text file.
         */
        InventoryList original = TypicalItem.getTypicalInventoryList();
        storageManager.writeToInventoryFile(original);
        InventoryList retrieved = storageManager.getInventoryList();
        assertEquals(original, retrieved);
    }

    @Test
    public void fileReadSaveForTransaction() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly writing and reading to the
         * text file.
         */
        Transaction original = TypicalTransactions.GEORGE_TRANSACTION_7;
        storageManager.appendToTransaction(original);

        TransactionList retrieved = storageManager.getTransactionList();
        assertEquals(original, retrieved.get(0));
    }
}


