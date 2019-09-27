package seedu.mark.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.commons.util.FileUtil;
import seedu.mark.commons.util.JsonUtil;
import seedu.mark.model.ReadOnlyBookmarkManager;

/**
 * A class to access BookmarkManager data stored as a json file on the hard disk.
 */
public class JsonBookmarkManagerStorage implements BookmarkManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBookmarkManagerStorage.class);

    private Path filePath;

    public JsonBookmarkManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBookmarkManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBookmarkManager> readBookmarkManager() throws DataConversionException {
        return readBookmarkManager(filePath);
    }

    /**
     * Similar to {@link #readBookmarkManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBookmarkManager> readBookmarkManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBookmarkManager> jsonBookmarkManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableBookmarkManager.class);
        if (!jsonBookmarkManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBookmarkManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager) throws IOException {
        saveBookmarkManager(bookmarkManager, filePath);
    }

    /**
     * Similar to {@link #saveBookmarkManager(ReadOnlyBookmarkManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager, Path filePath) throws IOException {
        requireNonNull(bookmarkManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBookmarkManager(bookmarkManager), filePath);
    }

}
