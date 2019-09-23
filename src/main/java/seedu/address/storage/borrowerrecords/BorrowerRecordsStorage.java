package seedu.address.storage.borrowerrecords;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.ReadOnlyBorrowerRecords;

/**
 * Represents a storage for {@link BorrowerRecords}.
 */
public interface BorrowerRecordsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBorrowerRecordsFilePath();

    /**
     * Returns BorrowerRecords data as a {@link ReadOnlyBorrowerRecords}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBorrowerRecords> readBorrowerRecords() throws DataConversionException, IOException;

    /**
     * @see #getBorrowerRecordsFilePath()
     */
    Optional<ReadOnlyBorrowerRecords> readBorrowerRecords(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBorrowerRecords} to the storage.
     * @param borrowerRecords cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBorrowerRecords(ReadOnlyBorrowerRecords borrowerRecords) throws IOException;

    /**
     * @see #saveBorrowerRecords(ReadOnlyBorrowerRecords)
     */
    void saveBorrowerRecords(ReadOnlyBorrowerRecords borrowerRecords, Path filePath) throws IOException;

}
