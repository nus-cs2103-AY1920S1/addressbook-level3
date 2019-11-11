package seedu.address.transaction.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_CATEGORY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_DESC;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

class StorageManagerTest {
    private StorageManager storageManager;

    public StorageManagerTest() {
        try {
            File file = File.createTempFile("testing", "tempTransaction.txt");
            CheckAndGetPersonByNameModel model =
                    new ModelManager(getTypicalAddressBook(), new UserPrefs());
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

    @Test
    public void fileReadTransactionTooLarge_failure() throws Exception {
        TransactionList original = TypicalTransactions.getTypicalTransactionList();
        original.add(new Transaction(VALID_DATE, VALID_DESC, VALID_CATEGORY, -1000000.0,
                TypicalPersons.ALICE, 1, false));
        storageManager.writeFile(original);
        assertThrows(ParseException.class, () -> storageManager.readTransactionList());
    }
}
