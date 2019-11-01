package seedu.savenus.storage.alias;

import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.alias.AliasList;

public class JsonAliasListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonAliasListStorageTest");

    @TempDir
    public Path testFolder;

    private java.util.Optional<AliasList> readList(String filePath) throws Exception {
        return new JsonAliasListStorage(Paths.get(filePath))
                .readList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readAliasList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readList(null));
        assertThrows(NullPointerException.class, () -> new JsonAliasListStorage(Paths.get(null))
                .readList());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(
                DataConversionException.class, () -> readList("invalidJson.json"));
    }

    @Test
    public void read_illegalValues_exceptionThrown() {
        assertThrows(
                DataConversionException.class, () -> readList("invalidAliasList.json")
        );
    }
}
