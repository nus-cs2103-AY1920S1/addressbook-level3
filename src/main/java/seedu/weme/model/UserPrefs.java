package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.weme.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path dataFilePath = Paths.get("data" , "weme.json");
    private Path memeImagePath = Paths.get("data", "memes");
    private Path templateImagePath = Paths.get("data", "templates");
    private Path statsDataFilePath = Paths.get("data" , "stats.json");

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
        setDataFilePath(newUserPrefs.getDataFilePath());
        setMemeImagePath(newUserPrefs.getMemeImagePath());
        setTemplateImagePath(newUserPrefs.getTemplateImagePath());
        setStatsDataFilePath(newUserPrefs.getStatsDataFilePath());
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

    @Override
    public Path getMemeImagePath() {
        return memeImagePath;
    }

    public void setMemeImagePath(Path memeImagePath) {
        requireNonNull(memeImagePath);
        this.memeImagePath = memeImagePath;
    }

    @Override
    public Path getTemplateImagePath() {
        return templateImagePath;
    }

    public void setTemplateImagePath(Path templateImagePath) {
        requireNonNull(templateImagePath);
        this.templateImagePath = templateImagePath;
    }

    public Path getStatsDataFilePath() {
        return statsDataFilePath;
    }

    public void setStatsDataFilePath(Path likeDataFilePath) {
        requireNonNull(likeDataFilePath);
        this.statsDataFilePath = likeDataFilePath;
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
                && dataFilePath.equals(o.dataFilePath)
                && memeImagePath.equals(o.memeImagePath)
                && templateImagePath.equals(o.templateImagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, dataFilePath, memeImagePath, templateImagePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + dataFilePath);
        sb.append("\nMemes image location : " + memeImagePath);
        sb.append("\nTemplate image location : " + templateImagePath);
        return sb.toString();
    }

}
