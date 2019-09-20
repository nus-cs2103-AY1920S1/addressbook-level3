package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAlgoBase;

/**
 * Represents a storage for {@link seedu.address.model.AlgoBase}.
 */
public interface AlgoBaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAlgoBaseFilePath();

    /**
     * Returns AlgoBase data as a {@link ReadOnlyAlgoBase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAlgoBase> readAlgoBase() throws DataConversionException, IOException;

    /**
     * @see #getAlgoBaseFilePath()
     */
    Optional<ReadOnlyAlgoBase> readAlgoBase(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAlgoBase} to the storage.
     * @param algoBase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAlgoBase(ReadOnlyAlgoBase algoBase) throws IOException;

    /**
     * @see #saveAlgoBase(ReadOnlyAlgoBase)
     */
    void saveAlgoBase(ReadOnlyAlgoBase algoBase, Path filePath) throws IOException;

}
