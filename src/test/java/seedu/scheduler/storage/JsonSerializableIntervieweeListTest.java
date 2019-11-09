package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.commons.util.JsonUtil;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.testutil.TypicalPersons;

public class JsonSerializableIntervieweeListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableIntervieweeListTest");
    private static final Path TYPICAL_INTERVIEWEELIST_FILE = TEST_DATA_FOLDER.resolve("typicalIntervieweeList.json");
    private static final Path INVALID_INTERVIEWEELIST_FILE = TEST_DATA_FOLDER.resolve("invalidIntervieweeList.json");
    private static final Path DUPLICATE_INTERVIEWEE_FILE = TEST_DATA_FOLDER.resolve("duplicateIntervieweeInList.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableIntervieweeList dataFromFile = JsonUtil.readJsonFile(TYPICAL_INTERVIEWEELIST_FILE,
                JsonSerializableIntervieweeList.class).get();
        IntervieweeList intervieweeListFromFile = dataFromFile.toModelType();
        IntervieweeList typicalIntervieweeList = TypicalPersons.getTypicalIntervieweeList();
        assertEquals(intervieweeListFromFile, typicalIntervieweeList);
    }

    @Test
    public void toModelType_invalidIntervieweeListFile_throwsIllegalValueException() throws Exception {
        JsonSerializableIntervieweeList dataFromFile = JsonUtil.readJsonFile(INVALID_INTERVIEWEELIST_FILE,
                JsonSerializableIntervieweeList.class).get();
        assertThrows(IllegalArgumentException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateInterviewee_throwsIllegalValueException() throws Exception {
        JsonSerializableIntervieweeList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INTERVIEWEE_FILE,
                JsonSerializableIntervieweeList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableIntervieweeList.MESSAGE_DUPLICATE_INTERVIEWEE,
                dataFromFile::toModelType);
    }

}
