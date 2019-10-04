package mams.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static mams.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import mams.commons.exceptions.IllegalValueException;
import mams.commons.util.JsonUtil;
import mams.model.Mams;
import mams.testutil.Assert;
import mams.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

public class JsonSerializableMamsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMamsTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsMams.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonMams.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonMams.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableMams dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableMams.class).get();
        Mams mamsFromFile = dataFromFile.toModelType();
        Mams typicalPersonsMams = TypicalPersons.getTypicalMams();
        assertEquals(mamsFromFile, typicalPersonsMams);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMams dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableMams.class).get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableMams dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableMams.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableMams.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
