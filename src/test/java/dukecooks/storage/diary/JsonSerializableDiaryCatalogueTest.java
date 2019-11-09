package dukecooks.storage.diary;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.commons.util.JsonUtil;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.testutil.diary.TypicalDiaries;

public class JsonSerializableDiaryCatalogueTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDiaryCatalogueTest");
    private static final Path TYPICAL_DIARIES_FILE = TEST_DATA_FOLDER.resolve("typicalDiaryRecords.json");
    private static final Path DUPLICATE_DIARIES_FILE = TEST_DATA_FOLDER.resolve("duplicateDiaryRecords.json");

    @Test
    public void toModelType_typicalDiariesFile_success() throws Exception {
        JsonSerializableDiaryCatalogue dataFromFile = JsonUtil.readJsonFile(TYPICAL_DIARIES_FILE,
                JsonSerializableDiaryCatalogue.class).get();
        DiaryRecords diaryRecordsFromFile = dataFromFile.toModelType();
        DiaryRecords typicalDiaryRecords = TypicalDiaries.getTypicalDiaryRecords();
        assertEquals(diaryRecordsFromFile, typicalDiaryRecords);
    }

    @Test
    public void toModelType_duplicateDiaries_throwsIllegalValueException() throws Exception {
        JsonSerializableDiaryCatalogue dataFromFile = JsonUtil.readJsonFile(DUPLICATE_DIARIES_FILE,
                JsonSerializableDiaryCatalogue.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableDiaryCatalogue.MESSAGE_DUPLICATE_DIARY,
                dataFromFile::toModelType);
    }
}
