package seedu.billboard.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;

/**
 * Represents a storage for {@link Billboard}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBillboardFilePath();

    /**
     * Returns Billboard data as a {@link ReadOnlyBillboard}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBillboard> readBillboard() throws DataConversionException, IOException;

    /**
     * @see #getBillboardFilePath()
     */
    Optional<ReadOnlyBillboard> readBillboard(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBillboard} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBillboard(ReadOnlyBillboard addressBook) throws IOException;

    /**
     * @see #saveBillboard(ReadOnlyBillboard)
     */
    void saveBillboard(ReadOnlyBillboard addressBook, Path filePath) throws IOException;

}
