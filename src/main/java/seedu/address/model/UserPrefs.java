package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private UserSettings userSettings = new UserSettings();
    private Path loanRecordsFilePath = Paths.get("data", "loanrecords.json");
    private Path catalogFilePath = Paths.get("data", "catalog.json");
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
        setUserSettings(newUserPrefs.getUserSettings());
        setLoanRecordsFilePath(newUserPrefs.getLoanRecordsFilePath());
        setCatalogFilePath(newUserPrefs.getCatalogFilePath());
        setBorrowerRecordsFilePath(newUserPrefs.getBorrowerRecordsFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        requireNonNull(userSettings);
        this.userSettings = userSettings;
    }

    public Path getLoanRecordsFilePath() {
        return loanRecordsFilePath;
    }

    public void setLoanRecordsFilePath(Path loanRecordsFilePath) {
        requireNonNull(loanRecordsFilePath);
        this.loanRecordsFilePath = loanRecordsFilePath;
    }

    public Path getCatalogFilePath() {
        return catalogFilePath;
    }

    public void setCatalogFilePath(Path catalogFilePath) {
        requireNonNull(catalogFilePath);
        this.catalogFilePath = catalogFilePath;
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
                && userSettings.equals(o.userSettings)
                && loanRecordsFilePath.equals(o.loanRecordsFilePath)
                && catalogFilePath.equals(o.catalogFilePath)
                && borrowerRecordsFilePath.equals(o.borrowerRecordsFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, userSettings, catalogFilePath, loanRecordsFilePath, borrowerRecordsFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nUser Settings : " + userSettings);
        sb.append("\nLocal LoanRecords data file location : " + loanRecordsFilePath);
        sb.append("\nLocal Catalog data file location : " + catalogFilePath);
        sb.append("\nLocal BorrowerRecords data file location : " + borrowerRecordsFilePath);
        return sb.toString();
    }

}
