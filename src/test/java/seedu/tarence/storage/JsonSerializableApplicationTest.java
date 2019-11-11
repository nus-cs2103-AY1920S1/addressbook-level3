package seedu.tarence.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.commons.util.JsonUtil;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Tutorial;

public class JsonSerializableApplicationTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableApplicationTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsApplication.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonApplication.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonApplication.json");
    private static final Path VALID_APPLICATION = TEST_DATA_FOLDER.resolve("validApplication.json");
    private static final Path INVALID_APPLICATION = TEST_DATA_FOLDER.resolve("invalidApplication.json");
    private static final String MODULE_IN_VALID_APPLICATION = "CS1010S";
    private static final String TUTORIALS_IN_VALID_APPLICATION = "Tutorial-01Tutorial-01Tutorial-01";

    @Test
    public void toModelType_validApplicationFile_success() throws DataConversionException, IllegalValueException {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(VALID_APPLICATION,
                JsonSerializableApplication.class).get();
        ReadOnlyApplication applicationFromFile = dataFromFile.toModelType();

        ObservableList<Module> listOfModules = applicationFromFile.getModuleList();

        String moduleCodes = "";
        String tutorials = "";
        for (Module m: listOfModules) {
            moduleCodes += m.getModCode().toString();
            for (Tutorial t : m.getTutorials()) {
                tutorials += t.getTutName();
            }
        }

        assertEquals(tutorials, TUTORIALS_IN_VALID_APPLICATION);
        assertEquals(moduleCodes, MODULE_IN_VALID_APPLICATION);

    }

    @Test
    public void toModelType_invalidApplicationFromRile_throwsIllegalValueException() throws DataConversionException {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(INVALID_APPLICATION,
                JsonSerializableApplication.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void constructor_validArguments_returnsTrue() throws DataConversionException, IllegalValueException {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(VALID_APPLICATION,
                JsonSerializableApplication.class).get();
        ReadOnlyApplication applicationFromFile = dataFromFile.toModelType();
        JsonSerializableApplication jsonSerializableApplication = new JsonSerializableApplication(applicationFromFile);

        String dataFromFileString = "";
        for (JsonAdaptedModule m : dataFromFile.getJsonAdaptedModules()) {
            dataFromFileString += m.toString();
        }

        String dataFromConstructorString = "";
        for (JsonAdaptedModule m : jsonSerializableApplication.getJsonAdaptedModules()) {
            dataFromConstructorString += m.toString();
        }

        String classIdentifier = "seedu.tarence.storage.JsonAdaptedModule";
        assertEquals(dataFromConstructorString.contains(classIdentifier),
                dataFromFileString.contains(classIdentifier));
    }

}
