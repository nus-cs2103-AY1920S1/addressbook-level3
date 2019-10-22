package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ItemStorage;

/**
 * Represents a storage for ELISA.
 */
public interface ItemListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getItemListFilePath();

    /**
     * Saves the given {@link ItemStorage} to the storage.
     * @param itemStorage cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveItemStorage(ItemStorage itemStorage) throws IOException;

    /**
     * @see #saveItemStorage(ItemStorage itemStorage)
     */
    void saveItemStorage(ItemStorage itemStorage, Path filePath) throws IOException;

    ItemStorage toModelType() throws IOException, DataConversionException;

}
