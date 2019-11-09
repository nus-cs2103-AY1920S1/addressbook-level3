package seedu.system.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.system.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.commons.util.JsonUtil;
import seedu.system.model.Data;
import seedu.system.testutil.TypicalPersons;

public class JsonSerializablePersonDataTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePersonDataTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalData.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonData.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonData.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializablePersonData dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializablePersonData.class).get();
        Data personDataFromFile = dataFromFile.toModelType();
        Data typicalPersonsData = TypicalPersons.getTypicalPersonData();
        assertEquals(personDataFromFile, typicalPersonsData);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePersonData dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializablePersonData.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializablePersonData dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializablePersonData.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePersonData.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
