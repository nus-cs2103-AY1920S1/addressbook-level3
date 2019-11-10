package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

    @Test
    public void readModulesInfo_fileInOrder_successfullyRead() throws IOException {
        ModulesInfo expected = getTypicalModulesInfo();
        ModulesInfo actual = readModulesInfo("JsonModulesInfoStorageTest/TypicalModulesInfo.json").get();

        assertModulesInfoEqual(expected, actual);
    }

    @Test
    public void readModulesInfo_extraValuesInFile_extraValuesIgnored() throws IOException {
        ModulesInfo expected = getTypicalModulesInfo();
        ModulesInfo actual = readModulesInfo("JsonModulesInfoStorageTest/ExtraValuesModulesInfo.json").get();

        assertModulesInfoEqual(expected, actual);
    }

    @Test
    public void readModulesInfo_valuesMissingFromFile_defaultValuesUsed() throws IOException {
        ModulesInfo actual = readModulesInfo("JsonModulesInfoStorageTest/EmptyModulesInfo.json").get();
        assertEquals(new ModulesInfo(), actual);
    }

    /**
     * Tests whether two ModulesInfo objects can be considered equal for the purpose of testing. They are considered
     * equal if their lists of module info strings are equal position-wise.
     * @param expected expected String list
     * @param actual actual String list
     */
    private void assertModulesInfoEqual(ModulesInfo expected, ModulesInfo actual) {
        List<String> expectedStrings = expected.getModuleCodeStrings();
        List<String> actualStrings = actual.getModuleCodeStrings();
        boolean result = true;
        try {
            for (int i = 0; i < expectedStrings.size(); i++) {
                String info1 = expectedStrings.get(i);
                String info2 = actualStrings.get(i);
                if (!info1.equals(info2)) {
                    result = false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            result = false;
        }
        assertTrue(result);
    }

}
