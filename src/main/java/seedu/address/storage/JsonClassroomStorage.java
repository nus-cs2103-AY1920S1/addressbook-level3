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
import seedu.address.model.classroom.ReadOnlyClassroom;

/**
 * A class to access Classroom data stored as a json file on the hard disk.
 */
public class JsonClassroomStorage implements ClassroomStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonClassroomStorage.class);

    private Path filePath;

    public JsonClassroomStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getClassroomFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyClassroom> readClassroom() throws DataConversionException {
        return readClassroom(filePath);
    }

    /**
     * Similar to {@link #readClassroom()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyClassroom> readClassroom(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableClassroom> jsonClassroom = JsonUtil.readJsonFile(
                filePath, JsonSerializableClassroom.class);
        if (!jsonClassroom.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonClassroom.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveClassroom(ReadOnlyClassroom classroom) throws IOException {
        saveClassroom(classroom, filePath);
    }

    /**
     * Similar to {@link #saveClassroom(ReadOnlyClassroom)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveClassroom(ReadOnlyClassroom classroom, Path filePath) throws IOException {
        requireNonNull(classroom);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableClassroom(classroom), filePath);
    }

}
