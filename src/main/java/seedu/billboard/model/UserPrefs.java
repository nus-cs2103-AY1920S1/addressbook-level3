package seedu.billboard.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.billboard.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path billboardFilePath = Paths.get("data" , "billboard.json");
    private Path archiveFilePath = Paths.get("data" , "archive.json");

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
        setBillboardFilePath(newUserPrefs.getBillboardFilePath());
        setArchiveFilePath(newUserPrefs.getArchiveFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getBillboardFilePath() {
        return billboardFilePath;
    }

    public void setBillboardFilePath(Path billboardFilePath) {
        requireNonNull(billboardFilePath);
        this.billboardFilePath = billboardFilePath;
    }

    public Path getArchiveFilePath() {
        return archiveFilePath;
    }

    public void setArchiveFilePath(Path archiveFilePath) {
        requireNonNull(archiveFilePath);
        this.archiveFilePath = archiveFilePath;
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
                && billboardFilePath.equals(o.billboardFilePath)
                && archiveFilePath.equals(o.archiveFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, billboardFilePath, archiveFilePath);
    }

    @Override
    public String toString() {
        return ("Gui Settings : " + guiSettings)
                + "\nLocal data file location : " + billboardFilePath
                + "\nLocal archive file location : " + archiveFilePath;
    }

}
