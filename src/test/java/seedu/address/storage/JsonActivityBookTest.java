package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ActivityBook;
import seedu.address.testutil.TypicalActivities;

public class JsonActivityBookTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonActivityBookTest");
    private static final Path TYPICAL_ACTIVITY_BOOK_FILE = TEST_DATA_FOLDER
            .resolve("typicalActivityBook.json");
    private static final Path INVALID_ACTIVITY_BOOK_FILE = TEST_DATA_FOLDER
            .resolve("invalidActivityBook.json");

    @Test
    public void toModelType_typicalActivityBookFile_success() throws Exception {
        JsonActivityBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ACTIVITY_BOOK_FILE,
                JsonActivityBook.class).get();
        ActivityBook activityBookFromFile = dataFromFile.toModelType();
        ActivityBook typicalActivityBook = TypicalActivities.getTypicalActivityBook();

        assertEquals(activityBookFromFile, typicalActivityBook);
    }

    @Test
    public void toModelType_invalidActivityBookFile_throwsIllegalValueException() throws Exception {
        JsonActivityBook dataFromFile = JsonUtil.readJsonFile(INVALID_ACTIVITY_BOOK_FILE,
                JsonActivityBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
