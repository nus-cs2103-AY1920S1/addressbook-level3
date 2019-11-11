package seedu.system.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.system.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.commons.util.JsonUtil;
import seedu.system.model.Data;
import seedu.system.testutil.TypicalCompetitions;

public class JsonSerializableCompetitionDataTest {

    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonSerializableCompetitionDataTest");
    private static final Path TYPICAL_COMPETITIONS_FILE = TEST_DATA_FOLDER.resolve("typicalData.json");
    private static final Path INVALID_COMPETITION_FILE = TEST_DATA_FOLDER.resolve("invalidCompetitionData.json");
    private static final Path DUPLICATE_COMPETITION_FILE = TEST_DATA_FOLDER.resolve("duplicateCompetitionData.json");

    @Test
    public void toModelType_typicalCompetitionsFile_success() throws Exception {
        JsonSerializableCompetitionData dataFromFile = JsonUtil.readJsonFile(TYPICAL_COMPETITIONS_FILE,
                JsonSerializableCompetitionData.class).get();
        Data dataBookFromFile = dataFromFile.toModelType();
        Data typicalCompetitionData = TypicalCompetitions.getTypicalCompetitionData();
        assertEquals(dataBookFromFile, typicalCompetitionData);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCompetitionData dataFromFile = JsonUtil.readJsonFile(INVALID_COMPETITION_FILE,
                JsonSerializableCompetitionData.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableCompetitionData dataFromFile = JsonUtil.readJsonFile(DUPLICATE_COMPETITION_FILE,
                JsonSerializableCompetitionData.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCompetitionData.MESSAGE_DUPLICATE_COMPETITION,
                dataFromFile::toModelType);
    }

}
