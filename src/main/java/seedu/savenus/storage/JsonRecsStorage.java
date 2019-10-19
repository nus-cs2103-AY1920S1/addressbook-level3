package seedu.savenus.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.FileUtil;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.recommend.UserRecommendations;

/**
 * A class to access Recommendation data stored as a json file on the hard disk.
 */
public class JsonRecsStorage implements RecsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMenuStorage.class);

    private Path filePath;

    public JsonRecsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRecsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserRecommendations> readRecs() throws DataConversionException {
        return readRecs(filePath);
    }

    /**
     * Similar to {@link #readRecs()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UserRecommendations> readRecs(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRecs> jsonRecs = JsonUtil.readJsonFile(
                filePath, JsonSerializableRecs.class);
        if (!jsonRecs.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRecs.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRecs(UserRecommendations recs) throws IOException {
        saveRecs(recs, filePath);
    }

    /**
     * Similar to {@link #saveRecs(UserRecommendations)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRecs(UserRecommendations recs, Path filePath) throws IOException {
        requireNonNull(recs);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRecs(recs), filePath);
    }

}
