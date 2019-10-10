package mams.storage;


import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import mams.commons.exceptions.IllegalValueException;
import mams.commons.util.JsonUtil;
import mams.testutil.Assert;

public class JsonSerializableMamsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonSerializableMamsTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsMams.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentMams.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentMams.json");

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMams dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableMams.class).get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableMams dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
                JsonSerializableMams.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableMams.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

}
