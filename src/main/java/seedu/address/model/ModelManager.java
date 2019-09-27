package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final LoanRecords loanRecords;
    private final Catalog catalog;
    private final BorrowerRecords borrowerRecords;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     * TODO change
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs,
                        ReadOnlyLoanRecords loanRecords, ReadOnlyCatalog catalog,
                        ReadOnlyBorrowerRecords borrowerRecords) {
        super();
//        requireAllNonNull(addressBook, userPrefs, catalog);

        logger.fine("Initializing with address book: " + catalog + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        // testing loan records
        this.loanRecords = new LoanRecords(loanRecords);
        this.loanRecords.populateLoans();
        // testing
        this.catalog = new Catalog(catalog);
        this.catalog.populateBooks();
        // testing
        this.borrowerRecords = new BorrowerRecords(borrowerRecords);
        this.borrowerRecords.populateBorrowers();

    }

    public ModelManager() {
        this(new UserPrefs(),
                new LoanRecords(), new Catalog(), new BorrowerRecords());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getLoanRecordsFilePath() {
        return userPrefs.getLoanRecordsFilePath();
    }

    @Override
    public void setLoanRecordsFilePath(Path loanRecordsFilePath) {
        requireNonNull(loanRecordsFilePath);
        userPrefs.setLoanRecordsFilePath(loanRecordsFilePath);
    }

    @Override
    public Path getCatalogFilePath() {
        return userPrefs.getCatalogFilePath();
    }

    @Override
    public void setCatalogFilePath(Path catalogFilePath) {
        requireNonNull(catalogFilePath);
        userPrefs.setCatalogFilePath(catalogFilePath);
    }

    @Override
    public Path getBorrowerRecordsFilePath() {
        return userPrefs.getBorrowerRecordsFilePath();
    }

    @Override
    public void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath) {
        requireNonNull(borrowerRecordsFilePath);
        userPrefs.setBorrowerRecordsFilePath(borrowerRecordsFilePath);
    }

    //=========== Loan Records ===============================================================================

    public ReadOnlyLoanRecords getLoanRecords() {
        return loanRecords;
    }

    //=========== Catalog ===============================================================================

    public ReadOnlyCatalog getCatalog() {
        return catalog;
    }

    //=========== BorrowerRecords ===============================================================================

    public ReadOnlyBorrowerRecords getBorrowerRecords() {
        return borrowerRecords;
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && loanRecords.equals(other.loanRecords)
                && catalog.equals(other.catalog)
                && borrowerRecords.equals(other.borrowerRecords);
    }

}
