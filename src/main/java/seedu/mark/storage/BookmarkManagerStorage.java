package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.BookmarkManager;
import seedu.mark.model.ReadOnlyBookmarkManager;

/**
 * Represents a storage for {@link BookmarkManager}.
 */
public interface BookmarkManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBookmarkManagerFilePath();

    /**
     * Returns BookmarkManager data as a {@link ReadOnlyBookmarkManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBookmarkManager> readBookmarkManager() throws DataConversionException, IOException;

    /**
     * @see #getBookmarkManagerFilePath()
     */
    Optional<ReadOnlyBookmarkManager> readBookmarkManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBookmarkManager} to the storage.
     * @param bookmarkManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager) throws IOException;

    /**
     * @see #saveBookmarkManager(ReadOnlyBookmarkManager)
     */
    void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager, Path filePath) throws IOException;

}
