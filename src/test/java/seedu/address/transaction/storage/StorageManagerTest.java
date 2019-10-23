package seedu.address.transaction.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.util.TransactionList;

class StorageManagerTest {
    private File file;
    private StorageManager storageManager;
    private Model model;

    public StorageManagerTest() {
        try {
            file = File.createTempFile("testing", "tempTransaction.txt");
            file.deleteOnExit();
            model = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            storageManager = new StorageManager(file, model);
        } catch (IOException e) {
            throw new AssertionError("This constructor should not throw an exception.");
        }
    }


    @Test
    public void fileReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly writing and reading to the
         * text file.
         */
        TransactionList original = TypicalTransactions.getTypicalTransactionList();
        storageManager.writeFile(original);
        TransactionList retrieved = storageManager.readTransactionList();
        assertEquals(original, retrieved);
    }
}