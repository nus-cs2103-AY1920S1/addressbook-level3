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

    private boolean isGuiLocked;
    private GuiSettings guiSettings = new GuiSettings();
    private Path travelPalFilePath = Paths.get("data" , "travelpal.json");

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
        setTravelPalFilePath(newUserPrefs.getTravelPalFilePath());
        setGuiPrefsLocked(newUserPrefs.isGuiPrefsLocked());
    }

    @Override
    public boolean isGuiPrefsLocked() {
        return isGuiLocked;
    }

    public void setGuiPrefsLocked(boolean doLock) {
        isGuiLocked = doLock;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getTravelPalFilePath() {
        return travelPalFilePath;
    }

    public void setTravelPalFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.travelPalFilePath = addressBookFilePath;
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
                && travelPalFilePath.equals(o.travelPalFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, travelPalFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append(" | Is gui settings locked: " + isGuiLocked);
        sb.append("\nLocal data file location : " + travelPalFilePath);
        return sb.toString();
    }

}
