package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Attendance;
import seedu.address.model.ReadOnlyAthletick;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AthletickStorage, PerformanceStorage, AttendanceStorage,
        UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAthletickFilePath();

    @Override
    Optional<ReadOnlyAthletick> readAthletick() throws DataConversionException, IOException;

    @Override
    void saveAthletick(ReadOnlyAthletick athletick) throws IOException;

    @Override
    Optional<ReadOnlyPerformance> readEvents(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveEvents(ReadOnlyPerformance events, Path filePath) throws IOException;

    @Override
    Optional<Attendance> readAttendance() throws DataConversionException, IOException;

    @Override
    void saveAttendance(Attendance attendance) throws IOException;

}
