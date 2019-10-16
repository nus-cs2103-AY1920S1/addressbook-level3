package seedu.address.storage;


import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Attendance;

public class JsonAttendanceStorage implements AttendanceStorage {

    Path path;

    public JsonAttendanceStorage(Path path) {
        this.path = path;
    }

    @Override
    public Path getAddressBookFilePath() {
        return path;
    }

    @Override
    public Optional<Attendance> readAttendance() throws DataConversionException, IOException {
        return readAttendance(this.path);
    }

    @Override
    public Optional<Attendance> readAttendance(Path filePath) throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(filePath, Attendance.class);
    }

    @Override
    public void saveAttendance(Attendance attendance) throws IOException {
        saveAttendance(attendance, this.path);
    }

    @Override
    public void saveAttendance(Attendance attendance, Path filePath) throws IOException {
        requireNonNull(attendance);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(attendance, filePath);
    }
}
