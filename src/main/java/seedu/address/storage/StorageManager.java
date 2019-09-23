package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBorrowerRecords;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.borrowerrecords.BorrowerRecordsStorage;
import seedu.address.storage.catalogue.CatalogueStorage;
import seedu.address.storage.loanrecord.LoanRecordsStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private LoanRecordsStorage loanRecordsStorage;
    private CatalogueStorage catalogueStorage;
    private BorrowerRecordsStorage borrowerRecordsStorage;

    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          LoanRecordsStorage loanRecordsStorage, CatalogueStorage catalogueStorage,
                          BorrowerRecordsStorage borrowerRecordsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.loanRecordsStorage = loanRecordsStorage;
        this.catalogueStorage = catalogueStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
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

    // ================ Catalogue methods ==============================

    @Override
    public Path getCatalogueFilePath() {
        return catalogueStorage.getCatalogueFilePath();
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue() throws DataConversionException, IOException {
        return readCatalogue(catalogueStorage.getCatalogueFilePath());
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return catalogueStorage.readCatalogue(filePath);
    }

    @Override
    public void saveCatalogue(ReadOnlyCatalogue catalogue) throws IOException {
        saveCatalogue(catalogue, catalogueStorage.getCatalogueFilePath());
    }

    @Override
    public void saveCatalogue(ReadOnlyCatalogue catalogue, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        catalogueStorage.saveCatalogue(catalogue, filePath);
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
