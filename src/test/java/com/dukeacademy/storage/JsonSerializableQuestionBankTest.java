package com.dukeacademy.storage;

import static com.dukeacademy.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.JsonUtil;
import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.testutil.TypicalQuestions;

public class JsonSerializableQuestionBankTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableQuestionBankTest");
    private static final Path TYPICAL_QUESTIONS_FILE = TEST_DATA_FOLDER.resolve("typicalQuestionQuestionBank.json");
    private static final Path INVALID_QUESTION_FILE = TEST_DATA_FOLDER.resolve("invalidQuestionQuestionBank.json");
    private static final Path DUPLICATE_QUESTION_FILE = TEST_DATA_FOLDER.resolve("duplicateQuestionQuestionBank.json");

    @Test
    public void toModelType_typicalQuestionsFile_success() throws Exception {
        JsonSerializableQuestionBank dataFromFile = JsonUtil.readJsonFile(TYPICAL_QUESTIONS_FILE,
                JsonSerializableQuestionBank.class).get();
        QuestionBank questionBankFromFile = dataFromFile.toModelType();
        QuestionBank typicalQuestionsQuestionBank =
            TypicalQuestions.getTypicalQuestionBank();
        assertEquals(questionBankFromFile, typicalQuestionsQuestionBank);
    }

    @Test
    public void toModelType_invalidQuestionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableQuestionBank dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTION_FILE,
                JsonSerializableQuestionBank.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateQuestions_throwsIllegalValueException() throws Exception {
        JsonSerializableQuestionBank dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QUESTION_FILE,
                JsonSerializableQuestionBank.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableQuestionBank.MESSAGE_DUPLICATE_QUESTION,
                dataFromFile::toModelType);
    }

}
