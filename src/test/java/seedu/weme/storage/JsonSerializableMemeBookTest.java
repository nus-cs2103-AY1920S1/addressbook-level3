package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.model.MemeBook;
import seedu.weme.testutil.TypicalMemeBook;

public class JsonSerializableMemeBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMemeBookTest");
    private static final Path TYPICAL_MEMEBOOK_FILE = TEST_DATA_FOLDER.resolve("typicalMemeBook.json");
    private static final Path INVALID_MEME_FILE = TEST_DATA_FOLDER.resolve("invalidMemeMemeBook.json");
    private static final Path DUPLICATE_MEME_FILE = TEST_DATA_FOLDER.resolve("duplicateMemeMemeBook.json");
    private static final Path INVALID_TEMPLATE_FILE = TEST_DATA_FOLDER.resolve("invalidTemplateMemeBook.json");
    private static final Path DUPLICATE_TEMPLATE_FILE = TEST_DATA_FOLDER.resolve("duplicateTemplateMemeBook.json");

    @Test
    public void toModelType_typicalMemesFile_success() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEMEBOOK_FILE,
                JsonSerializableMemeBook.class).get();
        MemeBook memeBookFromFile = dataFromFile.toModelType();
        MemeBook typicalMemeBook = TypicalMemeBook.getTypicalMemeBook();
        assertEquals(memeBookFromFile, typicalMemeBook);
    }

    @Test
    public void toModelType_invalidMemes_throwsIllegalValueException() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(INVALID_MEME_FILE,
                JsonSerializableMemeBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateMemes_throwsIllegalValueException() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEME_FILE,
                JsonSerializableMemeBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMemeBook.MESSAGE_DUPLICATE_MEME,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidTemplates_throwsIllegalValueException() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(INVALID_TEMPLATE_FILE,
            JsonSerializableMemeBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTemplates_throwsIllegalValueException() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TEMPLATE_FILE,
            JsonSerializableMemeBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMemeBook.MESSAGE_DUPLICATE_TEMPLATE,
            dataFromFile::toModelType);
    }

}
