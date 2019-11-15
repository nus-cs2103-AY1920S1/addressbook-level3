package seedu.revision.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.RevisionTool;

/**
 * Represents a storage for {@link RevisionTool}.
 */
public interface RevisionToolStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRevisionToolFilePath();

    /**
     * Returns RevisionTool data as a {@link ReadOnlyRevisionTool}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRevisionTool> readRevisionTool() throws DataConversionException, IOException;

    /**
     * @see #getRevisionToolFilePath()
     */
    Optional<ReadOnlyRevisionTool> readRevisionTool(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRevisionTool} to the storage.
     * @param revisionTool cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRevisionTool(ReadOnlyRevisionTool revisionTool) throws IOException;

    /**
     * @see #saveRevisionTool(ReadOnlyRevisionTool)
     */
    void saveRevisionTool(ReadOnlyRevisionTool revisionTool, Path filePath) throws IOException;

}
