package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Attendance;

/**
 * A class to access Attendance data stored as a json file on the hard disk.
 */
public class JsonAttendanceStorage implements AttendanceStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAthletickStorage.class);

    private Path path;

    public JsonAttendanceStorage(Path path) {
        this.path = path;
    }

    @Override
    public Path getAthletickFilePath() {
        return path;
    }

    @Override
    public Optional<Attendance> readAttendance() throws DataConversionException {
        return readAttendance(this.path);
    }

    @Override
    public Optional<Attendance> readAttendance(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonAttendance> jsonAttendance = JsonUtil.readJsonFile(filePath, JsonAttendance.class);
        if (!jsonAttendance.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAttendance.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
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
        JsonUtil.saveJsonFile(new JsonAttendance(attendance), filePath);
    }
}
