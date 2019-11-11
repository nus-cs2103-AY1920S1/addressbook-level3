package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.SemesterNo;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private AppSettings appSettings = new AppSettings();
    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");
    private Path timeBookFilePath = Paths.get("data", "timebook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

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
        setAppSettings(newUserPrefs.getAppSettings());
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public AcadYear getAcadYear() {
        return appSettings.getAcadYear();
    }

    public SemesterNo getSemesterNo() {
        return appSettings.getSemesterNo();
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

    public Path getTimeBookFilePath() {
        return timeBookFilePath;
    }

    public void setTimeBookFilePath(Path timeBookFilePath) {
        requireNonNull(timeBookFilePath);
        this.timeBookFilePath = timeBookFilePath;
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

        return appSettings.equals(o.appSettings)
                && guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath)
                && timeBookFilePath.equals(o.timeBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appSettings, guiSettings, addressBookFilePath, timeBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App Settings : " + appSettings);
        sb.append("\nGui Settings : " + guiSettings);
        sb.append("\nAddressBook file location: " + addressBookFilePath);
        sb.append("\nTimeBook file location : " + timeBookFilePath);
        return sb.toString();
    }

}
