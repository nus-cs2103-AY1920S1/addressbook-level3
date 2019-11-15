package dukecooks.storage.health;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import dukecooks.commons.util.JsonUtil;
import dukecooks.model.health.HealthRecords;
import dukecooks.testutil.health.TypicalRecords;

public class JsonSerializableHealthRecordsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableHealthRecordsTest");
    private static final Path TYPICAL_RECORDS_FILE = TEST_DATA_FOLDER.resolve("typicalRecordHealthRecords.json");
    private static final Path INVALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("invalidRecordHealthRecords.json");
    private static final Path DUPLICATE_RECORD_FILE = TEST_DATA_FOLDER.resolve("duplicateRecordHealthRecords.json");

    @Test
    public void toModelType_typicalRecordsFile_success() throws Exception {
        JsonSerializableHealthRecords dataFromFile = JsonUtil.readJsonFile(TYPICAL_RECORDS_FILE,
                JsonSerializableHealthRecords.class).get();
        HealthRecords healthRecordsFromFile = dataFromFile.toModelType();
        HealthRecords typicalHealthRecords = TypicalRecords.getTypicalHealthRecords();
        assertEquals(healthRecordsFromFile, typicalHealthRecords);
    }
}
