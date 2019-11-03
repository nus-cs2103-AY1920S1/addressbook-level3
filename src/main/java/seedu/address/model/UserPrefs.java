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
    private Path activityBookFilePath = Paths.get("data" , "activitybook.json");
    private Path internalStateFilePath = Paths.get("data", "internalstate.json");

    public UserPrefs() {};

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
        setActivityBookFilePath(newUserPrefs.getActivityBookFilePath());
        setInternalStateFilePath(newUserPrefs.getInternalStateFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getInternalStateFilePath() {
        return internalStateFilePath;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getActivityBookFilePath() {
        return activityBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    public void setActivityBookFilePath(Path activityBookFilePath) {
        requireNonNull(activityBookFilePath);
        this.activityBookFilePath = activityBookFilePath;
    }

    public void setInternalStateFilePath(Path internalStateFilePath) {
        requireNonNull(internalStateFilePath);
        this.internalStateFilePath = internalStateFilePath;
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
                && activityBookFilePath.equals(o.activityBookFilePath)
                && internalStateFilePath.equals(o.internalStateFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nActivity data file location: " + activityBookFilePath);
        sb.append("\nAddress data file location : " + addressBookFilePath);
        sb.append("\nState data file location : " + internalStateFilePath);
        return sb.toString();
    }

}
