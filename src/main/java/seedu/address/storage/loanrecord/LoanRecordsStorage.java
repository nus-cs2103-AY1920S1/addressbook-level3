package seedu.address.storage.loanrecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyLoanRecords;

/**
 * Represents a storage for {@link seedu.address.model.LoanRecords}.
 */
public interface LoanRecordsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLoanRecordsFilePath();

    /**
     * Returns LoanRecords data as a {@link ReadOnlyLoanRecords}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLoanRecords> readLoanRecords() throws DataConversionException, IOException;

    /**
     * @see #getLoanRecordsFilePath()
     */
    Optional<ReadOnlyLoanRecords> readLoanRecords(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLoanRecords} to the storage.
     * @param loanRecords cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLoanRecords(ReadOnlyLoanRecords loanRecords) throws IOException;

    /**
     * @see #saveLoanRecords(ReadOnlyLoanRecords, Path)
     */
    void saveLoanRecords(ReadOnlyLoanRecords loanRecords, Path filePath) throws IOException;

}
