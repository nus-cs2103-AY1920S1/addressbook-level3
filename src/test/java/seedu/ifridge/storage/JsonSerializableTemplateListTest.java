package seedu.ifridge.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class JsonSerializableTemplateListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableTemplateListTest");
    private static final Path TYPICAL_TEMPLATE_ITEMS_FILE = TEST_DATA_FOLDER
            .resolve("typicalTemplateItemsTemplateList.json");
    private static final Path INVALID_TEMPLATE_ITEM_FILE = TEST_DATA_FOLDER
            .resolve("invalidTemplateItemTemplateList.json");
    private static final Path DUPLICATE_TEMPLATE_ITEM_FILE = TEST_DATA_FOLDER
            .resolve("duplicateTemplateItemTemplateList.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        /**JsonSerializableTemplateList dataFromFile = JsonUtil.readJsonFile(TYPICAL_TEMPLATE_ITEMS_FILE,
                JsonSerializableTemplateList.class).get();
        TemplateList templateListFromFile = dataFromFile.toModelType();
        TemplateList typicalTemplateItemsTemplateList = TypicalTemplateList.getTypicalTemplateList();
        assertEquals(templateListFromFile, typicalTemplateItemsTemplateList);**/
    }

    @Test
    public void toModelType_invalidTemplateItemFile_throwsIllegalValueException() throws Exception {
        /**JsonSerializableTemplateList dataFromFile = JsonUtil.readJsonFile(INVALID_TEMPLATE_ITEM_FILE,
                JsonSerializableTemplateList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);**/
    }

    @Test
    public void toModelType_duplicateTemplateItems_throwsIllegalValueException() throws Exception {
        /**JsonSerializableTemplateList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TEMPLATE_ITEM_FILE,
                JsonSerializableTemplateList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTemplateList.MESSAGE_DUPLICATE_TEMPLATE,
                dataFromFile::toModelType);**/
    }

}
