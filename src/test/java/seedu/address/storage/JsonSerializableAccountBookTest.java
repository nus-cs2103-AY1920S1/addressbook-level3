package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;

public class JsonSerializableAccountBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAccountBookTest");
    private static final Path INVALID_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("invalidAccountBook.json");

    @Test
    public void toModelType_invalidFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAccountBook dataFromFile = JsonUtil.readJsonFile(INVALID_ACCOUNT_FILE,
                JsonSerializableAccountBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
