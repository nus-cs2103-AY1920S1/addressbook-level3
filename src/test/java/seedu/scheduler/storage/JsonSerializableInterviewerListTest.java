package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.commons.util.JsonUtil;
import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.testutil.TypicalPersons;

public class JsonSerializableInterviewerListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableInterviewerListTest");
    private static final Path TYPICAL_INTERVIEWERLIST_FILE = TEST_DATA_FOLDER.resolve("typicalInterviewerList.json");
    private static final Path INVALID_INTERVIEWERLIST_FILE = TEST_DATA_FOLDER.resolve("invalidInterviewerList.json");
    private static final Path DUPLICATE_INTERVIEWER_FILE = TEST_DATA_FOLDER.resolve("duplicateInterviewerInList.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableInterviewerList dataFromFile = JsonUtil.readJsonFile(TYPICAL_INTERVIEWERLIST_FILE,
                JsonSerializableInterviewerList.class).get();
        InterviewerList interviewerListFromFile = dataFromFile.toModelType();
        InterviewerList typicalInterviewerList = TypicalPersons.getTypicalInterviewerList();
        assertEquals(interviewerListFromFile, typicalInterviewerList);
    }

    @Test
    public void toModelType_invalidInterviewerListFile_throwsIllegalArgumentException() throws Exception {
        JsonSerializableInterviewerList dataFromFile = JsonUtil.readJsonFile(INVALID_INTERVIEWERLIST_FILE,
                JsonSerializableInterviewerList.class).get();
        assertThrows(IllegalArgumentException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateInterviewer_throwsIllegalValueException() throws Exception {
        JsonSerializableInterviewerList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INTERVIEWER_FILE,
                JsonSerializableInterviewerList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableInterviewerList.MESSAGE_DUPLICATE_INTERVIEWER,
                dataFromFile::toModelType);
    }

}
