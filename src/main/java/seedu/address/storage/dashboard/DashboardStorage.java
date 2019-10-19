package seedu.address.storage.dashboard;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.dashboard.ReadOnlyDashboard;
import seedu.address.model.diary.DiaryRecords;

/**
 * Represents a storage for {@link DiaryRecords}.
 */
public interface DashboardStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDashboardFilePath();

    /**
     * Returns DashboardRecords data as a {@link ReadOnlyDashboard}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDashboard> readDashboard() throws DataConversionException, IOException;

    /**
     * @see #getDashboardFilePath()
     */
    Optional<ReadOnlyDashboard> readDashboard(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDashboard} to the storage.
     * @param dashboard cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDashboard(ReadOnlyDashboard dashboard) throws IOException;

    /**
     * @see #saveDashboard(ReadOnlyDashboard)
     */
    void saveDashboard(ReadOnlyDashboard dashboard, Path filePath) throws IOException;

}
