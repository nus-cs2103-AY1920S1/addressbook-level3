package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends BookmarkManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getBookmarkManagerFilePath();

    @Override
    Optional<ReadOnlyBookmarkManager> readBookmarkManager() throws DataConversionException, IOException;

    @Override
    void saveBookmarkManager(ReadOnlyBookmarkManager bookmarkManager) throws IOException;

}
