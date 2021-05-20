package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;

//@@author{weigenie}
public class JsonAdaptedIdTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAdapted");
    private static final Path INVALID_ID_FILE = TEST_DATA_FOLDER.resolve("InvalidId.json");

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() throws Exception {
        assertThrows(IllegalValueException.class,
                JsonUtil.readJsonFile(INVALID_ID_FILE, JsonSerializableFinSec.class).get()::toModelType);
    }
}
