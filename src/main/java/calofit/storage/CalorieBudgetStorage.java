package calofit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.CalorieBudget;

/**
 * Represents a storage for {@link CalorieBudget}.
 */
public interface CalorieBudgetStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCalorieBudgetFilePath();


    /**
     * Returns calorie budget data as a {@link CalorieBudget}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CalorieBudget> readCalorieBudget() throws DataConversionException, IOException;

    /**
     * @see #readCalorieBudget()
     */
    Optional<CalorieBudget> readCalorieBudget(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link CalorieBudget} to the storage.
     * @param budget cannot be null
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCalorieBudget(CalorieBudget budget) throws IOException;

    /**
     * @see #saveCalorieBudget(CalorieBudget)
     */
    void saveCalorieBudget(CalorieBudget budget, Path filePath) throws IOException;
}
