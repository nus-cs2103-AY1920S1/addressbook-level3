package seedu.planner.storage.activity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.activity.Activity;
//@@author OneArmyj
/**
 * Represents a storage for {@link Activity}.
 */
public interface ActivityStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getActivityFilePath();

    //@@author ernestyyh
    /**
     * Sets the file path of the data file.
     */
    void setActivityFilePath(Path activityFilePath);

    //@@author OneArmyj
    /**
     * Returns Activity data as a {@link ReadOnlyActivity}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyActivity> readActivity() throws DataConversionException, IOException;

    /**
     * @see #getActivityFilePath()
     */
    Optional<ReadOnlyActivity> readActivity(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyActivity} to the storage.
     * @param activity cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveActivity(ReadOnlyActivity activity) throws IOException;

    /**
     * @see #saveActivity(ReadOnlyActivity)
     */
    void saveActivity(ReadOnlyActivity activity, Path filePath) throws IOException;

}
