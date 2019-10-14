package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DukeCooks;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableRecipeBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDukeCooksTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsDukeCooks.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableRecipeBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableRecipeBook.class).get();
        DukeCooks dukeCooksFromFile = dataFromFile.toModelType();
        DukeCooks typicalPersonsDukeCooks = TypicalPersons.getTypicalDukeCooks();
        assertEquals(dukeCooksFromFile, typicalPersonsDukeCooks);
    }
}
