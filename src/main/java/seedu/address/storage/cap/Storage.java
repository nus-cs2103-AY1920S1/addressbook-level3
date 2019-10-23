package seedu.address.storage.cap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.ReadOnlyUserPrefs;


/**
 * API of the Storage component
 */
public interface Storage extends CapStorage, UserPrefsStorage {

    @Override
    Optional<CapUserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCapLogFilePath();

    @Override
    Optional<ReadOnlyCapLog> readCapLog() throws DataConversionException, IOException;

    @Override
    void saveCapLog(ReadOnlyCapLog addressBook) throws IOException;

}
