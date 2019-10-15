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
    private Path userProfileFilePath = Paths.get("data" , "userprofile.json");
    private Path healthRecordsFilePath = Paths.get("data", "healthrecords.json");
    private Path recipesFilePath = Paths.get("data" , "recipes.json");

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
        setUserProfileFilePath(newUserPrefs.getUserProfileFilePath());
        setHealthRecordsFilePath(newUserPrefs.getHealthRecordsFilePath());
        setRecipesFilePath(newUserPrefs.getRecipesFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getUserProfileFilePath() {
        return userProfileFilePath;
    }

    public Path getRecipesFilePath() {
        return recipesFilePath;
    }

    public void setUserProfileFilePath(Path userProfileFilePath) {
        requireNonNull(userProfileFilePath);
        this.userProfileFilePath = userProfileFilePath;
    }

    public Path getHealthRecordsFilePath() {
        return healthRecordsFilePath;
    }

    public void setHealthRecordsFilePath(Path healthRecordsFilePath) {
        requireNonNull(healthRecordsFilePath);
        this.healthRecordsFilePath = healthRecordsFilePath;
    }

    public void setRecipesFilePath(Path recipesFilePath) {
        requireNonNull(recipesFilePath);
        this.recipesFilePath = recipesFilePath;
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
                && userProfileFilePath.equals(o.userProfileFilePath)
                && recipesFilePath.equals(o.recipesFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, userProfileFilePath, recipesFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal UserPref data file location : " + userProfileFilePath);
        sb.append("\nLocal RecipBook data file location : " + recipesFilePath);
        return sb.toString();
    }

}
