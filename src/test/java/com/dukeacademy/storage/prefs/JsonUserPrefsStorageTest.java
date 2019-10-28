package com.dukeacademy.storage.prefs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.model.prefs.UserPrefs;

class JsonUserPrefsStorageTest {
    private final Path testDataRootPath = Paths.get("src", "test", "data", "JsonUserPrefsStorageTest");

    @Test
    void getUserPrefsFilePath() {
        Path testPath = Paths.get("test", "test", "test");
        JsonUserPrefsStorage storage = new JsonUserPrefsStorage(testPath);
        assertEquals(testPath, storage.getUserPrefsFilePath());
    }

    @Test
    void readUserPrefs() throws DataConversionException {
        UserPrefs expectedPrefs = new UserPrefs(Paths.get("development"));

        JsonUserPrefsStorage storage = new JsonUserPrefsStorage(testDataRootPath.resolve("EmptyUserPrefs.json"));
        assertThrows(DataConversionException.class, storage::readUserPrefs);

        JsonUserPrefsStorage storage1 = new JsonUserPrefsStorage(testDataRootPath.resolve("ExtraValuesUserPref.json"));
        Optional<UserPrefs> prefs = storage1.readUserPrefs();
        assertTrue(prefs.isPresent());
        assertEquals(expectedPrefs, prefs.get());

        JsonUserPrefsStorage storage2 = new JsonUserPrefsStorage(testDataRootPath
                .resolve("NotJsonFormatUserPrefs.json"));
        assertThrows(DataConversionException.class, storage2::readUserPrefs);

        JsonUserPrefsStorage storage3 = new JsonUserPrefsStorage(testDataRootPath.resolve("TypicalUserPref.json"));
        Optional<UserPrefs> prefs1 = storage3.readUserPrefs();
        assertTrue(prefs1.isPresent());
        assertEquals(expectedPrefs, prefs1.get());

        JsonUserPrefsStorage storage4 = new JsonUserPrefsStorage(testDataRootPath.resolve("a!@ewt$"));
        Optional<UserPrefs> prefs2 = storage4.readUserPrefs();
        assertFalse(prefs2.isPresent());
    }


    @Test
    void testReadUserPrefs() throws DataConversionException {
        JsonUserPrefsStorage storage = new JsonUserPrefsStorage(Paths.get("test"));
        Optional<UserPrefs> prefs = storage.readUserPrefs(testDataRootPath.resolve("TypicalUserPref.json"));
        assertTrue(prefs.isPresent());
        assertEquals(new UserPrefs(Paths.get("development")), prefs.get());
    }
}
