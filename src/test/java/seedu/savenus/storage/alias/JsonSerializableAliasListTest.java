package seedu.savenus.storage.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.alias.AliasList;

public class JsonSerializableAliasListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAliasListTest");
    private static final Path INVALID_ALIAS_LIST_FILE = TEST_DATA_FOLDER.resolve("invalidAliasList.json");
    private static final Path MISSING_ALIAS_LIST_FILE = TEST_DATA_FOLDER.resolve("missingAliasList.json");
    private static final Path PROPER_ALIAS_LIST_FILE = TEST_DATA_FOLDER.resolve("properAliasList.json");

    @Test
    public void toModelType_invalidAliasPair_throwsIllegalValueException() throws Exception {
        JsonSerializableAliasList dataFromFile = JsonUtil.readJsonFile(INVALID_ALIAS_LIST_FILE,
                JsonSerializableAliasList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_missingAliasPairs_throwsIllegalValueException() throws Exception {
        JsonSerializableAliasList dataFromFile = JsonUtil.readJsonFile(MISSING_ALIAS_LIST_FILE,
                JsonSerializableAliasList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_correctAliasPairs_success() throws Exception {
        JsonSerializableAliasList dataFromFile = JsonUtil.readJsonFile(PROPER_ALIAS_LIST_FILE,
                JsonSerializableAliasList.class).get();
        assertEquals(dataFromFile.toModelType(), new AliasList());
    }
}
