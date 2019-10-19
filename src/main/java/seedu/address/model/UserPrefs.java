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
    private Path exercisesFilePath = Paths.get("data" , "exercises.json");
    private Path userProfileFilePath = Paths.get("data" , "userprofile.json");
    private Path healthRecordsFilePath = Paths.get("data", "healthrecords.json");
    private Path recipesFilePath = Paths.get("data" , "recipes.json");
    private Path diaryFilePath = Paths.get("data" , "diary.json");
    private Path dashboardFilePath = Paths.get("data", "dashboard.json");

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
        setExercisesFilePath(newUserPrefs.getExercisesFilePath());
        setUserProfileFilePath(newUserPrefs.getUserProfileFilePath());
        setHealthRecordsFilePath(newUserPrefs.getHealthRecordsFilePath());
        setRecipesFilePath(newUserPrefs.getRecipesFilePath());
        setDiaryFilePath(newUserPrefs.getDiaryFilePath());
        setDashboardFilePath(newUserPrefs.getDashboardFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getExercisesFilePath() {
        return exercisesFilePath;
    }

    public Path getUserProfileFilePath() {
        return userProfileFilePath;
    }

    public Path getRecipesFilePath() {
        return recipesFilePath;
    }

    public Path getDiaryFilePath() {
        return diaryFilePath;
    }

    public Path getHealthRecordsFilePath() {
        return healthRecordsFilePath;
    }

    public Path getDashboardFilePath() {
        return dashboardFilePath;
    }

    public void setUserProfileFilePath(Path userProfileFilePath) {
        requireNonNull(userProfileFilePath);
        this.userProfileFilePath = userProfileFilePath;
    }

    public void setHealthRecordsFilePath(Path healthRecordsFilePath) {
        requireNonNull(healthRecordsFilePath);
        this.healthRecordsFilePath = healthRecordsFilePath;
    }

    public void setExercisesFilePath(Path exercisesFilePath) {
        requireNonNull(exercisesFilePath);
        this.exercisesFilePath = exercisesFilePath;
    }

    public void setRecipesFilePath(Path recipesFilePath) {
        requireNonNull(recipesFilePath);
        this.recipesFilePath = recipesFilePath;
    }

    public void setDiaryFilePath(Path diaryFilePath) {
        requireNonNull(diaryFilePath);
        this.diaryFilePath = diaryFilePath;
    }

    public void setDashboardFilePath(Path dashboardFilePath) {
        requireNonNull(dashboardFilePath);
        this.dashboardFilePath = dashboardFilePath;
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
                && exercisesFilePath.equals(o.exercisesFilePath)
                && userProfileFilePath.equals(o.userProfileFilePath)
                && recipesFilePath.equals(o.recipesFilePath)
                && diaryFilePath.equals(o.diaryFilePath)
                && dashboardFilePath.equals(o.dashboardFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, exercisesFilePath, userProfileFilePath, healthRecordsFilePath,
                recipesFilePath, diaryFilePath, dashboardFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal WorkoutPlanner data file location : " + exercisesFilePath);
        sb.append("\nLocal UserPref data file location : " + userProfileFilePath);
        sb.append("\nLocal RecipeBook data file location : " + recipesFilePath);
        sb.append("\nLocal data file location : " + diaryFilePath);
        sb.append("\nLocal dashboard data file location : " + dashboardFilePath);
        return sb.toString();
    }

}
