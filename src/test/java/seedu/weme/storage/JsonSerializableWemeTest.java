package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.model.Weme;
import seedu.weme.testutil.TypicalWeme;

public class JsonSerializableWemeTest extends ApplicationTest {

    private static final Path IMAGE_DATA_FOLDER = Paths.get("src", "test", "data");
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableWemeTest");
    private static final Path TYPICAL_WEME_FILE = TEST_DATA_FOLDER.resolve("typicalWeme.json");
    private static final Path INVALID_MEME_FILE = TEST_DATA_FOLDER.resolve("invalidMemeWeme.json");
    private static final Path DUPLICATE_MEME_FILE = TEST_DATA_FOLDER.resolve("duplicateMemeWeme.json");
    private static final Path INVALID_TEMPLATE_FILE = TEST_DATA_FOLDER.resolve("invalidTemplateWeme.json");
    private static final Path DUPLICATE_TEMPLATE_FILE = TEST_DATA_FOLDER.resolve("duplicateTemplateWeme.json");

    @Test
    public void toModelType_typicalMemesFile_success() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(TYPICAL_WEME_FILE,
                JsonSerializableWeme.class).get();
        Weme wemeFromFile = dataFromFile.toModelType(IMAGE_DATA_FOLDER);
        Weme typicalWeme = TypicalWeme.getTypicalWeme();
        assertEquals(wemeFromFile, typicalWeme);
    }

    @Test
    public void toModelType_invalidMemes_throwsIllegalValueException() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(INVALID_MEME_FILE,
                JsonSerializableWeme.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelType(IMAGE_DATA_FOLDER));
    }

    @Test
    public void toModelType_duplicateMemes_throwsIllegalValueException() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEME_FILE,
                JsonSerializableWeme.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableWeme.MESSAGE_DUPLICATE_MEME, (
            ) -> dataFromFile.toModelType(IMAGE_DATA_FOLDER));
    }

    @Test
    public void toModelType_invalidTemplates_throwsIllegalValueException() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(INVALID_TEMPLATE_FILE,
            JsonSerializableWeme.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelType(IMAGE_DATA_FOLDER));
    }

    @Test
    public void toModelType_duplicateTemplates_throwsIllegalValueException() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TEMPLATE_FILE,
            JsonSerializableWeme.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableWeme.MESSAGE_DUPLICATE_TEMPLATE, (
            ) -> dataFromFile.toModelType(IMAGE_DATA_FOLDER));
    }

}
