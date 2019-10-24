package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.module.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class JsonModuleBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonModuleBookStorageTest");
    private static final ArchivedModule ARCHIVED_MODULE = new ArchivedModuleBuilder().build();
    private static final ArchivedModuleList ARCHIVED_MODULE_LIST = new ArchivedModuleList();

    @TempDir
    public Path testFolder;

    @BeforeAll
    public static void beforeAll() {
        ARCHIVED_MODULE_LIST.add(ARCHIVED_MODULE);
    }

    @Test
    public void readModuleBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModuleBook(null));
    }

    private ReadOnlyModuleBook readModuleBook(String filePath) {
        return new JsonModuleBookStorage(Paths.get(filePath)).readModuleBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_returnNewModuleBook() {
        assertEquals(readModuleBook("NonExistentFile.json"), new ModuleBook(ARCHIVED_MODULE_LIST));
    }

    @Test
    public void read_notJsonFormat_returnNewModuleBook() {
        assertEquals(readModuleBook("notJsonFormatModuleBook.json"), new ModuleBook(ARCHIVED_MODULE_LIST));
    }

    @Test
    public void readModuleBook_invalidModuleModuleBook_returnNewModuleBook() {
        assertEquals(readModuleBook("invalidModuleModuleBook.json"), new ModuleBook(ARCHIVED_MODULE_LIST));
    }

    @Test
    public void readAndSaveModuleBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempModuleBook.json");
        ModuleBook original = new ModuleBook(ARCHIVED_MODULE_LIST);
        JsonModuleBookStorage jsonModuleBookStorage = new JsonModuleBookStorage(filePath);

        // Save in new file and read back
        jsonModuleBookStorage.saveModuleBook(original, filePath);
        ReadOnlyModuleBook readBack = jsonModuleBookStorage.readModuleBook(filePath);
        assertEquals(original, new ModuleBook(readBack));

        // Modify data, overwrite exiting file, and read back
        TrackedModule trackedModule = new TrackedModule(ARCHIVED_MODULE);
        original.addModule(trackedModule);
        jsonModuleBookStorage.saveModuleBook(original, filePath);
        readBack = jsonModuleBookStorage.readModuleBook(filePath);
        assertEquals(original, new ModuleBook(readBack));

        // Save and read without specifying file path
        original.removeModule(trackedModule);
        jsonModuleBookStorage.saveModuleBook(original); // file path not specified
        readBack = jsonModuleBookStorage.readModuleBook(); // file path not specified
        assertEquals(original, new ModuleBook(readBack));
    }

    @Test
    public void saveModuleBook_nullModuleBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModuleBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code moduleBook} at the specified {@code filePath}.
     */
    private void saveModuleBook(ReadOnlyModuleBook moduleBook, String filePath) {
        try {
            new JsonModuleBookStorage(Paths.get(filePath))
                    .saveModuleBook(moduleBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveModuleBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModuleBook(new ModuleBook(), null));
    }
}
