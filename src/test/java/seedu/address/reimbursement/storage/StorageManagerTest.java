package seedu.address.reimbursement.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.testutil.TypicalReimbursements;


public class StorageManagerTest {
    private File file;
    private StorageManager storageManager;

    public StorageManagerTest() {
        try {
            file = File.createTempFile("testingStorage", "tempReimbursement.txt");
            file.deleteOnExit();
            storageManager = new StorageManager(file);
        } catch (IOException e) {
            throw new AssertionError("This constructor should not throw an exception.");
        }
    }


    @Test
    public void file_read_save() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly writing and reading to the
         * text file.
         */
        ReimbursementList original = TypicalReimbursements.getTypicalReimbursements();
        storageManager.writeFile(original);
        ReimbursementList retrieved =
                storageManager.getReimbursementFromFile(TypicalReimbursements.getTypicalTransactions());

        assertEquals(original.toString(), retrieved.toString());
    }
}
