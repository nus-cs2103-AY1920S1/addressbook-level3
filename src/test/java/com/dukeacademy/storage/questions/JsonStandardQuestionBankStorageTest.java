package com.dukeacademy.storage.questions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.storage.question.JsonQuestionBankStorage;

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
        // Path filePath = testFolder.resolve("TempQuestionBank.json");
        // StandardQuestionBank original = getTypicalQuestionBank();
        // JsonQuestionBankStorage
        //    jsonQuestionBankStorage = new JsonQuestionBankStorage(filePath);

        // Save in new file and read back
        // jsonQuestionBankStorage.saveQuestionBank(original, filePath);
        // QuestionBank
        //   readBack = jsonQuestionBankStorage.readQuestionBank(filePath).get();
        // assertEquals(original, new StandardQuestionBank(readBack));

        // Modify data, overwrite exiting file, and read back
        // original.addQuestion(HOON);
        //        original.removeQuestion(ALICE);
        // jsonQuestionBankStorage.saveQuestionBank(original, filePath);
        // readBack = jsonQuestionBankStorage.readQuestionBank(filePath).get();
        // assertEquals(original, new StandardQuestionBank(readBack));

        // Save and read without specifying file path
        // original.addQuestion(IDA);
        // jsonQuestionBankStorage.saveQuestionBank(original); // file path not specified
        // readBack = jsonQuestionBankStorage.readQuestionBank().get(); // file path not specified
        // assertEquals(original, new StandardQuestionBank(readBack));

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

}
