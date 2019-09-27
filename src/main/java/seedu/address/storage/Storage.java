package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBorrowerRecords;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.borrowerrecords.BorrowerRecordsStorage;
import seedu.address.storage.catalog.CatalogStorage;
import seedu.address.storage.loanrecord.LoanRecordsStorage;

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

    // CatalogStorage methods

    @Override
    Path getCatalogFilePath();

    @Override
    Optional<ReadOnlyCatalog> readCatalog() throws DataConversionException, IOException;

    @Override
    void saveCatalog(ReadOnlyCatalog Catalog) throws IOException;

    // BorrowerRecordsStorage methods

    @Override
    Path getBorrowerRecordsFilePath();

    @Override
    Optional<ReadOnlyBorrowerRecords> readBorrowerRecords() throws DataConversionException, IOException;

    @Override
    void saveBorrowerRecords(ReadOnlyBorrowerRecords borrowerRecords) throws IOException;
}
