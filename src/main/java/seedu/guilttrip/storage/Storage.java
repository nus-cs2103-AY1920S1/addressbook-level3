package seedu.guilttrip.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.guilttrip.commons.exceptions.DataConversionException;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.ReadOnlyUserPrefs;
import seedu.guilttrip.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GuiltTripStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getGuiltTripFilePath();

    @Override
    Optional<ReadOnlyGuiltTrip> readGuiltTrip() throws DataConversionException, IOException;

    @Override
    void saveGuiltTrip(ReadOnlyGuiltTrip guiltTrip) throws IOException;

}
