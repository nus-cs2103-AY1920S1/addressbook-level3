package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ModulePlanner;
import seedu.address.testutil.TypicalStudyPlans;

public class JsonSerializableModulePlannerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModulePlannerTest");
    private static final Path TYPICAL_STUDYPLANS_FILE = TEST_DATA_FOLDER.resolve("typicalStudyPlansModulePlanner.json");
    private static final Path INVALID_STUDYPLAN_FILE = TEST_DATA_FOLDER.resolve("invalidStudyPlanModulePlanner.json");
    private static final Path DUPLICATE_STUDYPLAN_FILE = TEST_DATA_FOLDER.resolve("duplicateStudyPlanModulePlanner.json");

    @Test
    public void toModelType_typicalStudyPlansFile_success() throws Exception {
        JsonSerializableModulePlanner dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDYPLANS_FILE,
                JsonSerializableModulePlanner.class).get();
        ModulePlanner modulePlannerFromFile = dataFromFile.toModelType();
        ModulePlanner typicalStudyPlansModulePlanner = TypicalStudyPlans.getTypicalModulePlanner();
        assertEquals(modulePlannerFromFile, typicalStudyPlansModulePlanner);
    }

    @Test
    public void toModelType_invalidStudyPlanFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModulePlanner dataFromFile = JsonUtil.readJsonFile(INVALID_STUDYPLAN_FILE,
                JsonSerializableModulePlanner.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudyPlans_throwsIllegalValueException() throws Exception {
        JsonSerializableModulePlanner dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDYPLAN_FILE,
                JsonSerializableModulePlanner.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableModulePlanner.MESSAGE_DUPLICATE_STUDYPLAN,
                dataFromFile::toModelType);
    }

}
