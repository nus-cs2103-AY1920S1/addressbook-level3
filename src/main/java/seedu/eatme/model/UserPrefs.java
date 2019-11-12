package seedu.eatme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.eatme.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path eateryListFilePath = Paths.get("data", System.getProperty("user.name") + ".json");
    private Path feedListFilePath = Paths.get("data", "feedlist.json");

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
        setEateryListFilePath(newUserPrefs.getEateryListFilePath());
        setFeedListFilePath(newUserPrefs.getFeedListFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getEateryListFilePath() {
        return eateryListFilePath;
    }

    public void setEateryListFilePath(Path eateryListFilePath) {
        requireNonNull(eateryListFilePath);
        this.eateryListFilePath = eateryListFilePath;
    }

    public Path getFeedListFilePath() {
        return feedListFilePath;
    }

    public void setFeedListFilePath(Path feedListFilePath) {
        requireNonNull(feedListFilePath);
        this.feedListFilePath = feedListFilePath;
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
                && eateryListFilePath.equals(o.eateryListFilePath)
                && feedListFilePath.equals(o.feedListFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, eateryListFilePath, feedListFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + eateryListFilePath);
        sb.append("\nLocal feed file location : " + feedListFilePath);
        return sb.toString();
    }

}
