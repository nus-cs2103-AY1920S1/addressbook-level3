package seedu.jarvis.storage.course;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.course.CoursePlanner;

/**
 * Represents a storage for {@link CoursePlanner}.
 */
public interface CoursePlannerStorage {
    /**
     * Gets the file path of the data file for {@code CoursePlanner}.
     *
     * @return File path of the data file for {@code CoursePlanner}.
     */
    Path getCoursePlannerFilePath();

    /**
     * Returns {@code CoursePlanner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code CoursePlanner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CoursePlanner> readCoursePlanner() throws DataConversionException, IOException;

    /**
     * Returns {@code CoursePlanner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code CoursePlanner} data.
     * @return {@code CoursePlanner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CoursePlanner> readCoursePlanner(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link CoursePlanner} to the storage.
     *
     * @param coursePlanner {@code CoursePlanner} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveCoursePlanner(CoursePlanner coursePlanner) throws IOException;

    /**
     * Saves the given {@link CoursePlanner} to the storage.
     *
     * @param coursePlanner {@code CoursePlanner} to be saved, which cannot be null.
     * @param filePath {@code Path} to read {@code CoursePlanner} data.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveCoursePlanner(CoursePlanner coursePlanner, Path filePath) throws IOException;
}
