package seedu.revision.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.revision.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path historyFilePath = Paths.get("data", "history.json");
    private Path revisionToolFilePath = Paths.get("data" , "revisiontool.json");

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
        setRevisionToolFilePath(newUserPrefs.getRevisionToolFilePath());
        setHistoryFilePath(newUserPrefs.getHistoryFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getRevisionToolFilePath() {
        return revisionToolFilePath;
    }

    public void setRevisionToolFilePath(Path revisionToolFilePath) {
        requireNonNull(revisionToolFilePath);
        this.revisionToolFilePath = revisionToolFilePath;
    }

    public Path getHistoryFilePath() {
        return historyFilePath;
    }

    public void setHistoryFilePath(Path historyFilePath) {
        requireNonNull(historyFilePath);
        this.historyFilePath = historyFilePath;
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
                && revisionToolFilePath.equals(o.revisionToolFilePath)
                && historyFilePath.equals(o.historyFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, revisionToolFilePath, historyFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + revisionToolFilePath);
        sb.append("\nLocal data history file location : " + historyFilePath);
        return sb.toString();
    }

}
