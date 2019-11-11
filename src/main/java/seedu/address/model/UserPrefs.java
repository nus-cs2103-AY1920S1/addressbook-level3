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
    private Path dataFilePath = Paths.get("data");
    private Path wordBankListFilePath = Paths.get("data", "wordBanks");
    private Path appSettingsFilePath = Paths.get("data" , "appsettings.json");
    private boolean isSampleInitiated = false;

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
    void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setDataFilePath(newUserPrefs.getDataFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(Path dataFilePath) {
        requireNonNull(dataFilePath);
        this.dataFilePath = dataFilePath;
    }

    public Path getAppSettingsFilePath() {
        return appSettingsFilePath;
    }

    public void setAppSettingsFilePath(Path appSettingsFilePath) {
        requireNonNull(appSettingsFilePath);
        this.appSettingsFilePath = appSettingsFilePath;
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
                && dataFilePath.equals(o.dataFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, dataFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + dataFilePath);
        return sb.toString();
    }

    /**
     * Retrieves whether if sample data has been initiated before.
     *
     * @return whether if sample data has been initiated before.
     */
    public boolean isSampleInitiated() {
        return isSampleInitiated;
    }

    /**
     * Set the boolean isSampleInitiated to true.
     */
    public void setSampleInitiated() {
        isSampleInitiated = true;
    }
}
