package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.StudyBuddyPro;
//import seedu.address.testutil.TypicalFlashcards;

public class JsonSerializableFlashcardTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFlashcardTest");
    private static final Path TYPICAL_FLASHCARDS_FILE = TEST_DATA_FOLDER.resolve("typicalFlashcardsStudyBuddyPro.json");
    private static final Path INVALID_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("invalidFlashcardStudyBuddyPro.json");
    private static final Path DUPLICATE_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("duplicateFlashcardStudyBuddyPro"
            + ".json");

    /* To fix this test - Can't use preset JSON file since pulling flashcards from getTypicalFlashcards methods always
    * creates flashcards with today's date (based on system)
    */
    /*
    @Test
    public void toModelType_typicalFlashcardsFile_success() throws Exception {
        JsonSerializableFlashcard dataFromFile = JsonUtil.readJsonFile(TYPICAL_FLASHCARDS_FILE,
                JsonSerializableFlashcard.class).get();
        StudyBuddyPro studyBuddyProFromFile = dataFromFile.toModelType(new StudyBuddyPro());
        StudyBuddyPro typicalFlashcardsStudyBuddyPro = TypicalFlashcards.getTypicalStudyBuddyPro();
        assertEquals(studyBuddyProFromFile, typicalFlashcardsStudyBuddyPro);
    }
    */

    @Test
    public void toModelType_invalidFlashcardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashcard dataFromFile = JsonUtil.readJsonFile(INVALID_FLASHCARD_FILE,
                JsonSerializableFlashcard.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelType(new StudyBuddyPro()));
    }

    @Test
    public void toModelType_duplicateFlashcards_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashcard dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FLASHCARD_FILE,
                JsonSerializableFlashcard.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFlashcard.MESSAGE_DUPLICATE_FLASHCARD, () ->
                dataFromFile.toModelType(new StudyBuddyPro()));
    }

}
