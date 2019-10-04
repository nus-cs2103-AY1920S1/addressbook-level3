package io.xpire.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import io.xpire.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path xpireFilePath = Paths.get("data" , "xpire.json");

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
        setXpireFilePath(newUserPrefs.getXpireFilePath());
    }

    public GuiSettings getGuiSettings() {
        return this.guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getXpireFilePath() {
        return this.xpireFilePath;
    }

    public void setXpireFilePath(Path expiryDateTrackerFilePath) {
        requireNonNull(expiryDateTrackerFilePath);
        this.xpireFilePath = expiryDateTrackerFilePath;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof UserPrefs)) {
            return false;
        } else {
            UserPrefs other = (UserPrefs) obj;
            return this.guiSettings.equals(other.guiSettings) && this.xpireFilePath.equals(other.xpireFilePath);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.guiSettings, this.xpireFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + this.guiSettings + "\n");
        sb.append("Local data file location : " + this.xpireFilePath);
        return sb.toString();
    }
}
