package seedu.address.storage.wastelist;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.TreeMap;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWasteList;
import seedu.address.model.WasteList;
import seedu.address.model.waste.WasteMonth;

/**
 * A storage for waste lists
 */
public interface WasteListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWasteListFilePath();

    /**
     * Returns WasteList data as a {@link ReadOnlyWasteList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<TreeMap<WasteMonth, WasteList>> readWasteList() throws DataConversionException, IOException;

    /**
     * @see #getWasteListFilePath()
     */
    Optional<TreeMap<WasteMonth, WasteList>> readWasteList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWasteList} to the storage.
     * @param wasteArchive cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWasteList(TreeMap<WasteMonth, WasteList> wasteArchive) throws IOException;

    /**
     * @see #saveWasteList(TreeMap<WasteMonth, WasteList>)
     */
    void saveWasteList(TreeMap<WasteMonth, WasteList> wasteArchive, Path filePath) throws IOException;
}
