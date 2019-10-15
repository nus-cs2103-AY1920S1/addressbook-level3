package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWasteList;

/**
 * Represents a storage for {@link seedu.address.model.WasteList}.
 */
public interface WasteListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWasteListFilePath();

    /**
     * Returns WasteList data as a {@link seedu.address.model.ReadOnlyWasteList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWasteList> readWasteList() throws DataConversionException, IOException;

    /**
     * @see #getWasteListFilePath()
     */
    Optional<ReadOnlyWasteList> readWasteList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWasteList} to the storage.
     * @param wasteList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWasteList(ReadOnlyWasteList wasteList) throws IOException;

    /**
     * @see #saveWasteList(ReadOnlyWasteList)
     */
    void saveWasteList(ReadOnlyWasteList wasteList, Path filePath) throws IOException;


}
