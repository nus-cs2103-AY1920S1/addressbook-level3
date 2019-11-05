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
import seedu.address.model.ReadOnlyTutorAid;

/**
 * A class to access TutorAid data stored as a json file on the hard disk.
 */
public class JsonTutorAidStorage implements TutorAidStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTutorAidStorage.class);

    private Path filePath;

    public JsonTutorAidStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTutorAidFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTutorAid> readTutorAid() throws DataConversionException {
        return readTutorAid(filePath);
    }

    /**
     * Similar to {@link #readTutorAid()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTutorAid> readTutorAid(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTutorAid> jsonTutorAid = JsonUtil.readJsonFile(
                filePath, JsonSerializableTutorAid.class);
        if (!jsonTutorAid.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTutorAid.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTutorAid(ReadOnlyTutorAid tutorAid) throws IOException {
        saveTutorAid(tutorAid, filePath);
    }

    /**
     * Similar to {@link #saveTutorAid(ReadOnlyTutorAid)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTutorAid(ReadOnlyTutorAid tutorAid, Path filePath) throws IOException {
        requireNonNull(tutorAid);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTutorAid(tutorAid), filePath);
    }

}
