package com.typee.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.typee.commons.exceptions.DataConversionException;
import com.typee.model.EngagementList;
import com.typee.model.ReadOnlyEngagementList;

/**
 * Represents a storage for {@link EngagementList}.
 */
public interface EngagementListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEngagementListFilePath();

    /**
     * Returns EngagementList data as a {@link ReadOnlyEngagementList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEngagementList> readEngagementList() throws DataConversionException, IOException;

    /**
     * @see #getEngagementListFilePath()
     */
    Optional<ReadOnlyEngagementList> readEngagementList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEngagementList} to the storage.
     * @param engagementList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEngagementList(ReadOnlyEngagementList engagementList) throws IOException;

    /**
     * @see #saveEngagementList(ReadOnlyEngagementList)
     */
    void saveEngagementList(ReadOnlyEngagementList engagementList, Path filePath) throws IOException;

}
