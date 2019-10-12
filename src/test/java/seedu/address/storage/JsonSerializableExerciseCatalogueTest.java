package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DukeCooks;
import seedu.address.testutil.TypicalDiaries;

public class JsonSerializableExerciseCatalogueTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDukeCooksTest");
    private static final Path TYPICAL_DIARIES_FILE = TEST_DATA_FOLDER.resolve("typicalDiariesDukeCooks.json");

    @Test
    public void toModelType_typicalDiariesFile_success() throws Exception {
        JsonSerializableExerciseCatalogue dataFromFile = JsonUtil.readJsonFile(TYPICAL_DIARIES_FILE,
                JsonSerializableExerciseCatalogue.class).get();
        DukeCooks dukeCooksFromFile = dataFromFile.toModelType();
        DukeCooks typicalDiariesDukeCooks = TypicalDiaries.getTypicalDukeCooks();
        assertEquals(dukeCooksFromFile, typicalDiariesDukeCooks);
    }
}
