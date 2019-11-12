package io.xpire.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.Xpire;
import javafx.util.Pair;

/**
 * Represents a storage for {@link Xpire}.
 */
public interface ListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getListFilePath();

    /**
     * Returns Xpire data as a {@link ReadOnlyListView}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Pair<Optional<ReadOnlyListView>, Optional<ReadOnlyListView>> readList() throws DataConversionException, IOException;

    /**
     * @see #getListFilePath()
     */
    Pair<Optional<ReadOnlyListView>, Optional<ReadOnlyListView>> readList(Path filePath) throws DataConversionException,
                                                               IOException;

    /**
     * Saves the given {@link ReadOnlyListView} to the storage.
     * @param lists cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveList(ReadOnlyListView[] lists) throws IOException;

    /**
     * @see #saveList(ReadOnlyListView[])
     */
    void saveList(ReadOnlyListView[] lists, Path filePath) throws IOException;

}
