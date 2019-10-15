package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.profile.ReadOnlyDukeCooks;
import seedu.address.profile.ReadOnlyHealthRecords;
import seedu.address.profile.ReadOnlyUserPrefs;
import seedu.address.profile.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends DukeCooksStorage, HealthRecordsStorage, UserPrefsStorage {

    // ================ UserPrefs methods ==============================

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ================ DukeCooks methods ==============================

    @Override
    Path getDukeCooksFilePath();

    @Override
    Optional<ReadOnlyDukeCooks> readDukeCooks() throws DataConversionException, IOException;

    @Override
    void saveDukeCooks(ReadOnlyDukeCooks dukeCooks) throws IOException;

    // ================ Health Records methods ==============================

    @Override
    Path getHealthRecordsFilePath();

    @Override
    Optional<ReadOnlyHealthRecords> readHealthRecords() throws DataConversionException, IOException;

    @Override
    void saveHealthRecords(ReadOnlyHealthRecords healthRecords) throws IOException;

}
