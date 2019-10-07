package mams.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static mams.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import mams.commons.exceptions.IllegalValueException;
import mams.commons.util.JsonUtil;
import mams.model.Mams;
import mams.testutil.Assert;
import mams.testutil.TypicalStudents;
import org.junit.jupiter.api.Test;

public class JsonSerializableMamsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMamsTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsMams.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentMams.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentMams.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableMams dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
                JsonSerializableMams.class).get();
        Mams mamsFromFile = dataFromFile.toModelType();
        Mams typicalStudentsMams = TypicalStudents.getTypicalMams();
        assertEquals(mamsFromFile, typicalStudentsMams);
    }

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
