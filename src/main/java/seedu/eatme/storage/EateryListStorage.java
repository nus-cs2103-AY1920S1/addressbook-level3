package seedu.eatme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.ReadOnlyEateryList;

/**
 * Represents a storage for {@link EateryList}.
 */
public interface EateryListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEateryListFilePath();

    /**
     * Returns EateryList data as a {@link ReadOnlyEateryList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEateryList> readEateryList() throws DataConversionException, IOException;

    /**
     * @see #getEateryListFilePath()
     */
    Optional<ReadOnlyEateryList> readEateryList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEateryList} to the storage.
     * @param eateryList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEateryList(ReadOnlyEateryList eateryList) throws IOException;

    /**
     * @see #saveEateryList(ReadOnlyEateryList)
     */
    void saveEateryList(ReadOnlyEateryList eateryList, Path filePath) throws IOException;

}
