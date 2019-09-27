package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
  
    private UserPrefsStorage userPrefsStorage;
    private LoanRecordsStorage loanRecordsStorage;
    private CatalogStorage catalogStorage;
    private BorrowerRecordsStorage borrowerRecordsStorage;

    public StorageManager(UserPrefsStorage userPrefsStorage,
                          LoanRecordsStorage loanRecordsStorage, CatalogStorage catalogStorage,
                          BorrowerRecordsStorage borrowerRecordsStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.loanRecordsStorage = loanRecordsStorage;
        this.catalogStorage = catalogStorage;
        this.borrowerRecordsStorage = borrowerRecordsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Loan Records methods ==============================

    @Override
    public Path getLoanRecordsFilePath() {
        return loanRecordsStorage.getLoanRecordsFilePath();
    }

    @Override
    public Optional<ReadOnlyLoanRecords> readLoanRecords() throws DataConversionException, IOException {
        return readLoanRecords(loanRecordsStorage.getLoanRecordsFilePath());
    }

    @Override
    public Optional<ReadOnlyLoanRecords> readLoanRecords(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return loanRecordsStorage.readLoanRecords(filePath);
    }

    @Override
    public void saveLoanRecords(ReadOnlyLoanRecords loanRecords) throws IOException {
        saveLoanRecords(loanRecords, loanRecordsStorage.getLoanRecordsFilePath());
    }

    @Override
    public void saveLoanRecords(ReadOnlyLoanRecords loanRecords, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        loanRecordsStorage.saveLoanRecords(loanRecords,  filePath);
    }

    // ================ Catalog methods ==============================

    @Override
    public Path getCatalogFilePath() {
        return catalogStorage.getCatalogFilePath();
    }

    @Override
    public Optional<ReadOnlyCatalog> readCatalog() throws DataConversionException, IOException {
        return readCatalog(catalogStorage.getCatalogFilePath());
    }

    @Override
    public Optional<ReadOnlyCatalog> readCatalog(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return catalogStorage.readCatalog(filePath);
    }

    @Override
    public void saveCatalog(ReadOnlyCatalog catalog) throws IOException {
        saveCatalog(catalog, catalogStorage.getCatalogFilePath());
    }

    @Override
    public void saveCatalog(ReadOnlyCatalog catalog, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        catalogStorage.saveCatalog(catalog, filePath);
    }

    // ================ BorrowerRecords methods ==============================

    @Override
    public Path getBorrowerRecordsFilePath() {
        return borrowerRecordsStorage.getBorrowerRecordsFilePath();
    }

    @Override
    public Optional<ReadOnlyBorrowerRecords> readBorrowerRecords() throws DataConversionException, IOException {
        return readBorrowerRecords(borrowerRecordsStorage.getBorrowerRecordsFilePath());
    }

    @Override
    public Optional<ReadOnlyBorrowerRecords> readBorrowerRecords(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return borrowerRecordsStorage.readBorrowerRecords(filePath);
    }

    @Override
    public void saveBorrowerRecords(ReadOnlyBorrowerRecords borrowerRecords) throws IOException {
        saveBorrowerRecords(borrowerRecords, borrowerRecordsStorage.getBorrowerRecordsFilePath());
    }

    @Override
    public void saveBorrowerRecords(ReadOnlyBorrowerRecords borrowerRecords, Path filePath) throws IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        borrowerRecordsStorage.saveBorrowerRecords(borrowerRecords, filePath);
    }

}
