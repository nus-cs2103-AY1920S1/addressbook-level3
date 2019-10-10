package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private Path filePath;
    private String password;

    public JsonUserPrefsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public JsonUserPrefsStorage(Path filePath, String password) {
        this.filePath = filePath;
        this.password = password;
    }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(Path prefsFilePath) throws DataConversionException {
        if (password == null) {
            return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
        } else {
            return JsonUtil.readEncryptedJsonFile(prefsFilePath, UserPrefs.class, password);
        }
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        if (password == null) {
            JsonUtil.saveJsonFile(userPrefs, filePath);
        } else {
            JsonUtil.saveEncryptedJsonFile(userPrefs, filePath, password);
        }
    }

}
