package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MarkStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getMarkFilePath();

    @Override
    Optional<ReadOnlyMark> readMark() throws DataConversionException, IOException;

    @Override
    void saveMark(ReadOnlyMark mark) throws IOException;

}
