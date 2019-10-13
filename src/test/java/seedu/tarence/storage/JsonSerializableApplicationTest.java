package seedu.tarence.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.tarence.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

//import org.junit.jupiter.api.Test;

//import seedu.tarence.commons.exceptions.IllegalValueException;
//import seedu.tarence.commons.util.JsonUtil;
//import seedu.tarence.model.Application;
//import seedu.tarence.testutil.TypicalPersons;

public class JsonSerializableApplicationTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableApplicationTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsApplication.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonApplication.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonApplication.json");

    /*
    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableApplication.class).get();
        Application applicationFromFile = dataFromFile.toModelType();
        Application typicalPersonsApplication = TypicalPersons.getTypicalApplication();
        assertEquals(applicationFromFile, typicalPersonsApplication);
    }
    TODO: FIX MODEL TEST
     */

    /*
    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableApplication.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
    TODO: FIX model test
     */

    /*
    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableApplication.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableApplication.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }
    TODO: FIX MODEL TEST
     */

}
