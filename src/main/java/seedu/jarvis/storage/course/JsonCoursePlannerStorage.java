package seedu.jarvis.storage.course;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.commons.util.FileUtil;
import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.course.CoursePlanner;

/**
 * A class to access {@code CoursePlanner} data stored as a text file on the hard disk.
 */
public class JsonCoursePlannerStorage implements CoursePlannerStorage {

    private Path filepath;

    public JsonCoursePlannerStorage(Path filepath) {
        this.filepath = filepath;
    }

    /**
     * Gets the file path of the data file for {@code CoursePlanner}.
     *
     * @return File path of the data file for {@code CoursePlanner}.
     */
    @Override
    public Path getCoursePlannerFilePath() {
        return filepath;
    }

    /**
     * Returns {@code CoursePlanner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code CoursePlanner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CoursePlanner> readCoursePlanner() throws DataConversionException, IOException {
        return readCoursePlanner(filepath);
    }

    /**
     * Returns {@code CoursePlanner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code CoursePlanner} data.
     * @return {@code CoursePlanner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CoursePlanner> readCoursePlanner(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);
        Optional<JsonSerializableCoursePlanner> jsonCoursePlanner = JsonUtil.readJsonFile(filePath,
                JsonSerializableCoursePlanner.class);
        if (jsonCoursePlanner.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCoursePlanner.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Saves the given {@link CoursePlanner} to the storage.
     *
     * @param coursePlanner {@code CoursePlanner} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCoursePlanner(CoursePlanner coursePlanner) throws IOException {
        saveCoursePlanner(coursePlanner, filepath);
    }

    /**
     * Saves the given {@link CoursePlanner} to the storage.
     *
     * @param coursePlanner {@code CoursePlanner} to be saved, which cannot be null.
     * @param filePath      {@code Path} to read {@code CoursePlanner} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCoursePlanner(CoursePlanner coursePlanner, Path filePath) throws IOException {
        requireAllNonNull(coursePlanner, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCoursePlanner(coursePlanner), filePath);
    }
}
