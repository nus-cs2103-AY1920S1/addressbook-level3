package seedu.ichifund.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ichifund.commons.exceptions.DataConversionException;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;

/**
 * Represents a storage for {@link FundBook}.
 */
public interface FundBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFundBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyFundBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFundBook> readFundBook() throws DataConversionException, IOException;

    /**
     * @see #getFundBookFilePath()
     */
    Optional<ReadOnlyFundBook> readFundBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFundBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFundBook(ReadOnlyFundBook addressBook) throws IOException;

    /**
     * @see #saveFundBook(ReadOnlyFundBook)
     */
    void saveFundBook(ReadOnlyFundBook addressBook, Path filePath) throws IOException;

}
