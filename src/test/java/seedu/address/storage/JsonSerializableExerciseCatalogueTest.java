package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DukeCooks;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableExerciseCatalogueTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDukeCooksTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsDukeCooks.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonDukeCooks.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonDukeCooks.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableExerciseCatalogue dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableExerciseCatalogue.class).get();
        DukeCooks dukeCooksFromFile = dataFromFile.toModelType();
        DukeCooks typicalPersonsDukeCooks = TypicalPersons.getTypicalDukeCooks();
        assertEquals(dukeCooksFromFile, typicalPersonsDukeCooks);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableExerciseCatalogue dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableExerciseCatalogue.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableExerciseCatalogue dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableExerciseCatalogue.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableExerciseCatalogue.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
