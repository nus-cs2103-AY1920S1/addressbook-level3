package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.commons.util.JsonUtil;
import seedu.mark.model.Mark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.testutil.TypicalBookmarks;

public class JsonSerializableMarkTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMarkTest");
    private static final Path TYPICAL_BOOKMARKS_FILE = TEST_DATA_FOLDER.resolve("typicalBookmarksMark.json");
    private static final Path INVALID_BOOKMARK_FILE = TEST_DATA_FOLDER.resolve("invalidBookmarkMark.json");
    private static final Path DUPLICATE_BOOKMARK_FILE = TEST_DATA_FOLDER.resolve("duplicateBookmarkMark.json");
    private static final Path DUPLICATE_FOLDER_IN_STRUCTURE_FILE =
            TEST_DATA_FOLDER.resolve("duplicateFolderInFolderStructureMark.json");
    private static final Path NO_ROOT_FOLDER_IN_STRUCTURE_FILE =
            TEST_DATA_FOLDER.resolve("noRootFolderInStructureMark.json");


    @Test
    public void toModelType_typicalBookmarksFile_success() throws Exception {
        FolderStructure family = new FolderStructure("family", new ArrayList<>());
        FolderStructure contacts = new FolderStructure("contacts", List.of(family));
        FolderStructure friends = new FolderStructure("friends", new ArrayList<>());
        FolderStructure f = new FolderStructure(Folder.DEFAULT_FOLDER_NAME, List.of(contacts, friends));
        assertEquals(f, f.clone());
        JsonSerializableMark dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOOKMARKS_FILE,
                JsonSerializableMark.class).get();
        Mark markFromFile = dataFromFile.toModelType();
        Mark typicalBookmarksMark = TypicalBookmarks.getTypicalMark();
        assertEquals(markFromFile, typicalBookmarksMark);
    }

    @Test
    public void toModelType_invalidBookmarkFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMark dataFromFile = JsonUtil.readJsonFile(INVALID_BOOKMARK_FILE,
                JsonSerializableMark.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBookmarks_throwsIllegalValueException() throws Exception {
        JsonSerializableMark dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BOOKMARK_FILE,
                JsonSerializableMark.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMark.MESSAGE_DUPLICATE_BOOKMARK,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateFolderInStrucutre_throwsIllegalValueException() throws Exception {
        JsonSerializableMark dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FOLDER_IN_STRUCTURE_FILE,
                JsonSerializableMark.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMark.MESSAGE_DUPLICATE_FOLDER,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_noRootFolderInStructure_throwsIllegalValueException() throws Exception {
        JsonSerializableMark dataFromFile = JsonUtil.readJsonFile(NO_ROOT_FOLDER_IN_STRUCTURE_FILE,
                JsonSerializableMark.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMark.MESSAGE_NO_ROOT_FOLDER,
                dataFromFile::toModelType);
    }
}
