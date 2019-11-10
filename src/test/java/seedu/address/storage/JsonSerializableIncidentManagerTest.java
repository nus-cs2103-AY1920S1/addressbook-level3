package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
/*import seedu.address.model.IncidentManager;
import seedu.address.testutil.TypicalIncidents;*/

public class JsonSerializableIncidentManagerTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableIncidentManagerTest");
    private static final Path TYPICAL_ENTITIES_FILE = TEST_DATA_FOLDER.resolve("typicalEntitiesIncidentManager.json");
    private static final Path INVALID_ENTITY_FILE = TEST_DATA_FOLDER.resolve("invalidEntityIncidentManager.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonIncidentManager.json");
    private static final Path DUPLICATE_ENTITY_FILE = TEST_DATA_FOLDER.resolve("duplicateEntityIncidentManager.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonIncidentManager.json");

    /*@Test
    public void toModelType_typicalEntitiesFile_success() throws Exception {
        JsonSerializableIncidentManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_ENTITIES_FILE,
                JsonSerializableIncidentManager.class).get();
        IncidentManager incidentManagerFromFile = dataFromFile.toModelType();
        IncidentManager typicalPersonsIncidentManager = TypicalIncidents.getTypicalIncidentManager();
        assertEquals(incidentManagerFromFile, typicalPersonsIncidentManager);
    }*/

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableIncidentManager dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableIncidentManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableIncidentManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableIncidentManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableIncidentManager.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidEntityFile_throwsIllegalValueException() throws Exception {
        JsonSerializableIncidentManager dataFromFile = JsonUtil.readJsonFile(INVALID_ENTITY_FILE,
                JsonSerializableIncidentManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /*@Test
    public void toModelType_duplicateEntities_throwsIllegalValueException() throws Exception {
        JsonSerializableIncidentManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ENTITY_FILE,
                JsonSerializableIncidentManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableIncidentManager.MESSAGE_DUPLICATE_VEHICLE,
                dataFromFile::toModelType);
    }*/
}
