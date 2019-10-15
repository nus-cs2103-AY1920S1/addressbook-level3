package seedu.weme.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.commons.util.FileUtil;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.LikeDataImpl;

/**
 * A class to access MemeBook data stored as a json file on the hard disk.
 */
public class JsonLikeDataStorage implements LikeDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLikeDataStorage.class);

    private Path filePath;

    public JsonLikeDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getLikeDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<LikeData> readLikeData() throws DataConversionException {
        return readLikeData(filePath);
    }

    /**
     * Similar to {@link #readLikeData()}.
     *
     * @param likeDataFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<LikeData> readLikeData(Path likeDataFilePath) throws DataConversionException {
        requireNonNull(likeDataFilePath);
        return JsonUtil.readJsonFile(likeDataFilePath, LikeData.class);
    }

    @Override
    public void saveLikeData(LikeDataImpl likeData) throws IOException {
        saveLikeData(likeData, filePath);
    }

    /**
     * Similar to {@link #saveLikeData(LikeDataImpl)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveLikeData(LikeDataImpl likeData, Path filePath) throws IOException {
        requireNonNull(likeData);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(likeData, filePath);
    }

}
