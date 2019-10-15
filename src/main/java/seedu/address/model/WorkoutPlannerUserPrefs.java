package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class WorkoutPlannerUserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path exercisesFilePath = Paths.get("data" , "exercises.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public WorkoutPlannerUserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public WorkoutPlannerUserPrefs(ReadOnlyUserPrefs userPrefs) {
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

    public void setExercisesFilePath(Path exercisesFilePath) {
        requireNonNull(exercisesFilePath);
        this.exercisesFilePath = exercisesFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WorkoutPlannerUserPrefs)) { //this handles null as well.
            return false;
        }

        WorkoutPlannerUserPrefs o = (WorkoutPlannerUserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && exercisesFilePath.equals(o.exercisesFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, exercisesFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + exercisesFilePath);
        return sb.toString();
    }

}
