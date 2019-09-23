package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path loanRecordsFilePath = Paths.get("data", "loanrecords.json");
    private Path catalogueFilePath = Paths.get("data", "catalogue.json");
    private Path borrowerRecordsFilePath = Paths.get("data", "borrowerrecords.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setLoanRecordsFilePath(newUserPrefs.getLoanRecordsFilePath());
        setCatalogueFilePath(newUserPrefs.getCatalogueFilePath());
        setBorrowerRecordsFilePath(newUserPrefs.getBorrowerRecordsFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getLoanRecordsFilePath() {
        return loanRecordsFilePath;
    }

    public void setLoanRecordsFilePath(Path loanRecordsFilePath) {
        requireNonNull(loanRecordsFilePath);
        this.loanRecordsFilePath = loanRecordsFilePath;
    }

    public Path getCatalogueFilePath() {
        return catalogueFilePath;
    }

    public void setCatalogueFilePath(Path catalogueFilePath) {
        requireNonNull(catalogueFilePath);
        this.catalogueFilePath = catalogueFilePath;
    }

    public Path getBorrowerRecordsFilePath() {
        return borrowerRecordsFilePath;
    }

    public void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath) {
        requireNonNull(borrowerRecordsFilePath);
        this.borrowerRecordsFilePath = borrowerRecordsFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath)
                && loanRecordsFilePath.equals(o.loanRecordsFilePath)
                && catalogueFilePath.equals(o.catalogueFilePath)
                && borrowerRecordsFilePath.equals(o.borrowerRecordsFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, loanRecordsFilePath, borrowerRecordsFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal Catalogue data file location : " + addressBookFilePath);
        sb.append("\nLocal LoanRecords data file location : " + loanRecordsFilePath);
        sb.append("\nLocal Catalogue data file location : " + catalogueFilePath);
        sb.append("\nLocal BorrowerRecords data file location : " + borrowerRecordsFilePath);
        return sb.toString();
    }

}
