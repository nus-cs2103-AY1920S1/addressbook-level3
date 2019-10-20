package com.dukeacademy.storage;

import static com.dukeacademy.testutil.Assert.assertThrows;

import static com.dukeacademy.testutil.TypicalQuestions.getTypicalQuestionBank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import com.dukeacademy.model.StandardQuestionBank;
import com.dukeacademy.model.question.Question;
import javafx.collections.transformation.SortedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.model.QuestionBank;

public class JsonStandardQuestionBankStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonQuestionBankStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readQuestionBank_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readQuestionBank(null));
    }

    private java.util.Optional<QuestionBank> readQuestionBank(String filePath) throws Exception {
        return new JsonQuestionBankStorage(Paths.get(filePath)).readQuestionBank(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readQuestionBank("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readQuestionBank("notJsonFormatQuestionBank.json"));
    }

    @Test
    public void readQuestionBank_invalidQuestionQuestionBank_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readQuestionBank("invalidQuestionQuestionBank.json"));
    }

    @Test
    public void readQuestionBank_invalidAndValidQuestionQuestionBank_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readQuestionBank(
            "invalidAndValidQuestionQuestionBank.json"));
    }

    @Test
    public void readAndSaveQuestionBank_allInOrder_success() throws Exception {
//        Path filePath = testFolder.resolve("TempQuestionBank.json");
//        StandardQuestionBank original = getTypicalQuestionBank();
//        JsonQuestionBankStorage
//            jsonQuestionBankStorage = new JsonQuestionBankStorage(filePath);
//
//        // Save in new file and read back
//        jsonQuestionBankStorage.saveQuestionBank(original, filePath);
//        QuestionBank
//            readBack = jsonQuestionBankStorage.readQuestionBank(filePath).get();
//        assertEquals(original, new StandardQuestionBank(readBack));
//
//        // Modify data, overwrite exiting file, and read back
//        original.addQuestion(HOON);
////        original.removeQuestion(ALICE);
//        jsonQuestionBankStorage.saveQuestionBank(original, filePath);
//        readBack = jsonQuestionBankStorage.readQuestionBank(filePath).get();
//        assertEquals(original, new StandardQuestionBank(readBack));
//
//        // Save and read without specifying file path
//        original.addQuestion(IDA);
//        jsonQuestionBankStorage.saveQuestionBank(original); // file path not specified
//        readBack = jsonQuestionBankStorage.readQuestionBank().get(); // file path not specified
//        assertEquals(original, new StandardQuestionBank(readBack));

    }

    @Test
    public void saveQuestionBank_nullQuestionBank_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveQuestionBank(null, "SomeFile.json"));
    }

    /**
     * Saves {@code questionBank} at the specified {@code filePath}.
     */
    private void saveQuestionBank(QuestionBank questionBank,
                                  String filePath) {
        try {
            new JsonQuestionBankStorage(Paths.get(filePath))
                    .saveQuestionBank(questionBank,
                        addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveQuestionBank_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveQuestionBank(new StandardQuestionBank(), null));
    }

    private boolean checkQuestionBanksEqual(StandardQuestionBank bank1, StandardQuestionBank bank2) {
        SortedList<Question> list1 = bank1.getReadOnlyQuestionListObservable().sorted((q1, q2) -> q1.getTitle().compareTo(q2.getTitle()));
        SortedList<Question> list2 = bank2.getReadOnlyQuestionListObservable().sorted((q1, q2) -> q1.getTitle().compareTo(q2.getTitle()));

        if (list1.size() != list2.size()) {
            return false;
        }

        if (list1.size() == 0) {
            return true;
        }

        return IntStream.range(0, list1.size())
                .mapToObj(i -> list1.get(i).getTitle().equals(list2.get(i).getTitle()))
                .reduce((x, y) -> x && y).get();
    }
}
