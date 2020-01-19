package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.Mark;
import seedu.mark.model.ReadOnlyMark;

/**
 * Represents a storage for {@link Mark}.
 */
public interface MarkStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMarkFilePath();

    /**
     * Returns Mark data as a {@link ReadOnlyMark}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMark> readMark() throws DataConversionException, IOException;

    /**
     * @see #getMarkFilePath()
     */
    Optional<ReadOnlyMark> readMark(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMark} to the storage.
     * @param mark cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMark(ReadOnlyMark mark) throws IOException;

    /**
     * @see #saveMark(ReadOnlyMark)
     */
    void saveMark(ReadOnlyMark mark, Path filePath) throws IOException;

}
