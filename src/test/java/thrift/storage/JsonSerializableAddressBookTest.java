package thrift.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static thrift.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

//import org.junit.jupiter.api.Test;

//import thrift.commons.exceptions.IllegalValueException;
//import thrift.commons.util.JsonUtil;
//import thrift.model.AddressBook;
//import thrift.testutil.TypicalTransactions;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalTransactionsAddressBook.json");
    private static final Path INVALID_TRANSACTIONS_FILE = TEST_DATA_FOLDER.resolve(
            "invalidTransactionAddressBook.json");

    /* TODO: Fix the test case when it is possible to read and parse from json file correctly.
    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalTransactionsAddressBook = TypicalTransactions.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalTransactionsAddressBook);
    }
     */

    /* TODO: Fix the test case when it is possible to read and parse from json file correctly.
    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTIONS_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
     */

}
