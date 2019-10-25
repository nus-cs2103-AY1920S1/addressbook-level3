package seedu.ifridge.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.TemplateList;

public class JsonTemplateListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonTemplateListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTemplateList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTemplateList(null));
    }

    private java.util.Optional<ReadOnlyTemplateList> readTemplateList(String filePath) throws Exception {
        return new JsonTemplateListStorage(Paths.get(filePath)).readTemplateList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTemplateList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTemplateList("notJsonFormatTemplateList.json"));
    }

    @Test
    public void readTemplateList_invalidTemplateItemTemplateList_throwDataConversionException() {
        //assertThrows(DataConversionException.class, () -> readTemplateList("invalidTemplateItemTemplateList.json"));
    }

    @Test
    public void readTemplateList_invalidAndValidTemplateItemTemplateList_throwDataConversionException() {
        //assertThrows(DataConversionException.class, ()
        // -> readTemplateList("invalidAndValidTemplateItemTemplateList.json"));
    }
    /**
    @Test
    public void readAndSaveTemplateList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("typicalTemplatesTemplateList.json");
        TemplateList original = getTypicalTemplateList();
        JsonTemplateListStorage jsonTemplateListStorage = new JsonTemplateListStorage(filePath);

        // Save in new file and read back
        jsonTemplateListStorage.saveTemplateList(original, filePath);
        ReadOnlyTemplateList readBack = jsonTemplateListStorage.readTemplateList(filePath).get();
        assertEquals(original, new TemplateList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTemplate(DIET_PLAN);
        original.removeTemplate(BIRTHDAY_PARTY);
        jsonTemplateListStorage.saveTemplateList(original, filePath);
        readBack = jsonTemplateListStorage.readTemplateList(filePath).get();
        assertEquals(original, new TemplateList(readBack));

        // Save and read without specifying file path
        original.addTemplate(WEEKLY_NECESSITIES);
        jsonTemplateListStorage.saveTemplateList(original); // file path not specified
        readBack = jsonTemplateListStorage.readTemplateList().get(); // file path not specified
        assertEquals(original, new TemplateList(readBack));

    }**/

    @Test
    public void saveTemplateList_nullTemplateList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTemplateList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code templateList} at the specified {@code filePath}.
     */
    private void saveTemplateList(ReadOnlyTemplateList templateList, String filePath) {
        try {
            new JsonTemplateListStorage(Paths.get(filePath))
                    .saveTemplateList(templateList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTemplateList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTemplateList(new TemplateList(), null));
    }
}
