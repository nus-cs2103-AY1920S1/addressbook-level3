package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyProjectList;

/**
 * Represents a storage for {@link seedu.address.model.ProjectList}.
 */
public interface ProjectListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProjectListFilePath();

    /**
     * Returns ProjectList data as a {@link ReadOnlyProjectList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyProjectList> readProjectList() throws DataConversionException, IOException;

    /**
     * @see #getProjectListFilePath()
     */
    Optional<ReadOnlyProjectList> readProjectList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyProjectList} to the storage.
     * @param projectList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProjectList(ReadOnlyProjectList projectList) throws IOException;

    /**
     * @see #saveProjectList(ReadOnlyProjectList)
     */
    void saveProjectList(ReadOnlyProjectList projectList, Path filePath) throws IOException;

}
