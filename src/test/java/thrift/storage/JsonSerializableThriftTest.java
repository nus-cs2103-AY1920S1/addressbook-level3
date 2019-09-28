package thrift.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static thrift.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

//import org.junit.jupiter.api.Test;

//import thrift.commons.exceptions.IllegalValueException;
//import thrift.commons.util.JsonUtil;
//import thrift.model.Thrift;
//import thrift.testutil.TypicalTransactions;

public class JsonSerializableThriftTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableThriftTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalTransactionThrift.json");
    private static final Path INVALID_TRANSACTIONS_FILE = TEST_DATA_FOLDER.resolve(
            "invalidTransactionThrift.json");

    /* TODO: Fix the test case when it is possible to read and parse from json file correctly.
    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableThrift dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
                JsonSerializableThrift.class).get();
        Thrift thriftFromFile = dataFromFile.toModelType();
        Thrift typicalTransactionsThrift = TypicalTransactions.getTypicalThrift();
        assertEquals(thriftFromFile, typicalTransactionsThrift);
    }
     */

    /* TODO: Fix the test case when it is possible to read and parse from json file correctly.
    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableThrift dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTIONS_FILE,
                JsonSerializableThrift.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
     */

}
