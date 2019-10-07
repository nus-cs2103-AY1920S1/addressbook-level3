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
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");
    private Path timeBookFilePath = Paths.get("data", "timebook.json");
    private Path nusModsDataFilePath = Paths.get("data", "nusmodsdata.json");

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
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
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

    public Path getNusModsDataFilePath() {
        return nusModsDataFilePath;
    }

    public void setNusModsDataFilePath(Path nusModsDataFilePath) {
        requireNonNull(nusModsDataFilePath);
        this.nusModsDataFilePath = nusModsDataFilePath;
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
                && timeBookFilePath.equals(o.timeBookFilePath)
                && nusModsDataFilePath.equals(o.nusModsDataFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, timeBookFilePath, nusModsDataFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : \n");
        sb.append("\t" + addressBookFilePath + "\n");
        sb.append("\t" + timeBookFilePath + "\n");
        sb.append("\t" + nusModsDataFilePath + "\n");
        return sb.toString();
    }

}
