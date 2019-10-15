package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCheatSheetBook;

public interface CheatSheetBookStorage {

    /**
     * @return the datapath of the file
     */
    Path getCheatSheetBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCheatSheetBook> readCheatSheetBook() throws DataConversionException, IOException;

    /**
     * @see #getCheatSheetBookFilePath()
     */
    Optional<ReadOnlyCheatSheetBook> readCheatSheetBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCheatSheetBook} to the storage.
     * @param cheatSheetBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook) throws IOException;

    /**
     * @see #saveCheatSheetBook(ReadOnlyCheatSheetBook);
     */
    void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook, Path filePath) throws IOException;
}
