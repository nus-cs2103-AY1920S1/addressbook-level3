package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.LoanSlipException;
import seedu.address.model.ReadOnlyBorrowerRecords;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.borrowerrecords.BorrowerRecordsStorage;
import seedu.address.storage.catalog.CatalogStorage;
import seedu.address.storage.loanrecords.LoanRecordsStorage;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage,
        LoanRecordsStorage, CatalogStorage, BorrowerRecordsStorage {

    // UserPrefStorage methods

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // LoanRecordsStorage methods

    @Override
    Path getLoanRecordsFilePath();

    @Override
    Optional<ReadOnlyLoanRecords> readLoanRecords() throws DataConversionException, IOException;

    @Override
    void saveLoanRecords(ReadOnlyLoanRecords loanRecords) throws IOException;

    void storeNewLoanSlip() throws LoanSlipException;

    // CatalogStorage methods

    @Override
    Path getCatalogFilePath();

    @Override
    Optional<ReadOnlyCatalog> readCatalog(ReadOnlyLoanRecords initialLoanRecords)
            throws DataConversionException, IOException;

    @Override
    void saveCatalog(ReadOnlyCatalog catalog) throws IOException;

    // BorrowerRecordsStorage methods

    @Override
    Path getBorrowerRecordsFilePath();

    @Override
    Optional<ReadOnlyBorrowerRecords> readBorrowerRecords(ReadOnlyLoanRecords initialLoanRecords)
            throws DataConversionException, IOException;

    @Override
    void saveBorrowerRecords(ReadOnlyBorrowerRecords borrowerRecords) throws IOException;
}
