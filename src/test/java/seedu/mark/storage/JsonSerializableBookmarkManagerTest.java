package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.commons.util.JsonUtil;
import seedu.mark.model.BookmarkManager;
import seedu.mark.testutil.TypicalBookmarks;

public class JsonSerializableBookmarkManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBookmarkManagerTest");
    private static final Path TYPICAL_BOOKMARKS_FILE = TEST_DATA_FOLDER.resolve("typicalBookmarksBookmarkManager.json");
    private static final Path INVALID_BOOKMARK_FILE = TEST_DATA_FOLDER.resolve("invalidBookmarkBookmarkManager.json");
    private static final Path DUPLICATE_BOOKMARK_FILE = TEST_DATA_FOLDER.resolve("duplicateBookmarkBookmarkManager.json");

    @Test
    public void toModelType_typicalBookmarksFile_success() throws Exception {
        JsonSerializableBookmarkManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOOKMARKS_FILE,
                JsonSerializableBookmarkManager.class).get();
        BookmarkManager bookmarkManagerFromFile = dataFromFile.toModelType();
        BookmarkManager typicalBookmarksBookmarkManager = TypicalBookmarks.getTypicalBookmarkManager();
        assertEquals(bookmarkManagerFromFile, typicalBookmarksBookmarkManager);
    }

    @Test
    public void toModelType_invalidBookmarkFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBookmarkManager dataFromFile = JsonUtil.readJsonFile(INVALID_BOOKMARK_FILE,
                JsonSerializableBookmarkManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBookmarks_throwsIllegalValueException() throws Exception {
        JsonSerializableBookmarkManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BOOKMARK_FILE,
                JsonSerializableBookmarkManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBookmarkManager.MESSAGE_DUPLICATE_BOOKMARK,
                dataFromFile::toModelType);
    }

}
