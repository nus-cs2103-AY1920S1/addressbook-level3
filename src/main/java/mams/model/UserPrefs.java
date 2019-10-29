package mams.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import mams.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path mamsFilePath = Paths.get("data" , "mams.json");
    private Path commandHistoryFilePath = Paths.get("data", "commandHistory.json");

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
        setMamsFilePath(newUserPrefs.getMamsFilePath());
        setCommandHistoryFilePath(newUserPrefs.getCommandHistoryFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getMamsFilePath() {
        return mamsFilePath;
    }

    public void setMamsFilePath(Path mamsFilePath) {
        requireNonNull(mamsFilePath);
        this.mamsFilePath = mamsFilePath;
    }

    public Path getCommandHistoryFilePath() {
        return commandHistoryFilePath;
    }

    public void setCommandHistoryFilePath(Path commandHistoryFilePath) {
        requireNonNull(commandHistoryFilePath);
        this.commandHistoryFilePath = commandHistoryFilePath;
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
                && mamsFilePath.equals(o.mamsFilePath)
                && commandHistoryFilePath.equals(o.commandHistoryFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, mamsFilePath, commandHistoryFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + mamsFilePath);
        sb.append("\nLocal command history file location : " + commandHistoryFilePath);
        return sb.toString();
    }

}
