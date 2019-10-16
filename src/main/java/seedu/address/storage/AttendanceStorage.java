package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Attendance;

public interface AttendanceStorage {

    /**
     * Returns the file path of the Attendance data file.
     */
    Path getAddressBookFilePath();

    Optional<Attendance> readAttendance() throws DataConversionException, IOException;

    Optional<Attendance> readAttendance(Path filePath) throws DataConversionException, IOException;

    void saveAttendance(Attendance attendance) throws IOException;

    void saveAttendance(Attendance attendance, Path filePath) throws IOException;
}
