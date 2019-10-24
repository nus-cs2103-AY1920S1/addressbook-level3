package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.KeyboardFlashCards;
import seedu.address.testutil.TypicalFlashCards;

public class JsonSerializableKeyboardFlashCardsTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableKeyboardFlashCardsTest");
    private static final Path TYPICAL_FLASHCARD_FILE =
            TEST_DATA_FOLDER.resolve("typicalFlashCardKeyboardFlashCards.json");
    private static final Path INVALID_FLASHCARD_FILE =
            TEST_DATA_FOLDER.resolve("invalidFlashCardKeyboardFlashCards.json");
    private static final Path DUPLICATE_FLASHCARD_FILE =
            TEST_DATA_FOLDER.resolve("duplicateFlashCardKeyboardFlashCards.json");
    private static final Path INVALID_DEADLINE_FILE =
            TEST_DATA_FOLDER.resolve("invalidDeadlineKeyboardFlashCards.json");
    private static final Path DUPLICATE_DEADLINE_FILE =
            TEST_DATA_FOLDER.resolve("duplicateDeadlineKeyboardFlashCards.json");

    @Test
    public void toModelType_typicalFlashCardFile_success() throws Exception {
        JsonSerializableKeyboardFlashCards dataFromFile = JsonUtil.readJsonFile(TYPICAL_FLASHCARD_FILE,
                JsonSerializableKeyboardFlashCards.class).get();
        KeyboardFlashCards keyboardFlashCardsFromFile = dataFromFile.toModelType();
        KeyboardFlashCards typicalFlashCardsKeyboardFlashCards = TypicalFlashCards.getTypicalAddressBook();
        assertEquals(keyboardFlashCardsFromFile, typicalFlashCardsKeyboardFlashCards);
    }

    @Test
    public void toModelType_invalidFlashCardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableKeyboardFlashCards dataFromFile = JsonUtil.readJsonFile(INVALID_FLASHCARD_FILE,
                JsonSerializableKeyboardFlashCards.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateFlashCards_throwsIllegalValueException() throws Exception {
        JsonSerializableKeyboardFlashCards dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FLASHCARD_FILE,
                JsonSerializableKeyboardFlashCards.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableKeyboardFlashCards.MESSAGE_DUPLICATE_FLASHCARD,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidDeadlineFile_throwsIllegalValueException() throws Exception {
        JsonSerializableKeyboardFlashCards dataFromFile = JsonUtil.readJsonFile(INVALID_DEADLINE_FILE,
                JsonSerializableKeyboardFlashCards.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateDeadlines_throwsIllegalValueException() throws Exception {
        JsonSerializableKeyboardFlashCards dataFromFile = JsonUtil.readJsonFile(DUPLICATE_DEADLINE_FILE,
                JsonSerializableKeyboardFlashCards.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableKeyboardFlashCards.MESSAGE_DUPLICATE_DEADLINE,
                dataFromFile::toModelType);
    }

}
