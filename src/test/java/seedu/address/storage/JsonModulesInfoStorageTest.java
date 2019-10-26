package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
            throws IOException {
        Path prefsFilePath = Paths.get(modulesInfoFileInTestDataFolder);
        return new JsonModulesInfoStorage(prefsFilePath).readModulesInfo(prefsFilePath);
    }

    @Test
    public void readModulesInfo_missingFile_emptyResult() throws IOException {
        assertFalse(readModulesInfo("JsonModulesInfoStorageTest/NonExistentFile.json").isPresent());
    }

    /*
    @Test
    public void readModulesInfo_fileInOrder_successfullyRead() throws IOException {
        ModulesInfo expected = getTypicalModulesInfo();
        ModulesInfo actual = readModulesInfo("JsonModulesInfoStorageTest/TypicalModulesInfo.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readModulesInfo_extraValuesInFile_extraValuesIgnored() throws IOException {
        ModulesInfo expected = getTypicalModulesInfo();
        ModulesInfo actual = readModulesInfo("JsonModulesInfoStorageTest/ExtraValuesModulesInfo.json").get();

        assertEquals(expected, actual);
    }
    */

    @Test
    public void readModulesInfo_valuesMissingFromFile_defaultValuesUsed() throws IOException {
        ModulesInfo actual = readModulesInfo("JsonModulesInfoStorageTest/EmptyModulesInfo.json").get();
        assertEquals(new ModulesInfo(), actual);
    }

}
