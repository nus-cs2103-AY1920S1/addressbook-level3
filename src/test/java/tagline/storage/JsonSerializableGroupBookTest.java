//@@author e0031374
package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tagline.commons.exceptions.IllegalValueException;
import tagline.commons.util.JsonUtil;
import tagline.model.group.GroupBook;
import tagline.storage.group.JsonSerializableGroupBook;
import tagline.testutil.TypicalGroups;

public class JsonSerializableGroupBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableGroupBookTest");
    private static final Path TYPICAL_GROUPS_FILE = TEST_DATA_FOLDER.resolve("typicalGroupsGroupBook.json");
    private static final Path INVALID_GROUP_FILE = TEST_DATA_FOLDER.resolve("invalidGroupGroupBook.json");
    private static final Path DUPLICATE_GROUP_FILE = TEST_DATA_FOLDER.resolve("duplicateGroupGroupBook.json");

    @Test
    public void toModelType_typicalGroupsFile_success() throws Exception {
        JsonSerializableGroupBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_GROUPS_FILE,
                JsonSerializableGroupBook.class).get();
        GroupBook addressBookFromFile = dataFromFile.toModelType();
        GroupBook typicalGroupsGroupBook = TypicalGroups.getTypicalGroupBook();

        assertEquals(addressBookFromFile, typicalGroupsGroupBook);
    }

    @Test
    public void toModelType_invalidGroupFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGroupBook dataFromFile = JsonUtil.readJsonFile(INVALID_GROUP_FILE,
                JsonSerializableGroupBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateGroups_throwsIllegalValueException() throws Exception {
        JsonSerializableGroupBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_GROUP_FILE,
                JsonSerializableGroupBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableGroupBook.MESSAGE_DUPLICATE_GROUP,
                dataFromFile::toModelType);
    }

}
