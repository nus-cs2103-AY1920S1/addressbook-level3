package seedu.algobase.storage;

import org.junit.jupiter.api.Test;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.commons.util.JsonUtil;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.testutil.TypicalProblems;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.algobase.testutil.Assert.assertThrows;

public class JsonSerializableAlgoBaseTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAlgoBaseTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalProblemsAlgoBase.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidProblemAlgoBase.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateProblemAlgoBase.json");

    @Test
    public void toModelType_typicalProblemsFile_success() throws Exception {
        JsonSerializableAlgoBase dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAlgoBase.class).get();
        AlgoBase algoBaseFromFile = dataFromFile.toModelType();
        AlgoBase typicalProblemsAlgoBase = TypicalProblems.getTypicalAlgoBase();
        assertEquals(algoBaseFromFile, typicalProblemsAlgoBase);
    }

    @Test
    public void toModelType_invalidProblemFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAlgoBase dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAlgoBase.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateProblems_throwsIllegalValueException() throws Exception {
        JsonSerializableAlgoBase dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAlgoBase.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAlgoBase.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
