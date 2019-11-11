package seedu.eatme.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.commons.exceptions.IllegalValueException;
import seedu.eatme.commons.util.FileUtil;
import seedu.eatme.commons.util.JsonUtil;
import seedu.eatme.model.ReadOnlyFeedList;

/**
 * A class to access FeedList data stored as a json file on the hard disk.
 */
public class JsonFeedListStorage implements FeedListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFeedListStorage.class);

    private Path filePath;

    public JsonFeedListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFeedListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFeedList> readFeedList() throws DataConversionException {
        return readFeedList(filePath);
    }

    /**
     * Similar to {@link #readFeedList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFeedList> readFeedList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFeedList> jsonFeedList = JsonUtil.readJsonFile(
                filePath, JsonSerializableFeedList.class);
        if (jsonFeedList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFeedList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFeedList(ReadOnlyFeedList feedList) throws IOException {
        saveFeedList(feedList, filePath);
    }

    /**
     * Similar to {@link #saveFeedList(ReadOnlyFeedList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFeedList(ReadOnlyFeedList feedList, Path filePath) throws IOException {
        requireNonNull(feedList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFeedList(feedList), filePath);
    }
}
