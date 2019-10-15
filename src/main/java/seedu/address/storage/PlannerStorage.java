package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Planner;
import seedu.address.model.ReadOnlyPlanner;

/**
 * Represents a storage for {@link Planner}.
 */
public interface PlannerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPlannerFilePath();

    /**
     * Returns Planner data as a {@link ReadOnlyPlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPlanner> readPlanner() throws DataConversionException, IOException;

    /**
     * @see #getPlannerFilePath()
     */
    Optional<ReadOnlyPlanner> readPlanner(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPlanner} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePlanner(ReadOnlyPlanner addressBook) throws IOException;

    /**
     * @see #savePlanner(ReadOnlyPlanner)
     */
    void savePlanner(ReadOnlyPlanner planner, Path filePath) throws IOException;

}
