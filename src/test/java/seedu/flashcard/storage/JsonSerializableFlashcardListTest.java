package seedu.flashcard.storage;

import static seedu.flashcard.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.commons.util.JsonUtil;
import seedu.flashcard.model.FlashcardList;

public class JsonSerializableFlashcardListTest {

    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonSerializableFlashcardListTest");
    private static final Path TYPICAL_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("typicalFlashcardList.json");
    private static final Path DUPLICATE_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("duplicateFlashcardList.json");
    private static final Path INVALID_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("invalidFlashcardList.json");
    private static final Path INVALID_MCQ_FILE = TEST_DATA_FOLDER.resolve("invalidMCQFlashcardList.json");

    @Test
    public void toModelType_typicalFlashcardFile_success() throws Exception {
        JsonSerializableFlashcardList dataFromFile = JsonUtil.readJsonFile(TYPICAL_FLASHCARD_FILE,
            JsonSerializableFlashcardList.class).get();
        FlashcardList flashcardList = dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateFlashcardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashcardList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FLASHCARD_FILE,
            JsonSerializableFlashcardList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidFlashcardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashcardList dataFromFile = JsonUtil.readJsonFile(INVALID_FLASHCARD_FILE,
            JsonSerializableFlashcardList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidMcqFlashcardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashcardList dataFromFile = JsonUtil.readJsonFile(INVALID_MCQ_FILE,
            JsonSerializableFlashcardList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
