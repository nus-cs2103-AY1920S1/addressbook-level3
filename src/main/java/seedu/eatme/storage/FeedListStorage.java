package seedu.eatme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.model.ReadOnlyFeedList;

/**
 * Represents a storage for {@link seedu.eatme.model.FeedList}.
 */
public interface FeedListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFeedListFilePath();

    /**
     * Returns FeedList data as a {@link ReadOnlyFeedList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFeedList> readFeedList() throws DataConversionException, IOException;

    /**
     * @see #getFeedListFilePath()
     */
    Optional<ReadOnlyFeedList> readFeedList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFeedList} to the storage.
     *
     * @param feedList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFeedList(ReadOnlyFeedList feedList) throws IOException;

    /**
     * @see #saveFeedList(ReadOnlyFeedList)
     */
    void saveFeedList(ReadOnlyFeedList feedList, Path filePath) throws IOException;

}
