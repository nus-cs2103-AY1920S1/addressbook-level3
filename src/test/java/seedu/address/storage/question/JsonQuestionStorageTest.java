package seedu.address.storage.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.question.TypicalQuestions.NOT_IN_TYPICAL;
import static seedu.address.testutil.question.TypicalQuestions.OPEN_ENDED;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;
import seedu.address.testutil.question.QuestionBuilder;
import seedu.address.testutil.question.TypicalQuestions;

public class JsonQuestionStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
        "src", "test", "data", "JsonSavedQuestionsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSavedQuestions_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readQuestions(null));
    }

    private java.util.Optional<ReadOnlyQuestions> readQuestions(String filePath) throws Exception {
        return new JsonQuestionStorage(Paths.get(filePath))
            .readQuestions(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readQuestions("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
            readQuestions("notJsonFormatSavedQuestions.json"));
    }

    @Test
    public void readQuestions_invalidQuestionSavedQuestions_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readQuestions("invalidQuestionSavedQuestions.json"));
    }

    @Test
    public void readQuestions_invalidAndValidQuestionSavedQuestions_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readQuestions("invalidAndValidQuestionSavedQuestions.json"));
    }

    @Test
    public void readAndSavedQuestions_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSavedQuestions.json");
        SavedQuestions original = TypicalQuestions.getTypicalSavedQuestions();
        JsonQuestionStorage jsonSavedQuestionsStorage = new JsonQuestionStorage(filePath);

        // Save in new file and read back
        jsonSavedQuestionsStorage.saveQuestions(original, filePath);
        ReadOnlyQuestions readBack = jsonSavedQuestionsStorage.readQuestions(filePath).get();
        assertEquals(original, new SavedQuestions(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addQuestion(NOT_IN_TYPICAL);
        original.deleteQuestion(OPEN_ENDED);
        jsonSavedQuestionsStorage.saveQuestions(original, filePath);
        readBack = jsonSavedQuestionsStorage.readQuestions(filePath).get();
        assertEquals(original, new SavedQuestions(readBack));

        // Save and read without specifying file path
        original.addQuestion(new QuestionBuilder().withQuestion(OPEN_ENDED.getQuestion())
            .withAnswer(OPEN_ENDED.getAnswer()).build());
        jsonSavedQuestionsStorage.saveQuestions(original); // file path not specified
        readBack = jsonSavedQuestionsStorage.readQuestions().get(); // file path not specified
        assertEquals(original, new SavedQuestions(readBack));

    }

    @Test
    public void saveQuestions_nullSavedQuestions_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveQuestions(null, "SomeFile.json"));
    }

    /**
     * Saves {@code savedQuestions} at the specified {@code filePath}.
     */
    private void saveQuestions(ReadOnlyQuestions savedQuestions, String filePath) {
        try {
            new JsonQuestionStorage(Paths.get(filePath))
                .saveQuestions(savedQuestions, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSavedQuestions_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            saveQuestions(new SavedQuestions(), null));
    }

}
