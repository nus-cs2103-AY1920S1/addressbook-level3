package com.dukeacademy.storage.questions;

import static com.dukeacademy.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.JsonUtil;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.storage.question.JsonSerializableStandardQuestionBank;
import com.dukeacademy.testutil.TypicalQuestions;

import javafx.collections.transformation.SortedList;

public class JsonSerializableStandardQuestionBankTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableQuestionBankTest");
    private static final Path TYPICAL_QUESTIONS_FILE = TEST_DATA_FOLDER.resolve("typicalQuestionQuestionBank.json");
    private static final Path INVALID_QUESTION_FILE = TEST_DATA_FOLDER.resolve("invalidQuestionQuestionBank.json");

    @Test
    public void toModelType_typicalQuestionsFile_success() throws Exception {
        JsonSerializableStandardQuestionBank dataFromFile = JsonUtil.readJsonFile(TYPICAL_QUESTIONS_FILE,
                JsonSerializableStandardQuestionBank.class).get();
        StandardQuestionBank standardQuestionBankFromFile = dataFromFile.toModelType();
        StandardQuestionBank typicalQuestionsStandardQuestionBank = TypicalQuestions.getTypicalQuestionBank();
        assertTrue(this.checkQuestionBanksEqual(standardQuestionBankFromFile, typicalQuestionsStandardQuestionBank));
    }

    @Test
    public void toModelType_invalidQuestionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableStandardQuestionBank dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTION_FILE,
                JsonSerializableStandardQuestionBank.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /**
     * Helper function to check if the questions in 2 question banks are equal.
     * @param bank1 the first question bank to be checked.
     * @param bank2 the second question bank to be checked.
     * @return true if the questions in both banks are equal.
     */
    private boolean checkQuestionBanksEqual(StandardQuestionBank bank1, StandardQuestionBank bank2) {
        SortedList<Question> list1 = bank1.getReadOnlyQuestionListObservable()
                .sorted(Comparator.comparing(Question::getTitle));
        SortedList<Question> list2 = bank2.getReadOnlyQuestionListObservable()
                .sorted(Comparator.comparing(Question::getTitle));

        if (list1.size() != list2.size()) {
            return false;
        }

        if (list1.size() == 0) {
            return true;
        }

        return IntStream.range(0, list1.size())
                .mapToObj(i -> list1.get(i).checkContentsEqual(list2.get(i)))
                .reduce((x, y) -> x && y).get();
    }
}
