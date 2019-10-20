package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.sorter.CustomSorter;

/**
 * Creates a storage to store fields for the CustomSort/
 */
public interface CustomSortStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getSortFilePath();

    /**
     * Returns Recommendation data as a {@link CustomSorter}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CustomSorter> readFields() throws DataConversionException, IOException;

    /**
     * @see #getSortFilePath()
     */
    Optional<CustomSorter> readFields(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link UserRecommendations} to the storage.
     * @param sorter cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFields(CustomSorter sorter) throws IOException;

    /**
     * @see #saveFields(CustomSorter)
     */
    void saveFields(CustomSorter sorter, Path filePath) throws IOException;
}
