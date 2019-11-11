package seedu.ifridge.storage;

import static seedu.ifridge.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.commons.util.JsonUtil;

public class JsonSerializableTemplateListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableTemplateListTest");
    private static final Path INVALID_TEMPLATE_ITEM_FILE = TEST_DATA_FOLDER
            .resolve("invalidTemplateTemplateList.json");
    private static final Path DUPLICATE_TEMPLATE_ITEM_FILE = TEST_DATA_FOLDER
            .resolve("duplicateTemplateTemplateList.json");

    @Test
    public void toModelType_invalidTemplateItemFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTemplateList dataFromFile = JsonUtil.readJsonFile(INVALID_TEMPLATE_ITEM_FILE,
                JsonSerializableTemplateList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTemplateItems_throwsIllegalValueException() throws Exception {
        JsonSerializableTemplateList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TEMPLATE_ITEM_FILE,
                JsonSerializableTemplateList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTemplateList.MESSAGE_DUPLICATE_TEMPLATE,
                dataFromFile::toModelType);
    }

}
