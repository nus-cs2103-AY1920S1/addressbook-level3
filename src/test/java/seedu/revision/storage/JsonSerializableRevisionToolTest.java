package seedu.revision.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.revision.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.commons.util.JsonUtil;
import seedu.revision.model.RevisionTool;
import seedu.revision.testutil.TypicalMcqs;

public class JsonSerializableRevisionToolTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableRevisionToolTest");
    private static final Path TYPICAL_ANSWERABLES_FILE = TEST_DATA_FOLDER.resolve("typicalAnswerableTestBank.json");
    private static final Path INVALID_ANSWERABLE_FILE = TEST_DATA_FOLDER.resolve("invalidAnswerableTestBank.json");
    private static final Path DUPLICATE_ANSWERABLE_FILE = TEST_DATA_FOLDER.resolve("duplicateAnswerableTestBank.json");

    @Test
    public void toModelType_typicalAnswerablesFile_success() throws Exception {
        JsonSerializableRevisionTool dataFromFile = JsonUtil.readJsonFile(TYPICAL_ANSWERABLES_FILE,
                JsonSerializableRevisionTool.class).get();
        RevisionTool addressBookFromFile = dataFromFile.toModelType();
        RevisionTool typicalAnswerablesAddressBook = TypicalMcqs.getTypicalRevisionTool();
        assertEquals(addressBookFromFile, typicalAnswerablesAddressBook);
    }

    /*
    @Test
    public void toModelType_invalidAnswerableFile_throwsIllegalValueException() throws Exception {
        JsonSerializableRevisionTool dataFromFile = JsonUtil.readJsonFile(INVALID_ANSWERABLE_FILE,
                JsonSerializableRevisionTool.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
    */

    @Test
    public void toModelType_duplicateAnswerables_throwsIllegalValueException() throws Exception {
        JsonSerializableRevisionTool dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ANSWERABLE_FILE,
                JsonSerializableRevisionTool.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableRevisionTool.MESSAGE_DUPLICATE_ANSWERABLE,
                dataFromFile::toModelType);
    }

}
