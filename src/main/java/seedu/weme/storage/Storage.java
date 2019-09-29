package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MemeBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getMemeBookFilePath();

    @Override
    Optional<ReadOnlyMemeBook> readMemeBook() throws DataConversionException, IOException;

    @Override
    void saveMemeBook(ReadOnlyMemeBook memeBook) throws IOException;

}
