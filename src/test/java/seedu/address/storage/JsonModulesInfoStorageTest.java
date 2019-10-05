package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModulesInfo;

public class JsonModulesInfoStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonModulesInfoStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readModulesInfo_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModulesInfo(null));
    }

    private Optional<ModulesInfo> readModulesInfo(String modulesInfoFileInTestDataFolder)
            throws DataConversionException {
        Path prefsFilePath = addToTestDataPathIfNotNull(modulesInfoFileInTestDataFolder);
        return new JsonModulesInfoStorage(prefsFilePath).readModulesInfo(prefsFilePath);
    }

    @Test
    public void readModulesInfo_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readModulesInfo("NonExistentFile.json").isPresent());
    }

    @Test
    public void readModulesInfo_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readModulesInfo("NotJsonFormatModulesInfo.json"));
    }

    private Path addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userPrefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readModulesInfo_fileInOrder_successfullyRead() throws DataConversionException {
        ModulesInfo expected = getTypicalModulesInfo();
        ModulesInfo actual = readModulesInfo("TypicalModulesInfo.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readModulesInfo_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        ModulesInfo actual = readModulesInfo("EmptyModulesInfo.json").get();
        assertEquals(new ModulesInfo(), actual);
    }

    @Test
    public void readModulesInfo_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        ModulesInfo expected = getTypicalModulesInfo();
        ModulesInfo actual = readModulesInfo("ExtraValuesModulesInfo.json").get();

        assertEquals(expected, actual);
    }
}
