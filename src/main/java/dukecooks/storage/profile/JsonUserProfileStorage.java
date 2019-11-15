package dukecooks.storage.profile;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.commons.util.FileUtil;
import dukecooks.commons.util.JsonUtil;
import dukecooks.model.profile.ReadOnlyUserProfile;

/**
 * A class to access UserProfile data stored as a json file on the hard disk.
 */
public class JsonUserProfileStorage implements UserProfileStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserProfileStorage.class);

    private Path filePath;

    public JsonUserProfileStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserProfileFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException {
        return readUserProfile(filePath);
    }

    /**
     * Similar to {@link #readUserProfile()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserProfile> readUserProfile(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableUserProfile> jsonUserProfile = JsonUtil.readJsonFile(
                filePath, JsonSerializableUserProfile.class);
        if (!jsonUserProfile.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonUserProfile.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile userProfile) throws IOException {
        saveUserProfile(userProfile, filePath);
    }

    /**
     * Similar to {@link #saveUserProfile(ReadOnlyUserProfile)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveUserProfile(ReadOnlyUserProfile userProfile, Path filePath) throws IOException {
        requireNonNull(userProfile);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableUserProfile(userProfile), filePath);
    }

}
