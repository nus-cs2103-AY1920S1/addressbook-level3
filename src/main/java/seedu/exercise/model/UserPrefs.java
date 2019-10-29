package seedu.exercise.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.exercise.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path exerciseBookFilePath = Paths.get("data" , "exercisebook.json");
    private Path propertyBookFilePath = Paths.get("data", "propertybook.json");
    private Path exerciseDatabaseFilePath = Paths.get("data", "exercisedatabase.json");
    private Path regimeBookFilePath = Paths.get("data", "regimebook.json");
    private Path scheduleBookFilePath = Paths.get("data", "schedulebook.json");

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
        setExerciseBookFilePath(newUserPrefs.getExerciseBookFilePath());
        setRegimeBookFilePath(newUserPrefs.getRegimeBookFilePath());
        setPropertyBookFilePath(newUserPrefs.getPropertyBookFilePath());
        setAllExerciseBookFilePath(newUserPrefs.getAllExerciseBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getExerciseBookFilePath() {
        return exerciseBookFilePath;
    }

    public void setExerciseBookFilePath(Path exerciseBookFilePath) {
        requireNonNull(exerciseBookFilePath);
        this.exerciseBookFilePath = exerciseBookFilePath;
    }

    public Path getRegimeBookFilePath() {
        return regimeBookFilePath;
    }

    public void setRegimeBookFilePath(Path regimeBookFilePath) {
        requireNonNull(regimeBookFilePath);
        this.regimeBookFilePath = regimeBookFilePath;
    }

    public Path getScheduleBookFilePath() {
        return scheduleBookFilePath;
    }

    public Path getPropertyBookFilePath() {
        return propertyBookFilePath;
    }

    public void setPropertyBookFilePath(Path propertyBookFilePath) {
        requireNonNull(propertyBookFilePath);
        this.propertyBookFilePath = propertyBookFilePath;
    }

    public Path getAllExerciseBookFilePath() {
        return exerciseDatabaseFilePath;
    }

    public void setAllExerciseBookFilePath(Path allExerciseFilePath) {
        requireNonNull(allExerciseFilePath);
        this.exerciseDatabaseFilePath = allExerciseFilePath;
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
                && exerciseBookFilePath.equals(o.exerciseBookFilePath)
                && regimeBookFilePath.equals(o.regimeBookFilePath)
                && propertyBookFilePath.equals(o.propertyBookFilePath)
                && exerciseDatabaseFilePath.equals(o.exerciseDatabaseFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings,
                exerciseBookFilePath, regimeBookFilePath,
                propertyBookFilePath, exerciseDatabaseFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + exerciseBookFilePath);
        return sb.toString();
    }

}
