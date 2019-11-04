package seedu.address.storage.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_QUESTION;
import static seedu.address.storage.question.JsonAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.question.SavedQuestions;
import seedu.address.testutil.question.TypicalQuestions;

public class JsonSerializableQuestionsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
        "src", "test", "data", "JsonSerializableSavedQuestionsTest");
    private static final Path TYPICAL_QUESTIONS_FILE = TEST_DATA_FOLDER
        .resolve("typicalSavedQuestions.json");
    private static final Path INVALID_QUESTIONS_FILE = TEST_DATA_FOLDER
        .resolve("invalidSavedQuestions.json");
    private static final Path DUPLICATE_QUESTIONS_FILE = TEST_DATA_FOLDER
        .resolve("duplicateSavedQuestions.json");

    @Test
    public void toModelType_typicalSavedQuestionsFile_success() throws Exception {
        JsonSerializableQuestions dataFromFile = JsonUtil.readJsonFile(TYPICAL_QUESTIONS_FILE,
            JsonSerializableQuestions.class).get();
        SavedQuestions savedQuestionsFromFile = dataFromFile.toModelType();
        SavedQuestions typicalSavedQuestions = TypicalQuestions.getTypicalSavedQuestions();
        assertEquals(savedQuestionsFromFile, typicalSavedQuestions);
    }

    @Test
    public void toModelType_invalidSavedQuestionsFile_throwsIllegalValueException()
        throws Exception {
        JsonSerializableQuestions dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTIONS_FILE,
            JsonSerializableQuestions.class).get();
        assertThrows(IllegalValueException.class,
            String.format(MISSING_FIELD_MESSAGE_FORMAT, "TYPE"), dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateQuestions_throwsIllegalValueException() throws Exception {
        JsonSerializableQuestions dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QUESTIONS_FILE,
            JsonSerializableQuestions.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_QUESTION,
            dataFromFile::toModelType);
    }
}
