package seedu.jarvis.storage.planner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.planner.Planner;

/**
 * Represents a storage for {@link Planner}.
 */
public interface PlannerStorage {

    /**
     * Gets the file path of the data file for {@code Planner}.
     *
     * @return File path of the data file for {@code Planner}.
     */
    Path getPlannerFilePath();

    /**
     * Returns {@code Planner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code Planner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Planner> readPlanner() throws DataConversionException, IOException;

    /**
     * Returns {@code Planner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code Planner} data.
     * @return {@code Planner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Planner> readPlanner(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link Planner} to the storage.
     *
     * @param planner {@code Planner} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    void savePlanner(Planner planner) throws IOException;

    /**
     * Saves the given {@link Planner} to the storage.
     *
     * @param planner {@code Planner} to be saved, which cannot be null.
     * @param filePath {@code Path} to read {@code Planner} data.
     * @throws IOException If there was any problem writing to the file.
     */
    void savePlanner(Planner planner, Path filePath) throws IOException;

}
