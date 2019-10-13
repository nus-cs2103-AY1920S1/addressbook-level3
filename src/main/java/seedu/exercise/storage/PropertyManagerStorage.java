package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.exercise.PropertyManager;

/**
 * Represents a storage for {@code PropertyManager}
 */
public interface PropertyManagerStorage {

    /**
     * Returns the file path of the PropertyManager data file.
     */
    Path getPropertyManagerFilePath();

    /**
     * Returns PropertyManager data as a {@code PropertyManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<PropertyManager> readPropertyManager() throws DataConversionException, IOException;

    /**
     * @see #readPropertyManager()
     */
    Optional<PropertyManager> readPropertyManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@code PropertyManager} to the storage.
     *
     * @throws IOException if there was any problem writing to the file.
     */
    void savePropertyManager(PropertyManager propertyManager) throws IOException;

    /**
     * @see #savePropertyManager(PropertyManager)
     */
    void savePropertyManager(PropertyManager propertyManager, Path filePath) throws IOException;
}
