package seedu.address.model.cap;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class CapUserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path capLogFilePath = Paths.get("data" , "capmodulelog.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public CapUserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public CapUserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setCapLogFilePath(newUserPrefs.getCapLogFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getCapLogFilePath() {
        return capLogFilePath;
    }

    public void setCapLogFilePath(Path capLogFilePath) {
        requireNonNull(capLogFilePath);
        this.capLogFilePath = capLogFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CapUserPrefs)) { //this handles null as well.
            return false;
        }

        CapUserPrefs o = (CapUserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && capLogFilePath.equals(o.capLogFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, capLogFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + capLogFilePath);
        return sb.toString();
    }

}
