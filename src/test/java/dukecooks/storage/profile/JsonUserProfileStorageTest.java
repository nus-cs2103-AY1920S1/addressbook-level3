package dukecooks.storage.profile;

import static dukecooks.testutil.profile.TypicalProfiles.BENSON;
import static dukecooks.testutil.profile.TypicalProfiles.getTypicalProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.UserProfile;
import dukecooks.testutil.Assert;

public class JsonUserProfileStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonUserProfileStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserProfile_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readUserProfile(null));
    }

    private java.util.Optional<ReadOnlyUserProfile> readUserProfile(String filePath) throws Exception {
        return new JsonUserProfileStorage(Paths.get(filePath)).readUserProfile(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUserProfile("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readUserProfile("notJsonFormatUserProfile.json"));
    }

    @Test
    public void readUserProfile_invalidPersonUserProfile_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readUserProfile("invalidPersonUserProfile.json"));
    }

    @Test
    public void readAndSaveUserProfile_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempUserProfile.json");
        UserProfile original = getTypicalProfiles();
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        // Save in new file and read back
        jsonUserProfileStorage.saveUserProfile(original, filePath);
        ReadOnlyUserProfile readBack = jsonUserProfileStorage.readUserProfile(filePath).get();
        assertEquals(original, new UserProfile(readBack));

        // Modify data, overwrite exiting file, and read back
        original.resetData(new UserProfile());
        jsonUserProfileStorage.saveUserProfile(original, filePath);
        readBack = jsonUserProfileStorage.readUserProfile(filePath).get();
        assertEquals(original, new UserProfile(readBack));

        // Save and read without specifying file path
        original.addPerson(BENSON);
        jsonUserProfileStorage.saveUserProfile(original); // file path not specified
        readBack = jsonUserProfileStorage.readUserProfile().get(); // file path not specified
        assertEquals(original, new UserProfile(readBack));

    }

    @Test
    public void saveUserProfile_nullUserProfile_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveUserProfile(null, "SomeFile.json"));
    }

    /**
     * Saves {@code userProfile} at the specified {@code filePath}.
     */
    private void saveUserProfile(ReadOnlyUserProfile userProfile, String filePath) {
        try {
            new JsonUserProfileStorage(Paths.get(filePath))
                    .saveUserProfile(userProfile, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveUserProfile_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveUserProfile(new UserProfile(), null));
    }
}
