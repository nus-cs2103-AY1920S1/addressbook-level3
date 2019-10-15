package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;

/**
 * Represents a storage for {@link ProjectDashboard}.
 */
public interface ProjectDashboardStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProjectDashboardFilePath();

    /**
     * Returns ProjectDashboard data as a {@link ReadOnlyProjectDashboard}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyProjectDashboard> readProjectDashBoard() throws DataConversionException, IOException;

    /**
     * @see #getProjectDashboardFilePath()
     */
    Optional<ReadOnlyProjectDashboard> readProjectDashBoard(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyProjectDashboard} to the storage.
     * @param projectDashboard cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard) throws IOException;

    /**
     * @see #saveProjectDashboard(ReadOnlyProjectDashboard)
     */
    void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard, Path filePath) throws IOException;

}
